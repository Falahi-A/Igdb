package com.pinch.codeassignment.igdb.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [GameEntity::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class GamesDb : RoomDatabase() {

    abstract fun getGamesDao(): GamesDao
}