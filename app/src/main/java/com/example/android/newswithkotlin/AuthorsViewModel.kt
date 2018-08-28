package com.example.android.newswithkotlin

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.android.newswithkotlin.database.AppDatabase
import com.example.android.newswithkotlin.database.ContributorContent

class AuthorsViewModel(application: Application) : AndroidViewModel(application) {

    // Add a newses member variable for a list of News objects wrapped in a LiveData
    val authors: LiveData<List<ContributorContent>>

    init {
        // In the constructor use the loadAllNews of the newsDao to initialize the newses variable
        val database = AppDatabase.getInstance(this.getApplication())
        authors = database.newsDao().loadAllAuthors()
    }

    companion object {
        // Constant for logging
        private val TAG = AuthorsViewModel::class.java.simpleName
    }
}