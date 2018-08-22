package com.example.android.newswithkotlin

import com.example.android.newswithkotlin.database.GsonNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface APIInterface {
    //@Query parameter explained here: https://stackoverflow.com/a/42036048/5770629
    @GET("/search?order-by=newest&api-key=0a397f99-4b95-416f-9c51-34c711f0069a&show-tags=contributor")
    fun getNewsList(@Query("q") results: String): Call<GsonNewsResponse>
}