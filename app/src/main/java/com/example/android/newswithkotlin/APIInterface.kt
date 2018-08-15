package com.example.android.newswithkotlin

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface APIInterface {
    @GET("/search?q=sport%2520culture&order-by=newest&api-key=0a397f99-4b95-416f-9c51-34c711f0069a&show-tags=contributor")
    fun getNewsList(@Query("response") results: String): Call<GsonNewsResponse>
}