package com.example.myfunlist.Models

import com.google.gson.annotations.SerializedName

class ListMovie(
    title: String,
    image: String,
    id: String,
    movieUserId: String?
) {
    @SerializedName("title")
    val title = title

    @SerializedName("image")
    val image = image

    @SerializedName("id")
    val id = id

    val movieUserId = movieUserId
}