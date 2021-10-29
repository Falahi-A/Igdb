package com.pinch.codeassignment.igdb.domain.repository

import android.database.sqlite.SQLiteException
import android.telephony.AvailableNetworkInfo
import com.pinch.codeassignment.igdb.data.db.GameEntity
import com.pinch.codeassignment.igdb.data.db.toGame
import com.pinch.codeassignment.igdb.data.model.CoverNetResponse
import com.pinch.codeassignment.igdb.data.model.GameNetResponse
import com.pinch.codeassignment.igdb.data.model.toGameEntity
import com.pinch.codeassignment.igdb.data.repository.IgRepository
import com.pinch.codeassignment.igdb.utils.Constants
import com.pinch.codeassignment.igdb.utils.buildImageUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class FakeIgRepository : IgRepository {

    private val gamesList = ArrayList<GameNetResponse>()
    private val gamesListDb = ArrayList<GameEntity>()
    private var isNetAvailable = false // // simulating internet situation
    private var size: Int = 0 // size of games list
    private var hasSqliteException = false // simulating sqliteException
    private var hasHttpException = false // simulating httpException


    fun hasSqliteException(hasException: Boolean) {
        hasSqliteException = hasException
    }

    fun hasHttpException(hasException: Boolean) {
        hasHttpException = hasException
    }


    fun setListSize(size: Int) {
        this.size = size
    }

    fun isNetworkAvailable(isNetAvailable: Boolean) {
        this.isNetAvailable = isNetAvailable
    }


    @Throws(HttpException::class, IOException::class)
    override suspend fun getGames(fields: String): List<GameNetResponse> {
        return when {
            !hasHttpException && isNetAvailable -> createGamesList()
            hasHttpException && isNetAvailable -> throw HttpException(
                Response.error<Any>(
                    409, ResponseBody.create(
                        MediaType.parse("plain/text"), "An unexpected error happened"
                    )
                )
            )
            !isNetAvailable -> throw IOException("An unexpected error happened")
            else -> emptyList()
        }
    }

    @Throws(SQLiteException::class)
    override suspend fun insertGamesDb(games: List<GameEntity>) { // if it has an exception throw it, otherwise do nothing(it means games were added successfully).
        if (hasSqliteException) throw SQLiteException("unexpected error happened ")
    }

    @Throws(SQLiteException::class)
    override suspend fun deleteGamesDb() {  // if it has an exception throw it, otherwise do nothing(it means games were deleted successfully).
        if (hasSqliteException) throw SQLiteException("unexpected error happened ")
    }

    @Throws(SQLiteException::class)
    override fun getGamesDb(): Flow<List<GameEntity>> {
        return if (hasSqliteException) throw SQLiteException("unexpected error happened ")
        else {
            flowOf(createGamesListDb())
        }

    }


    // convert GameNetResponse to local Game model
    fun getGamesList() = gamesList.map {
        it.toGameEntity().toGame()

    }

    // create a fake api response
    private fun createGamesList(): List<GameNetResponse> {
        gamesList.clear()
        for (i: Int in 1 until size) {
            (gamesList).add(
                GameNetResponse(
                    i,
                    i.toString(),
                    "",
                    0,
                    0.0,
                    0,
                    CoverNetResponse(i, ""),
                    null,
                    null,
                    null
                )
            )
        }
        return gamesList
    }

    // create a fake database response
    private fun createGamesListDb(): List<GameEntity> {
        gamesListDb.clear()
        for (i: Int in 1 until size) {
            gamesListDb.add(
                GameEntity(
                    i,
                    i.toString(),
                    0,
                    0.0,
                    0,
                    "",
                    buildImageUrl(
                        imageSize = Constants.IMAGE_COVER_SMALL_2X_SIZE,
                        imageId = ""
                    ),
                    emptyList(),
                    emptyList(),
                    emptyList()
                )
            )
        }
        return gamesListDb
    }

}