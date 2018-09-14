package com.example.android.newswithkotlin.widget

import android.content.Intent
import android.widget.RemoteViewsService


class MyWidgetRemoteViewsService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return FavoriteWidgetRemoteViewsFactory(this.applicationContext,
                intent.getParcelableArrayListExtra("newsList"))
    }
}