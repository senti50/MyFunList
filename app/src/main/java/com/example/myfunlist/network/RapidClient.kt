package com.example.myfunlist.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class RapidClient {
    companion object{
        private var retrofit: Retrofit? = null
        private val BASE_URL = "https://movie-database-imdb-alternative.p.rapidapi.com"

        fun getInstace(): Retrofit {
            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request().newBuilder()
                        .addHeader("x-rapidapi-host", "imdb-internet-movie-database-unofficial.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", "f0ad274accmshd6653184123f9b1p11beb4jsn42ce3fdfc6d5")
                        .build()
                    return chain.proceed(request)
                }
            })

            if(retrofit === null) {
                retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
            }

            return retrofit!!
        }
    }
}