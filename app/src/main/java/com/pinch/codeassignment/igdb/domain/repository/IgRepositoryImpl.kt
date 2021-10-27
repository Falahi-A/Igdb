package com.pinch.codeassignment.igdb.domain.repository

import com.pinch.codeassignment.igdb.data.model.GameNetResponse
import com.pinch.codeassignment.igdb.data.network.IgNetApi
import com.pinch.codeassignment.igdb.data.repository.IgRepository
import javax.inject.Inject

class IgRepositoryImpl @Inject constructor(private val igNetApi: IgNetApi) : IgRepository {


    override suspend fun getGames(fields:String): List<GameNetResponse> =
        igNetApi.getGames(fields)


}