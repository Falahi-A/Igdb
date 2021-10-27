package com.pinch.codeassignment.igdb.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.pinch.codeassignment.igdb.R

fun loadImage(imageView: ImageView, imageUrl: String?) {
    Glide.with(imageView.context).load(imageUrl)
        .placeholder(R.drawable.ic_game_controller)
        .into(imageView)
}

/**
 * there is a rule for making an image url witch need to be respected.
 *
 */
fun buildImageUrl(
    imageUrl: String = Constants.IMAGE_URL,
    imageSize: String,
    imageId: String? = "",
    imageFormat: String = Constants.IMAGE_FORMAT
) = "$imageUrl$imageSize/$imageId$imageFormat"
