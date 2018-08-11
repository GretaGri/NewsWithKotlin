package com.example.android.newswithkotlin

import android.os.Parcelable
import com.beust.klaxon.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class News(
        @Json(name = "webTitle")
        val title: String = "title",
        @Json(name = "authorTitle")
        val author: String = "author",
        val webUrl: String = "url") : Parcelable