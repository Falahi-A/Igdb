package com.pinch.codeassignment.igdb.data.model

import com.pinch.codeassignment.igdb.domain.model.Game
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
    @Json(name = "release_dates") val releaseDates: List<ReleaseDateNetResponse>?,
    @Json(name = "screenshots") val screenshots: List<ScreenshotNetResponse>?,
    @Json(name = "platforms") val platforms: List<PlatformNetResponse>?,
    @Json(name = "genres") val genres: List<GenreNetResponse>?

)

fun GameNetResponse.toGame(): Game {
    return Game(
        id = id,
        name = name,
        imageId = cover?.imageId,
        summary = summary,
        follows = follows,
        rating = rating ,
        ratingCount = ratingCount,
        releaseDates = releaseDates?.map { releaseDate ->
            releaseDate.human
        },
        screenshots = screenshots?.map { screenshot ->
            screenshot.imageId
        },
        platforms = platforms?.map { platform ->
            platform.name
        },
        genres = genres?.map { genre ->
            genre.name
        }
    )
}