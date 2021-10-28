package com.pinch.codeassignment.igdb.data.repository

import com.pinch.codeassignment.igdb.data.db.GameEntity
import com.pinch.codeassignment.igdb.data.model.GameNetResponse
import kotlinx.coroutines.flow.Flow

interface IgRepository {

    suspend fun getGames(fields: String): List<GameNetResponse>

    suspend fun insertGamesDb(games: List<GameEntity>)

    suspend fun deleteGamesDb()

    suspend fun hasItemDb(): Boolean

    fun getGamesDb(): Flow<List<GameEntity>>


}