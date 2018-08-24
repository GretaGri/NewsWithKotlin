package com.example.android.newswithkotlin.database

import android.arch.persistence.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson



/**
 * Created by Greta Grigutė on 2018-08-24.
 */
class TypeConverterForTagsArrayList {
    @TypeConverter
    fun arrayListToString(listToBeConverted: ArrayList<ContributerContent>): String {
        val gson = Gson()
        return gson.toJson(listToBeConverted)
}

    @TypeConverter
    fun stringToArrayList(stringToBeConverted: String): ArrayList<ContributerContent> {
        val listType = object : TypeToken<ArrayList<ContributerContent>>() {

        }.type
        return Gson().fromJson(stringToBeConverted, listType)
    }
}