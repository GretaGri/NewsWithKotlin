package com.example.android.newswithkotlin.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.example.android.newswithkotlin.R
import com.example.android.newswithkotlin.database.News


class FavoriteNewsWidgetProvider : AppWidgetProvider() {

    private lateinit var newsList: ArrayList<News>

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager,
                          appWidgetIds: IntArray) {
        Log.d("my_tag", "onUpdate called")


        // Get all ids
        val favoriteWidget = ComponentName(context,
                FavoriteNewsWidgetProvider::class.java)
        val allWidgetIds = appWidgetManager.getAppWidgetIds(favoriteWidget)


        Log.v("my_tag", "appWidgetIds size is: " + allWidgetIds.size)

        for (widgetId in allWidgetIds) {
            // create some random data
            val widgetTitle = "This is favorite widget"

            val remoteViews = RemoteViews(context.getPackageName(),
                    R.layout.widget_layout)
            Log.v(TAG, widgetTitle)
            // Set the text
            remoteViews.setTextViewText(R.id.widget_title_text_view, widgetTitle)

            // Add an onClickListener on widget to perform some action
            val intent = Intent(context, FavoriteNewsWidgetProvider::class.java)

            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)

            intent.putParcelableArrayListExtra("newsList", newsList)
            Log.d("my_tag", "newsList size inside onUpdate is: " + newsList.size)
            val pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            remoteViews.setOnClickPendingIntent(R.id.widget_title_text_view, pendingIntent)
            appWidgetManager.updateAppWidget(widgetId, remoteViews)
        }

    }


    override fun onReceive(context: Context, intent: Intent) {
        Log.d("my_tag", "onReceive called")
        val action = intent.action
        if (action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            // refresh all your widgets
            //get newsFromFavoriteActivity and pass to remote factory
            newsList = intent.getParcelableArrayListExtra<News>("newsList")
            Log.d("my_tag", "getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS) size inside onReceive is: " + intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS).size)
            this.onUpdate(context, AppWidgetManager.getInstance(context), intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS));
        }
        super.onReceive(context, intent)
    }

    companion object {

        //private val TAG = FavoriteNewsWidgetProvider::class.java.simpleName
        private val TAG = "my_tag"
    }
}