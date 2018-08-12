package com.example.android.newswithkotlin

import android.util.Log
import com.google.gson.Gson


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

            val gson = Gson()
            val userJson = gson.fromJson(jsonResponse, GsonNews::class.java);
            Log.v("my_tag", "results is: " + userJson.response.newsItem.toString())
            var arrayList: ArrayList<NewsContent> = userJson.response.newsItem
        return arrayList
    }
}

