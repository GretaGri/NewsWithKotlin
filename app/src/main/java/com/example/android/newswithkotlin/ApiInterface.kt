package com.example.android.newswithkotlin

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Greta Grigutė on 2018-08-13.
 */
interface ApiInterface {

    @GET("/search?q=sport&order-by=newest&api-key=0a397f99-4b95-416f-9c51-34c711f0069a&show-tags=contributor")
    fun searchArticle(): Call<NewsContent>
    // fun searchArticle(@Query("webTitle") title: String, @Query("webUrl") url: String, @Query("tags") tags: ArrayList<ContributerContent>): Observable<NewsContent>

}