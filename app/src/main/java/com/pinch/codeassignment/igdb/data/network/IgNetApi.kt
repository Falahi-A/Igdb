package com.pinch.codeassignment.igdb.data.network

import com.pinch.codeassignment.igdb.data.model.GameNetResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface IgNetApi {

    @POST("games")
    suspend fun getGames(@Query("fields") fields:String): List<GameNetResponse>

}