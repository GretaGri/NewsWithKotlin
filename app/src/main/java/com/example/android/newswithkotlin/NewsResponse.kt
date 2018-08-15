package com.example.android.newswithkotlin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.google.gson.annotations.SerializedName

/**
 * Created by Greta Grigutė on 2018-08-14.
 */
//main news content
//for handling Parcelable do not forget to add to Build gradle App
// AndroidExtensions {experimental = true}

@Parcelize
data class NewsResponse(
        @SerializedName("response")
        val response: Results) : Parcelable

@Parcelize
data class Results(
        @SerializedName("results")
        val newsItem: ArrayList<NewsContent> = ArrayList()) : Parcelable

@Parcelize
data class NewsContent(
        @SerializedName("webTitle")
        val title: String = "webTitle",
        @SerializedName("webUrl")
        val webUrl: String = "webUrl",
        @SerializedName("tags")
        val tags: ArrayList<ContributerContent> = ArrayList()):Parcelable

@Parcelize
data class ContributerContent(
        @SerializedName("webTitle")
        val title: String = "webTitle",
        @SerializedName("apiUrl")
        val apiUrl: String = "apiUrl"): Parcelable