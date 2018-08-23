package com.example.android.newswithkotlin

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.android.newswithkotlin.database.AppDatabase
import com.example.android.newswithkotlin.database.News

class AddNewsViewModel(database: AppDatabase, newsId: Int) : ViewModel() {

    val task: LiveData<News>

    init {
        task = database.newsDao().loadNewsById(newsId)
    }
}