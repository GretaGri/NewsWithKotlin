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


class FavoriteWidgetRemoteViewsFactory(val mContext: Context,
                                       var newsList: ArrayList<News>? = null) : RemoteViewsService.RemoteViewsFactory {

    lateinit var mFavouriteNewsWidgetArrayList: ArrayList<News>
    private var mDb: AppDatabase? = null

    init {
        Log.d(TAG, "Context : $mContext")
    }

    override fun onCreate() {
        mDb = AppDatabase.getInstance(mContext)
        mFavouriteNewsWidgetArrayList = ArrayList()
        Log.d("my_tag", "FavoriteWidgetRemoteViewsFactory onCreate called")
        getDummyData()
    }

    private fun getDummyData() {
        newsList = ArrayList()
        doAsync {
            val result = mDb?.newsDao()?.loadAllNewsArrayListFromDatabase() as ArrayList<News>
            Log.d("my_tag", "newsList size inside appExecutor is: " + mFavouriteNewsWidgetArrayList!!.size)
            uiThread {
                mFavouriteNewsWidgetArrayList = result
                Log.d("my_tag", "newsList size inside uiThread is: " + mFavouriteNewsWidgetArrayList!!.size)
            }
            onDataSetChanged()
        }

    }

    override fun onDataSetChanged() {
        Log.d("my_tag", "newsList size inside onDataSetChanged is: " + newsList!!.size)
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int {
        Log.d("my_tag", "newsList size inside onDataSetChanged is: " + newsList!!.size)
        return if (mFavouriteNewsWidgetArrayList.isEmpty()) {
            0
        } else mFavouriteNewsWidgetArrayList.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(mContext.packageName,
                R.layout.widget_list_item)
        val favoriteNews = mFavouriteNewsWidgetArrayList[position]
        remoteViews.setTextViewText(R.id.news_title_widget_text_view, favoriteNews.title)
        Log.d(TAG, "getViewAt setTextViewText : " + favoriteNews.id)
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
        //private val TAG = FavoriteWidgetRemoteViewsFactory::class.java.simpleName
        private val TAG = "my_tag"
    }
}