package com.example.android.newswithkotlin

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors


/**
 * Created by Greta Grigutė on 2018-08-26.
 * Global executor pools for the whole application.
 *
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
class AppExecutors private constructor(private val diskIO: Executor, private val networkIO: Executor, private val mainThread: Executor) {

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    companion object {
        //for Singleton instantiation
        //Executor is an object that executes submitted runnable tasks. Executor is normally used
        //instead of explicitly creating threads.

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

    fun diskIO(): Executor {
        return diskIO
    }

    fun networkIO(): Executor {
        return networkIO
    }

    fun mainThread(): Executor {
        return mainThread
    }
}