package com.example.android.newswithkotlin

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews


class FavoriteNewsWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager,
                          appWidgetIds: IntArray) {

        // Get all ids
        val favoriteWidget = ComponentName(context,
                FavoriteNewsWidgetProvider::class.java)
        val allWidgetIds = appWidgetManager.getAppWidgetIds(favoriteWidget)
        for (widgetId in allWidgetIds) {
            // create some random data
            val dummyData = "This is some dummy data"

            val remoteViews = RemoteViews(context.getPackageName(),
                    R.layout.widget_layout)
            Log.v(TAG, dummyData)
            // Set the text
            remoteViews.setTextViewText(R.id.update, dummyData)

            // Add an onClickListener on widget to perform some action
            val intent = Intent(context, FavoriteNewsWidgetProvider::class.java)

            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)

            val pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent)
            appWidgetManager.updateAppWidget(widgetId, remoteViews)
        }
    }

    companion object {

        private val TAG = FavoriteNewsWidgetProvider::class.java.simpleName
    }
}