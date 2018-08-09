package com.example.android.newswithkotlin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class News(val title: String,
           val author: String,
           val webUrl: String) : Parcelable