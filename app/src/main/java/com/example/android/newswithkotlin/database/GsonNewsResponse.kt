package com.example.android.newswithkotlin.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


//we can reduce no of model classes to handle the Json Parsing using the method JsonParser().parse)
//as in this example: https://stackoverflow.com/q/32490011/5770629
//so finally we'll not need the model classes GsonNews and GsonNewsResults

@Parcelize
data class GsonNewsResponse(
        @Expose
        @SerializedName("response")
        val response: GsonNewsResults) : Parcelable

@Parcelize
data class GsonNewsResults(
        @Expose
        @SerializedName("results")
        val newsItem: ArrayList<News>) : Parcelable

//main news content
@Entity(tableName = "newstable")
@Parcelize
data class News(
        @Expose
        @SerializedName("webTitle")
        var title: String = "webTitle",
        @Expose
        @SerializedName("webUrl")
        var webUrl: String = "webUrl",
        /* to exclude this field from being parsed/serialized and desrialized,
        we can ignore by setting these annotations to false */
        @Expose(deserialize = false, serialize = false)
        @ColumnInfo(name = "id")
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        @Expose
        @ColumnInfo(name = "tags")
        @SerializedName("tags")
        @Ignore
        var tags: ArrayList<ContributorContent>) : Parcelable {
    constructor() : this("", "", 0, ArrayList())
}

@Parcelize
data class ContributorContent(
        @Expose
        @SerializedName("webTitle")
        val title: String = "webTitle",
        @Expose
        @SerializedName("apiUrl")
        val apiUrl: String = "apiUrl") : Parcelable