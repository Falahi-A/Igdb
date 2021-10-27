package com.pinch.codeassignment.igdb.utils

object Constants {

    /**
     * Base on your need these fields can be changed. For more info you can visit [https://api-docs.igdb.com/#fields]
     * @see 'cover.image_id': image_id is an expander for cover property. for more information you can refer to https://api-docs.igdb.com/#expander
     */
    const val GAMES_FIELDS = "name,summary,follows,rating,rating_count,cover.image_id,screenshots.image_id,release_dates.human,platforms.name,genres.name"

    const val BASE_URL = "https://api.igdb.com/v4/"

    const val AUTHORIZATION_KEY = "Authorization"
    const val AUTHORIZATION = "Bearer dtlu02nx07b7hix9t0nc6wulubed48"
    const val CLIENT_ID_KEY = "Client-ID"
    const val CLIENT_ID = "ikefu3gjaojsnnt21ik7orxyofnztq"
    const val ACCEPT_KEY = "Accept"
    const val ACCEPT = "application/json"

    const val DATABASE_NAME = "games-db"

    const val IO_DISPATCHER = "io_dispatcher"
    const val MAIN_DISPATCHER = "main_dispatcher"

    const val IMAGE_COVER_SMALL_2X_SIZE =
        "cover_small_2x" // Base on your need it can be changed. for more info check this link [https://api-docs.igdb.com/#images]
    const val IMAGE_SCREENSHOT_BIG_SIZE =
        "screenshot_big"
    const val IMAGE_FORMAT = ".jpg"
    const val IMAGE_URL =
        "https://images.igdb.com/igdb/image/upload/t_"

    const val MAX_STRING_LENGTH = 12


}