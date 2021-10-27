package com.pinch.codeassignment.igdb.data.repository

import com.pinch.codeassignment.igdb.data.model.GameNetResponse

interface IgRepository {

    suspend fun getGames(fields:String): List<GameNetResponse>
}