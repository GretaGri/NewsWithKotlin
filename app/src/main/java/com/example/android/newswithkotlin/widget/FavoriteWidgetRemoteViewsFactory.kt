package com.example.android.newswithkotlin.widget

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.android.newswithkotlin.DummyData
import com.example.android.newswithkotlin.R
import java.util.*
import kotlin.collections.ArrayList


class FavoriteWidgetRemoteViewsFactory(val mContext: Context,
                                       var newsList: ArrayList<DummyData>? = null) : RemoteViewsService.RemoteViewsFactory {

    lateinit var mFavouriteNewsWidgetArrayList: ArrayList<DummyData>

    init {
        Log.d(TAG, "Context : $mContext")
    }

    override fun onCreate() {
        mFavouriteNewsWidgetArrayList = ArrayList()
        Log.d("my_tag", "FavoriteWidgetRemoteViewsFactory onCreate called")
        mFavouriteNewsWidgetArrayList = getDummyData()
    }

    private fun getDummyData(): ArrayList<DummyData> {
        val setting = mContext.getSharedPreferences("id", Context.MODE_PRIVATE)
        val id = setting.getInt("id", 1)

        var currentValue = 0
        val newsListAdded = ArrayList<DummyData>()
        while (currentValue <= id) {
            val random = Random()
            val randomNum = random.nextInt(2) + 50
            val dummyData = DummyData(randomNum)
            newsListAdded.add(dummyData)
            currentValue = currentValue + 1
        }
        val settings: SharedPreferences = mContext.getSharedPreferences("id", Context.MODE_PRIVATE);
        val editor: SharedPreferences.Editor = settings.edit();
        editor.putInt("id", newsListAdded.size + 1);
        editor.commit()
        this.onDataSetChanged()
        return newsListAdded
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
        val remoteViews = RemoteViews(mContext.packageName,
                R.layout.widget_layout)
        val favoriteNews = mFavouriteNewsWidgetArrayList[position]
        remoteViews.setTextViewText(R.id.news_title_widget_text_view, "id is: " + favoriteNews.id)
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