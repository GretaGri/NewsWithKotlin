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
        val newsItem: ArrayList<News>) : Parcelable

//main news content
//UNIQUE field referenced from: https://stackoverflow.com/a/48736187
@Entity(tableName = "newstable",
        indices = arrayOf(Index(value = ["webUrl", "title"], unique = true)))
@Parcelize
data class News(
        @Expose
        @SerializedName("webTitle")
        var title: String = "webTitle",
        @Expose
        @SerializedName("webUrl")
        var webUrl: String = "webUrl",
        /*to exclude this field from being parsed/serialized and desrialized,
        we can ignore by setting these annotations to false
        */
        @PrimaryKey(autoGenerate = true)
        @Expose(deserialize = false, serialize = false)
        var id: Int = 0,

        @Expose
        @Ignore
        @SerializedName("tags")
        var tags: ArrayList<ContributorContent>) : Parcelable {
    constructor() : this("title", "webUrl", 0, ArrayList())
}

/*
for foreign key implementations,
refer to: https://www.bignerdranch.com/blog/room-data-storage-for-everyone/
and https://medium.com/@tonyowen/room-entity-annotations-379150e1ca82
*/
@Entity(tableName = "authorstable",
        foreignKeys = arrayOf(ForeignKey(entity = News::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("idContributor"),
                onDelete = ForeignKey.CASCADE)))
@Parcelize
data class ContributorContent(
        @Expose(deserialize = false, serialize = false)
        @PrimaryKey(autoGenerate = true)
        var idContributor: Int = 0,
        @Expose
        @SerializedName("webTitle")
        var title: String = "webTitle",
        @Expose
        @SerializedName("apiUrl")
        var apiUrl: String = "apiUrl") : Parcelable