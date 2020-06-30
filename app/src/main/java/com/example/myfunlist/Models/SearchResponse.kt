package com.example.myfunlist.Models

import com.google.gson.annotations.SerializedName

class SearchResponse(titles: ArrayList<ListMovie>) {

    @SerializedName("titles")
    val titles: ArrayList<ListMovie> = titles
}