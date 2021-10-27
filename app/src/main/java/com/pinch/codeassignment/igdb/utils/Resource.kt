package com.pinch.codeassignment.igdb.utils

/**
 * Response possibilities that my occurs for fetching data from network apis
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T? = null) : Resource<T>(data)
    class Loading<T> : Resource<T>()
    class Error<T>(data: T? = null, message: String? = null) : Resource<T>(data, message)

}