package com.pinch.codeassignment.igdb.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GameEntity::class], version = 1)
abstract class GamesDb:RoomDatabase() {

    abstract fun getGamesDao(): GamesDao
}