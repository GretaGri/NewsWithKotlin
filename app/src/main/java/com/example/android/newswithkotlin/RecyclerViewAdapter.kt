package com.example.android.newswithkotlin

import android.arch.lifecycle.LiveData
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.android.newswithkotlin.database.News
import com.example.android.newswithkotlin.database.NewsDataBase
import kotlinx.android.synthetic.main.news_list_item.view.*

class RecyclerViewAdapter(val items: ArrayList<News>, val context: Context) : RecyclerView.Adapter<RecyclerViewAdapter.MyListViewHolder>() {
    override fun onBindViewHolder(holder: MyListViewHolder, position: Int) {
        holder.bindList(items[position], context)
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyListViewHolder {
        return MyListViewHolder(LayoutInflater.from(context).inflate(R.layout.news_list_item, parent, false))
    }

    // Gets the number of items in the list
    override fun getItemCount(): Int {
        return items.size
    }

    open class MyListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each item to recyclerView
        val dummyTextViewTitle = view.text_view_title
        val dummyTextViewAuthor = view.text_view_author
        val dummyTextViewWebUrl = view.text_view_web_url
        val favButton: ImageButton = view.fav_image_button
        // Create NewsDataBase member variable for the Database
        private var mDb: NewsDataBase? = null

        fun bindList(item: News, context: Context) {
            //initiate Database
            mDb = NewsDataBase.getInstance(context)

            dummyTextViewTitle?.text = item.title

            //to handle author's part
            if (item.tags.size > 0)
                dummyTextViewAuthor?.text = item.tags[0].title
            dummyTextViewWebUrl?.text = item.webUrl

            if (item.favorite == 0) favButton.setImageResource(R.drawable.ic_favorite_border_red_24dp)
            if (item.favorite == 1) favButton.setImageResource(R.drawable.ic_favorite_red_24dp)

            //add item to favorites on fav button click
            favButton.setOnClickListener { view ->
                AppExecutors.instance.diskIO.execute(Runnable {
                    //add the news together with the author details to the database on fav click
                    val itemFavoriteValue: Int? = mDb?.newsDao()?.loadTaskByTitle(item.title)
                    Log.d("My tag", "item favorite value is: ${itemFavoriteValue}")
                    if (itemFavoriteValue == 1) {
                        AppExecutors.instance.mainThread.execute(Runnable {
                            item.favorite = 0
                            Log.d("My tag", "item content is:" + item.toString())
                        })

                    }
                    if (itemFavoriteValue == 0) {
                        AppExecutors.instance.mainThread.execute(Runnable {
                            item.favorite = 1
                            Log.d("My tag", "item content is:" + item.toString())
                        })
                    }
                    mDb?.newsDao()?.updateNews(item)
                })
            }
        }
    }
}

