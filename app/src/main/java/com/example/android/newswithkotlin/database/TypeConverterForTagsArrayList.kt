package com.example.android.newswithkotlin.database

import android.arch.persistence.room.TypeConverter
import android.util.Log
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson



/**
 * Created by Greta Grigutė on 2018-08-24.
 */
class TypeConverterForTagsArrayList {

    companion object {


    @TypeConverter @JvmStatic
    fun arrayListToString(listToBeConverted: ArrayList<ContributerContent>?): String? {
       if (listToBeConverted!=null){
        val gson = Gson()
           return gson.toJson(listToBeConverted)}
        else return null
}

    @TypeConverter @JvmStatic
    fun stringToArrayList(stringToBeConverted: String?): ArrayList<ContributerContent>?{
     if(stringToBeConverted == null)  {
         return null
     } else{
         val listType = object : TypeToken<ArrayList<ContributerContent>>() {
        }.type
        return Gson().fromJson(stringToBeConverted, listType)
    }
    }
    }
}