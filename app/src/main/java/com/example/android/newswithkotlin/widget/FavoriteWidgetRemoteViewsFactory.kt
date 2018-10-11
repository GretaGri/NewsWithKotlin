package com.example.android.newswithkotlin.widget

import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.android.newswithkotlin.R
import com.example.android.newswithkotlin.database.AppDatabase
import com.example.android.newswithkotlin.database.News
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class FavoriteWidgetRemoteViewsFactory(val mContext: Context) : RemoteViewsService.RemoteViewsFactory {

    lateinit var mFavouriteNewsWidgetArrayList: ArrayList<News>
    private var mDb: AppDatabase? = null

    init {
        Log.d(TAG, "Context : $mContext")
    }

    override fun onCreate() {
        mDb = AppDatabase.getInstance(mContext)
        mFavouriteNewsWidgetArrayList = ArrayList()
        Log.d("my_tag", "FavoriteWidgetRemoteViewsFactory onCreate called")
        getWidgetData()
    }

    private fun getWidgetData() {
        doAsync {
            val result = mDb?.newsDao()?.loadAllNewsArrayListFromDatabase() as ArrayList<News>
            uiThread {
                mFavouriteNewsWidgetArrayList = result
                Log.d("my_tag", "mFavouriteNewsWidgetArrayList size inside uiThread is: " + mFavouriteNewsWidgetArrayList.size)
            }
            onDataSetChanged()
        }

    }

    override fun onDataSetChanged() {
        Log.d("my_tag", "mFavouriteNewsWidgetArrayList size inside onDataSetChanged is: " + mFavouriteNewsWidgetArrayList!!.size)
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int {
        return if (mFavouriteNewsWidgetArrayList.isEmpty()) {
            0
        } else mFavouriteNewsWidgetArrayList.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(mContext.packageName,
                R.layout.widget_list_item)
        val favoriteNews = mFavouriteNewsWidgetArrayList[position]
        remoteViews.setTextViewText(R.id.news_title_widget_text_view, favoriteNews.title)
        Log.d(TAG, "getViewAt setTextViewText : " + favoriteNews.title)
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