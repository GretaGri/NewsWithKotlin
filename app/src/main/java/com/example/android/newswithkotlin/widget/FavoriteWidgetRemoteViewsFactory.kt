package com.example.android.newswithkotlin.widget

import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.android.newswithkotlin.R
import com.example.android.newswithkotlin.database.News


class FavoriteWidgetRemoteViewsFactory(val mContext: Context,
                                       val newsList: ArrayList<News>) : RemoteViewsService.RemoteViewsFactory {

    lateinit var mFavouriteNewsWidgetArrayList: ArrayList<News>


    init {
        Log.d(TAG, "Context : $mContext")
    }

    override fun onCreate() {
        Log.d("my_tag", "factory called")
        Log.d("my_tag", "factory size is: " + mFavouriteNewsWidgetArrayList.size)
        mFavouriteNewsWidgetArrayList = newsList
    }

    override fun onDataSetChanged() {

    }

    override fun onDestroy() {

    }

    override fun getCount(): Int {
        return if (mFavouriteNewsWidgetArrayList.isEmpty()) {
            0
        } else mFavouriteNewsWidgetArrayList.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        Log.d("my_tag", "getViewAt called")
        val remoteViews = RemoteViews(mContext.packageName,
                R.layout.widget_layout)

        Log.d(TAG, "getViewAt Favorite news Size : " + mFavouriteNewsWidgetArrayList.size)

        val favoriteNews = mFavouriteNewsWidgetArrayList[position]

        remoteViews.setTextViewText(R.id.news_title_widget_text_view, favoriteNews.title)

        Log.d(TAG, "getViewAt news title : " + favoriteNews.title)

        return remoteViews
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    companion object {

        private val TAG = FavoriteWidgetRemoteViewsFactory::class.java.simpleName
    }
}