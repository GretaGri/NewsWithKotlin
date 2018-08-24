package com.example.android.newswithkotlin.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(News::class, ContributorContent::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {

        private val LOG_TAG = AppDatabase::class.java.simpleName
        private val LOCK = Any()
        private val DATABASE_NAME = "newsdatabase"
        private var sInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase::class.java, AppDatabase.DATABASE_NAME)
                            .build()
                }
            }
            return sInstance!!
        }
    }

}