package com.example.android.newswithkotlin

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Greta Grigutė on 2018-08-14.
 */
//main news content
data class NewsContent(
        @SerializedName("webTitle")
        val title: String = "webTitle",
        @SerializedName("webUrl")
        val webUrl: String = "webUrl",
        @SerializedName("tags")
        val tags: ArrayList<ContributerContent> = ArrayList())

data class ContributerContent(
        @SerializedName("webTitle")
        val title: String = "webTitle",
        @SerializedName("apiUrl")
        val apiUrl: String = "apiUrl")