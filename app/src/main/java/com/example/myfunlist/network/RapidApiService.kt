package com.example.myfunlist.network

import com.example.myfunlist.Models.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RapidApiService {
    @GET("/search/{phrase}")
    fun search(@Path("phrase") phrase: String): Call<SearchResponse>
}