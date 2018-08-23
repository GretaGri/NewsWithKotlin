package com.example.android.newswithkotlin

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.util.Log
import com.example.android.newswithkotlin.database.AppDatabase
import com.example.android.newswithkotlin.database.News

class MainViewModel(application: Application) : AndroidViewModel(application) {

    // Add a newss member variable for a list of News objects wrapped in a LiveData
    val newses: LiveData<List<News>>

    init {
        // In the constructor use the loadAllNews of the newsDao to initialize the newses variable
        val database = AppDatabase.getInstance(this.getApplication())
        Log.d("my_tag", "Actively retrieving the newses from the DataBase")
        newses = database.newsDao().loadAllNews()
    }

    companion object {
        // Constant for logging
        private val TAG = MainViewModel::class.java.simpleName
    }
}