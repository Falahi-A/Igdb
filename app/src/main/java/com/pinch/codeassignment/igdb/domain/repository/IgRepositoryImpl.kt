package com.pinch.codeassignment.igdb.domain.repository

import com.pinch.codeassignment.igdb.data.db.GameEntity
import com.pinch.codeassignment.igdb.data.db.GamesDao
import com.pinch.codeassignment.igdb.data.model.GameNetResponse
import com.pinch.codeassignment.igdb.data.network.IgNetApi
import com.pinch.codeassignment.igdb.data.repository.IgRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class IgRepositoryImpl @Inject constructor(
    private val igNetApi: IgNetApi,
    private val gamesDao: GamesDao
) : IgRepository {


    override suspend fun getGames(fields: String): List<GameNetResponse> =
        igNetApi.getGames(fields)

    override suspend fun insertGamesDb(games: List<GameEntity>) {
        gamesDao.insertGames(games)
    }

    override fun getGamesDb(): Flow<List<GameEntity>> {
        return gamesDao.getGames()
    }

    override suspend fun deleteGamesDb() {
        return gamesDao.deleteAllGames()
    }

}