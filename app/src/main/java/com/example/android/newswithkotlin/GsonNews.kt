package com.example.android.newswithkotlin

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//to parse the "response" field, we can also use the suggestion from: https://stackoverflow.com/q/32490011/5770629
//Which would make the code look like:
//JsonElement userJson = new JsonParser().parse("response");
@Parcelize
data class GsonNews(
        @SerializedName("response")
        val response: GsonNewsResults) : Parcelable

@Parcelize
data class GsonNewsResults(
        @SerializedName("results")
        val newsItem: ArrayList<NewsContent> = ArrayList()) : Parcelable

//main news content
@Parcelize
data class NewsContent(
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