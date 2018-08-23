package com.example.android.newswithkotlin

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors(val diskIO: Executor, val networkIO: Executor, val mainThread: Executor) {
    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    companion object {
        // For Singleton instantiation
        private val LOCK = Any()
        private var sInstance: AppExecutors? = null

        val instance: AppExecutors
            get() {
                if (sInstance == null) {
                    synchronized(LOCK) {
                        sInstance = AppExecutors(Executors.newSingleThreadExecutor(),
                                Executors.newFixedThreadPool(3),
                                MainThreadExecutor())
                    }
                }
                return sInstance!!
            }
    }
}