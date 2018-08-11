package com.example.android.newswithkotlin

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GsonNews(
        @SerializedName("results")
        val newsItem: ArrayList<NewsContent> = ArrayList()) : Parcelable {
}

@Parcelize
data class NewsContent(
        @SerializedName("webTitle")
        val title: String = "webTitle",
        @SerializedName("webUrl")
        val webUrl: String = "webUrl",
        @SerializedName("tags")
        val tags: ContributerContent = ContributerContent()) : Parcelable

@Parcelize
data class ContributerContent(
        @SerializedName("webTitle")
        val title: String = "webTitle",
        @SerializedName("apiUrl")
        val apiUrl: String = "apiUrl") : Parcelable