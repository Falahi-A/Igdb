package com.pinch.codeassignment.igdb.data.model

import com.pinch.codeassignment.igdb.data.db.GameEntity
import com.pinch.codeassignment.igdb.utils.Constants
import com.pinch.codeassignment.igdb.utils.buildImageUrl
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class GameNetResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "summary") val summary: String?,
    @Json(name = "follows") val follows: Int?,
    @Json(name = "rating") val rating: Double?,
    @Json(name = "rating_count") val ratingCount: Int?,
    @Json(name = "cover") val cover: CoverNetResponse?,
    @Json(name = "screenshots") val screenshots: List<ScreenshotNetResponse>?,
    @Json(name = "platforms") val platforms: List<PlatformNetResponse>?,
    @Json(name = "genres") val genres: List<GenreNetResponse>?

)

fun GameNetResponse.toGameEntity(): GameEntity {
    return GameEntity(
        id = id,
        name = name,
        imageUrl = buildImageUrl(
            imageSize = Constants.IMAGE_COVER_SMALL_2X_SIZE,
            imageId = cover?.imageId
        ),
        summary = summary ?: "",
        follows = follows ?: 0,
        rating = rating ?: 0.0,
        ratingCount = ratingCount ?: 0,
        screenshots = screenshots?.map { screenshot ->
            buildImageUrl(
                imageSize = Constants.IMAGE_SCREENSHOT_BIG_SIZE,
                imageId = screenshot.imageId
            )
        } ?: emptyList(),
        platforms = platforms?.map { platform ->
            platform.name
        } ?: emptyList(),
        genres = genres?.map { genre ->
            genre.name
        } ?: emptyList()
    )
}