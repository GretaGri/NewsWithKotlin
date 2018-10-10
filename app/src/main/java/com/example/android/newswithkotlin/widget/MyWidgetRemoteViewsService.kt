package com.example.android.newswithkotlin.widget

import android.content.Intent
import android.util.Log
import android.widget.RemoteViewsService


class MyWidgetRemoteViewsService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        Log.d("my_tag", "onGetViewFactory called")
        return FavoriteWidgetRemoteViewsFactory(this.applicationContext)
    }
}