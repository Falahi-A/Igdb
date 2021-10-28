package com.pinch.codeassignment.igdb.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Game(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val summary: String,
    val follows: Int,
    val rating: Double,
    val ratingCount: Int,
    val screenshots: List<String>,
    val platforms: List<String>,
    val genres: List<String>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.createStringArrayList()?: emptyList(),
        parcel.createStringArrayList()?: emptyList(),
        parcel.createStringArrayList()?: emptyList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(imageUrl)
        parcel.writeString(summary)
        parcel.writeInt(follows)
        parcel.writeDouble(rating)
        parcel.writeInt(ratingCount)
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
