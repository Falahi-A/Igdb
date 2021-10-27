package com.pinch.codeassignment.igdb.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoverNetResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "image_id") val imageId: String
)