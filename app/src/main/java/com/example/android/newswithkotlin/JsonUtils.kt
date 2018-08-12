package com.example.android.newswithkotlin

import com.google.gson.Gson
import com.google.gson.JsonParser


/**
 * Created by Greta Grigutė on 2018-08-09.
 */
class JsonUtils {

    /**
     * Create a private constructor because no one should ever create a [JsonUtils] object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private fun JsonUtils() {}

    fun extractFeatureFromJson(jsonResponse: String?): ArrayList<NewsContent> {

        //another way of doing json parsing by reducing no of model classes
        val newsJsonArray = JsonParser().parse(jsonResponse).asJsonObject
                .getAsJsonObject("response").asJsonObject
                .getAsJsonArray("results").asJsonArray

        val arrayList: ArrayList<NewsContent> = ArrayList()
        for (news in newsJsonArray) {
            arrayList.add(Gson().fromJson<NewsContent>(news, NewsContent::class.java))
        }

        return arrayList
    }
}

