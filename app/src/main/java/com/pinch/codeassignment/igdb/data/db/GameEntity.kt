package com.pinch.codeassignment.igdb.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "follows") val follows: Int,
    @ColumnInfo(name = "rating") val rating: Double,
    @ColumnInfo(name = "rating_count") val ratingCount: Int,
    @ColumnInfo(name = "summary") val summary: String,
    @ColumnInfo(name = "image_url") val imageUrl: String
)
