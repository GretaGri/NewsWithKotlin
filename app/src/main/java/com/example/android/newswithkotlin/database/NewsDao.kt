package com.example.android.newswithkotlin.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface NewsDao {

    @Query("SELECT * FROM newstable ORDER BY id")
    fun loadAllNews(): LiveData<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: News)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNews(news: News)

    @Delete
    fun deleteNews(news: News)

    @Query("SELECT * FROM newstable WHERE title = :id")
    fun loadNewsById(id: Int): LiveData<News>

    @Query("SELECT * FROM newstable ORDER BY id")
    fun loadAllNewsArrayListFromDatabase(): List<News>
}