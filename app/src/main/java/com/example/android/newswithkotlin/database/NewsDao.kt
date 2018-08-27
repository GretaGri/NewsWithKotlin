package com.example.android.newswithkotlin.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

/**
 * Created by Greta Grigutė on 2018-08-23.
 */
@Dao
interface NewsDao {

    //a method to get all the news, which returns list of news.
    @Query("SELECT * FROM newstable")
    fun loadAllNews(): LiveData<List<News>>

    //a method to insert one news item.
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(newsItem: News)

    //a method to update news
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNews(newsItem: News)

    //a method to delete one news item.
    @Delete
    fun deleteNews(newsItem: News)

    //a method to delete all news.
    @Query("DELETE FROM newstable")
    abstract fun deleteAll()

    //a method to select news item with id
    @Query("SELECT * FROM newstable WHERE id = :id")
    fun loadTaskById(id: Int): LiveData<News>
}