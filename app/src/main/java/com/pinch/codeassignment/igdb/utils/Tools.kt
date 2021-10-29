package com.pinch.codeassignment.igdb.utils

import android.database.sqlite.SQLiteException
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.pinch.codeassignment.igdb.R
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.io.IOException

fun loadImage(imageView: ImageView, imageUrl: String?) {
    Glide.with(imageView.context).load(imageUrl)
        .placeholder(R.drawable.ic_game_controller)
        .into(imageView)
}

/**
 * There is a rule for making an image url witch need to be respected.
 *  For more information checkout this link [https://api-docs.igdb.com/#images]
 */
fun buildImageUrl(
    imageUrl: String = Constants.IMAGE_URL,
    imageSize: String,
    imageId: String? = "",
    imageFormat: String = Constants.IMAGE_FORMAT
) = "$imageUrl$imageSize/$imageId$imageFormat"


/**
 * Cache handling strategy for api requests
 */
inline fun <RequestType, ResultType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,  // Get data from database
    crossinline fetch: suspend () -> RequestType, // Fetch data from server
    crossinline saveFetchedResult: suspend (RequestType) -> Unit, // Save fetched data from server to database
    crossinline shouldFetch: () -> Boolean  // Making decision that if need to fetch data from server or not
) = flow {
    val flow = if (shouldFetch()) {
        emit(Resource.Loading())
        try {
            saveFetchedResult(fetch())
            query().map {
                Resource.Success(it)
            }
        } catch (ioException: IOException) { // it's for handling internet issues
            query().map {
                Resource.Error(
                    it,
                    ioException.localizedMessage
                        ?: "An unexpected error happened. check your internet connection"
                )
            }
        } catch (httpException: HttpException) { // it's for handling server issues
            query().map {
                Resource.Error(
                    it,
                    httpException.localizedMessage ?: "An unexpected server error happened."
                )
            }
        } catch (e: SQLiteException) { // it's for handling database issues
            query().map {
                Resource.Error(
                    message = e.localizedMessage ?: "An unexpected database error happened."
                )
            }
        }
    } else {
        query().map {
            Resource.Success(it)
        }
    }
    emitAll(flow)
}
