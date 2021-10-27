package com.pinch.codeassignment.igdb.domain.usecase

import com.pinch.codeassignment.igdb.data.model.toGame
import com.pinch.codeassignment.igdb.data.repository.IgRepository
import com.pinch.codeassignment.igdb.domain.model.Game
import com.pinch.codeassignment.igdb.utils.Constants
import com.pinch.codeassignment.igdb.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named


class GetGamesUseCase @Inject constructor(
    private val igRepository: IgRepository,
    @Named(Constants.IO_DISPATCHER) private val dispatcher: CoroutineDispatcher
) {


    operator fun invoke() = flow<Resource<List<Game>>> {
        try {
            emit(Resource.Loading())
            val games = igRepository.getGames(Constants.GAMES_FIELDS)
            emit(Resource.Success(games.map { gameNetResponse ->
                gameNetResponse.toGame()
            }))
        } catch (ioException: IOException) { // it's for handling internet issues
            emit(
                Resource.Error(
                    message = ioException.localizedMessage
                        ?: "couldn't reach server, check your internet please"
                )
            )
        } catch (httpException: HttpException) { // it's for handling either server or local issues
            emit(
                Resource.Error(
                    message = httpException.localizedMessage ?: "an unexpected error occurred"
                )
            )
        }

    }.flowOn(dispatcher)

}