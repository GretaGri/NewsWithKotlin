package com.example.android.newswithkotlin.database

import android.arch.persistence.room.*
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
        val newsItem: ArrayList<News> = ArrayList()) : Parcelable

//main news content
@Entity(tableName = "newstable")
@Parcelize
data class News(
        @Expose
        @SerializedName("webTitle")
        val title: String = "webTitle",

        @Expose
        @SerializedName("webUrl")
        val webUrl: String = "webUrl",

        /* to exclude this field from being parsed/serialized and deserialized,
        we can ignore by setting these annotations to false */
        @Expose(deserialize = false, serialize = false)
        @ColumnInfo(name = "id")
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,

        //column for detecting favorite items
        @Expose(deserialize = false, serialize = false)
        var favorite: Int = 0,

        @TypeConverters(TypeConverterForTagsArrayList::class)
        @Expose
        @SerializedName("tags")
        val tags: ArrayList<ContributerContent> = ArrayList()) : Parcelable {
    @Ignore
    constructor () : this("title", "webUrl", 0,0, ArrayList<ContributerContent>())
}


//author details
@Parcelize
data class ContributerContent(
        @Expose
        @SerializedName("webTitle")
        val title: String = "webTitle",
        @Expose
        @SerializedName("apiUrl")
        val apiUrl: String = "apiUrl") : Parcelable