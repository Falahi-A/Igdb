package com.pinch.codeassignment.igdb.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesDao {

    @Query("SELECT * FROM games")
    fun getGames(): Flow<List<GameEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(gamesList: List<GameEntity>)

    @Query("DELETE FROM games")
    suspend fun deleteAllGames()

    @Query("SELECT EXISTS(SELECT * FROM games)")
    suspend fun hasItem(): Boolean

}