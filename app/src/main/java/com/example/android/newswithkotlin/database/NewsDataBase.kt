package com.example.android.newswithkotlin.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context

/**
 * Created by Greta Grigutė on 2018-08-24.
 */
@Database(entities = arrayOf(News::class), version = 1, exportSchema = false)
@TypeConverters(TypeConverterForTagsArrayList::class)
abstract class NewsDataBase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        private val DB_NAME = "news.db"
       // private var INSTANCE: NewsDataBase? = null
       @Volatile private var INSTANCE: NewsDataBase? = null

        fun getInstance(context: Context): NewsDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also{INSTANCE = it}
            }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        NewsDataBase::class.java, DB_NAME)
                        .build()
        }

    // The same as not so deep Kotlin:

    //companion object {
    //    private var INSTANCE: NewsDataBase? = null
    //
    //    fun getInstance(context: Context): NewsDataBase? {
    //       if (INSTANCE == null) {
    //            synchronized(NewsDataBase::class) {
    //                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
    //                        NewsDataBase::class.java, DB_NAME)
    //                        .build()
    //           }
    //        }
    //       return INSTANCE
    //    }

        fun destroyInstance() {
            INSTANCE = null
        }
    }