package com.example.android.newswithkotlin.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface NewsDao {

    @Query("SELECT * FROM newstable ORDER BY id")
    fun loadAllNews(): LiveData<List<News>>

    @Insert
    fun insertNews(news: News)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNews(news: News)

    @Delete
    fun deleteNews(taskEntry: News)

    @Query("SELECT * FROM newstable WHERE title = :id")
    fun loadNewsById(id: Int): LiveData<News>

    @Query("SELECT * FROM authorstable WHERE idContributor IS :idContributor")
    fun getAuthorsForNews(idContributor: Int): List<ContributorContent>

    @Insert
    fun insertAuthorsForNews(author: ContributorContent)
}