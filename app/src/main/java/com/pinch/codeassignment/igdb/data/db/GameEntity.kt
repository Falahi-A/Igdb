package com.pinch.codeassignment.igdb.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pinch.codeassignment.igdb.domain.model.Game

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "follows") val follows: Int,
    @ColumnInfo(name = "rating") val rating: Double,
    @ColumnInfo(name = "rating_count") val ratingCount: Int,
    @ColumnInfo(name = "summary") val summary: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "screenshots") val screenshots: List<String>,
    @ColumnInfo(name = "platforms") val platforms: List<String>,
    @ColumnInfo(name = "genres") val genres: List<String>,
)

fun GameEntity.toGame(): Game {
    return Game(
        id = id,
        name = name,
        imageUrl = imageUrl,
        summary = summary,
        follows = follows,
        rating = rating,
        ratingCount = ratingCount,
        screenshots = screenshots,
        platforms = platforms,
        genres = genres
    )
}
