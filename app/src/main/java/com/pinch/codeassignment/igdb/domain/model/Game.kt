package com.pinch.codeassignment.igdb.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Game(
    val id: Int,
    val name: String,
    val imageId: String?,
    val summary: String?,
    val follows: Int?,
    val rating: Double?,
    val ratingCount: Int?,
    val releaseDates: List<String>?,
    val screenshots: List<String>?,
    val platforms: List<String>?,
    val genres: List<String>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(imageId)
        parcel.writeString(summary)
        parcel.writeValue(follows)
        parcel.writeValue(rating)
        parcel.writeValue(ratingCount)
        parcel.writeStringList(releaseDates)
        parcel.writeStringList(screenshots)
        parcel.writeStringList(platforms)
        parcel.writeStringList(genres)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Game> {
        override fun createFromParcel(parcel: Parcel): Game {
            return Game(parcel)
        }

        override fun newArray(size: Int): Array<Game?> {
            return arrayOfNulls(size)
        }
    }
}