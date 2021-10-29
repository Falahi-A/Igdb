package com.pinch.codeassignment.igdb.ui.main.games

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.pinch.codeassignment.igdb.domain.repository.FakeIgRepository
import com.pinch.codeassignment.igdb.domain.usecase.GetGamesUseCase
import com.pinch.codeassignment.igdb.utils.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class GamesListViewModelTest {


    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule: CoroutinesTestRule = CoroutinesTestRule()

    private lateinit var fakeGetGamesUseCase: GetGamesUseCase
    private lateinit var fakeIgRepository: FakeIgRepository
    private lateinit var gamesListViewModel: GamesListViewModel


    @Before
    fun setUp() {
        fakeIgRepository = FakeIgRepository()
        fakeGetGamesUseCase = GetGamesUseCase(fakeIgRepository, testCoroutineRule.testDispatcher)
        gamesListViewModel = GamesListViewModel(fakeGetGamesUseCase)
    }


    @Test
    fun `get games return games list, internet is available`() = runBlockingTest {

        fakeIgRepository.isNetworkAvailable(true)
        gamesListViewModel.getGames(true)
        Truth.assertThat(gamesListViewModel.games.value is GamesListViewState).isTrue()
        Truth.assertThat(gamesListViewModel.games.value?.error).isEmpty()
        Truth.assertThat(gamesListViewModel.games.value?.gamesList)
            .isEqualTo(fakeIgRepository.getGamesList())

    }

    @Test
    fun `get games return games list, internet is not available`() = runBlockingTest {

        fakeIgRepository.isNetworkAvailable(false)
        gamesListViewModel.getGames(false)
        Truth.assertThat(gamesListViewModel.games.value is GamesListViewState).isTrue()
        Truth.assertThat(gamesListViewModel.games.value?.error).isEmpty()
        Truth.assertThat(gamesListViewModel.games.value?.gamesList)
            .isEqualTo(fakeIgRepository.getGamesList())

    }


    @Test
    fun `get games return error, internet is available`() = runBlockingTest {

        fakeIgRepository.isNetworkAvailable(true)
        fakeIgRepository.hasHttpException(true)
        gamesListViewModel.getGames(true)
        Truth.assertThat(gamesListViewModel.games.value is GamesListViewState).isTrue()
        Truth.assertThat(gamesListViewModel.games.value?.error).isNotEmpty()
        Truth.assertThat(gamesListViewModel.games.value?.gamesList)
            .isEqualTo(fakeIgRepository.getGamesList())

    }
}