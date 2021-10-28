package com.pinch.codeassignment.igdb.domain.usecase

import androidx.room.withTransaction
import com.pinch.codeassignment.igdb.data.db.GamesDb
import com.pinch.codeassignment.igdb.data.db.toGame
import com.pinch.codeassignment.igdb.data.model.toGameEntity
import com.pinch.codeassignment.igdb.data.repository.IgRepository
import com.pinch.codeassignment.igdb.utils.Constants
import com.pinch.codeassignment.igdb.utils.networkBoundResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named


class GetGamesUseCase @Inject constructor(
    private val igRepository: IgRepository,
    private val gamesDb: GamesDb,
    @Named(Constants.IO_DISPATCHER) private val dispatcher: CoroutineDispatcher
) {


    operator fun invoke(
        isNetworkAvailable: Boolean
    ) = networkBoundResource(query = {
        igRepository.getGamesDb().map {
            it.map { gameEntity ->
                gameEntity.toGame()

            }
        }
    }, fetch = {
        igRepository.getGames(Constants.GAMES_FIELDS)
    }, saveFetchedResult = { gamesResponse ->
        gamesDb.withTransaction {
            igRepository.deleteGamesDb()
            igRepository.insertGamesDb(gamesResponse.map { gameNetResponse ->
                gameNetResponse.toGameEntity()

            })
        }
    }, shouldFetch = { hasItem ->
        isNetworkAvailable || !hasItem
    }, hasItem = {
        igRepository.hasItemDb()
    }).flowOn(dispatcher)

}