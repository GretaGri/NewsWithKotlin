package com.example.android.newswithkotlin

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.android.newswithkotlin.database.AppDatabase

class AddNewsViewModelFactory(private val mDb: AppDatabase,
                              private val mNewsId: Int) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return AddNewsViewModel(mDb, mNewsId) as T
    }
}