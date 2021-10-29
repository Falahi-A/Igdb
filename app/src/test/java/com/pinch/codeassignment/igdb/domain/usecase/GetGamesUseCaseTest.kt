package com.pinch.codeassignment.igdb.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.pinch.codeassignment.igdb.domain.repository.FakeIgRepository
import com.pinch.codeassignment.igdb.utils.CoroutinesTestRule
import com.pinch.codeassignment.igdb.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class GetGamesUseCaseTest {

    private lateinit var fakeRepository: FakeIgRepository

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule: CoroutinesTestRule = CoroutinesTestRule()

    private lateinit var getGamesUseCase: GetGamesUseCase


    @Before
    fun setup() {
        fakeRepository = FakeIgRepository()
        getGamesUseCase = GetGamesUseCase(
            igRepository = fakeRepository,
            dispatcher = testCoroutineRule.testDispatcher
        )
    }

    @Test
    fun `invoke return games list, network is available`() =
        testCoroutineRule.testDispatcher.runBlockingTest {

            val games = getGamesUseCase.invoke(true)
            fakeRepository.isNetworkAvailable(true)
            fakeRepository.setListSize(10)


            Truth.assertThat(games.first() is Resource.Loading).isEqualTo(true)
            Truth.assertThat(games.drop(1).first() is Resource.Success).isEqualTo(true)
            Truth.assertThat(games.drop(1).first().data).isEqualTo(fakeRepository.getGamesList())
            Truth.assertThat(games.drop(1).first().data?.size)
                .isEqualTo(fakeRepository.getGamesList().size)


        }

    @Test
    fun `invoke return httpException, network is available`() =
        testCoroutineRule.testDispatcher.runBlockingTest {

            fakeRepository.isNetworkAvailable(true)
            fakeRepository.hasHttpException(true)
            fakeRepository.hasSqliteException(false)
            val games = getGamesUseCase.invoke(true)

            Truth.assertThat(games.first() is Resource.Loading).isEqualTo(true)
            Truth.assertThat(games.drop(1).first() is Resource.Error).isEqualTo(true)
            Truth.assertThat(games.drop(1).first().message).isNotEmpty()

        }

    @Test
    fun `invoke return games list , offline mode`() =
        testCoroutineRule.testDispatcher.runBlockingTest {

            fakeRepository.isNetworkAvailable(false)
            fakeRepository.hasHttpException(false)
            val games = getGamesUseCase.invoke(false)

            Truth.assertThat(games.first() is Resource.Success).isEqualTo(true)
            Truth.assertThat(games.first().data).isEqualTo(fakeRepository.getGamesList())
            Truth.assertThat(games.first().data?.size)
                .isEqualTo(fakeRepository.getGamesList().size)
        }


}