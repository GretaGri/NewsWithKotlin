package com.example.android.newswithkotlin.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.android.newswithkotlin.R


class FavoriteNewsWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager,
                          appWidgetIds: IntArray) {
        // Get all ids
        val favoriteWidget = ComponentName(context,
                FavoriteNewsWidgetProvider::class.java)
        val allWidgetIds = appWidgetManager.getAppWidgetIds(favoriteWidget)
        for (widgetId in allWidgetIds) {
            val remoteViews = RemoteViews(context.getPackageName(),
                    R.layout.widget_layout)
            val serviceIntent = Intent(context, MyWidgetRemoteViewsService::class.java)
            remoteViews.setRemoteAdapter(R.id.widget_list_view, serviceIntent)
            appWidgetManager.notifyAppWidgetViewDataChanged(widgetId, R.id.widget_list_view)
            appWidgetManager.updateAppWidget(widgetId, remoteViews)
        }
    }


    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            // refresh all your widgets
            this.onUpdate(context, AppWidgetManager.getInstance(context), intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS));
        }
        super.onReceive(context, intent)
    }

    companion object {

        //private val TAG = FavoriteNewsWidgetProvider::class.java.simpleName
        private val TAG = "my_tag"
    }
}