package com.example.android.newswithkotlin

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.util.Log
import com.example.android.newswithkotlin.database.News
import com.example.android.newswithkotlin.database.NewsDataBase

/**
 * Created by Greta Grigutė on 2018-08-28.
 */
class FavoriteNewsViewModel (application: Application) : AndroidViewModel(application) {

    val news: LiveData<List<News>>

    init{
        // super(application)// constructor that gets one parameter type application.
        val dataBase = NewsDataBase.getInstance(this.getApplication())
        Log.d(TAG, "Actively retrieving favorite news from Database")
        news = dataBase.newsDao().loadFavorites(1)
    }

    companion object {
        // constant for logging.
        private val TAG = FavoriteNewsViewModel::class.java.getSimpleName()
    }
}