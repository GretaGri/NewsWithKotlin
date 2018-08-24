package com.example.android.newswithkotlin

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.util.Log
import com.example.android.newswithkotlin.database.AppDatabase
import com.example.android.newswithkotlin.database.ContributorContent

class AuthorsViewModel(application: Application, title: Int) : AndroidViewModel(application) {

    // Add a newss member variable for a list of News objects wrapped in a LiveData
    val authors: List<ContributorContent>

    init {
        // In the constructor use the loadAllNews of the newsDao to initialize the newses variable
        val database = AppDatabase.getInstance(this.getApplication())
        Log.d("my_tag", "Actively retrieving the newses from the DataBase")
        authors = database.newsDao().getAuthorsForNews(title)
    }

    companion object {
        // Constant for logging
        private val TAG = AllNewsViewModel::class.java.simpleName
    }
}