package com.example.android.newswithkotlin.widget

import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.support.annotation.Nullable
import android.util.Log

class FetchWidgetDataFromBackgroundService : Service {

    companion object {
        val TAG: String = FetchWidgetDataFromBackgroundService::class.java.simpleName
    }

    constructor() {}

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.v("my_tag", "FetchWidgetDataFromBackgroundService onStartCommand called")
        sendDatabaseChangedBroadcast(this)
        return START_STICKY
    }

    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
    }

    fun sendDatabaseChangedBroadcast(context: Context) {
        val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        val appWidgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(ComponentName(context,
                FavoriteNewsWidgetProvider::class.java))
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)
        context.sendBroadcast(intent)
    }
}