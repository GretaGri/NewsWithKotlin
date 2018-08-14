package com.example.android.newswithkotlin

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


//we can reduce no of model classes to handle the Json Parsing using the method JsonParser().parse)
//as in this example: https://stackoverflow.com/q/32490011/5770629
//so finally we'll not need the model classes GsonNews and GsonNewsResults

@Parcelize
data class GsonNewsResponse(
        @SerializedName("response")
        val response: GsonNewsResults) : Parcelable

@Parcelize
data class GsonNewsResults(
        @SerializedName("results")
        val newsItem: ArrayList<News> = ArrayList()) : Parcelable

//main news content
@Parcelize
data class News(
        @SerializedName("webTitle")
        val title: String = "webTitle",
        @SerializedName("webUrl")
        val webUrl: String = "webUrl",
        @SerializedName("tags")
        val tags: ArrayList<ContributerContent> = ArrayList()) : Parcelable

//author details
@Parcelize
data class ContributerContent(
        @SerializedName("webTitle")
        val title: String = "webTitle",
        @SerializedName("apiUrl")
        val apiUrl: String = "apiUrl") : Parcelable