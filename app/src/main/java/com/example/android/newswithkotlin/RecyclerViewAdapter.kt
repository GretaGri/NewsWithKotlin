package com.example.android.newswithkotlin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.android.newswithkotlin.database.AppDatabase
import com.example.android.newswithkotlin.database.ContributorContent
import com.example.android.newswithkotlin.database.News
import kotlinx.android.synthetic.main.news_list_item.view.*

class RecyclerViewAdapter(val items: ArrayList<News>,
                          val context: Context, val isFav: Boolean = false) : RecyclerView.Adapter<RecyclerViewAdapter.MyListViewHolder>() {
    override fun onBindViewHolder(holder: MyListViewHolder, position: Int) {

        holder.bindList(items[position], context, isFav)
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

        // Create AppDatabase member variable for the Database
        // Member variable for the Database
        private var mDb: AppDatabase? = null


        // Holds the TextView that will add each item to recyclerView
        val textViewNewsTitle = view.text_view_title
        val textViewAuthorTitle = view.text_view_author
        val textViewNewsWebUrl = view.text_view_web_url
        val favButton: ImageButton = view.fav_image_button
        fun bindList(item: News, context: Context, isFav: Boolean) {
            mDb = AppDatabase.getInstance(context)
            textViewNewsTitle?.text = item.title

            //need to handle author's part as it's not getting initialized properly
            if (item.tags.size > 0) {
                textViewAuthorTitle?.text = item.tags[0].title
            } else {
                AppExecutors.instance.diskIO.execute(Runnable {
                    for (author in (mDb?.newsDao()?.getAuthorsForNews(item.id)!!)) {

                        if (!((mDb?.newsDao()?.getAuthorsForNews(item.id)!!).isEmpty())) {
                            textViewAuthorTitle?.text = (mDb?.newsDao()?.getAuthorsForNews(item.id)!!).get(0).title
                        }
                    }
                })
            }
            textViewNewsWebUrl?.text = item.webUrl

            favButton.setOnClickListener { view ->
                saveOrDeleteNews(item, item.tags, isFav)
            }
        }

        private fun saveOrDeleteNews(item: News, authors: ArrayList<ContributorContent>, isFav: Boolean) {

            AppExecutors.instance.diskIO.execute(Runnable {

                if (!isFav) {
                    //add the news and the author setails to the database on fav click
                    mDb?.newsDao()?.insertNews(item)
                    if (authors.size > 0)
                        mDb?.newsDao()?.insertAuthorsForNews(authors.get(0))

                } else {
                    mDb?.newsDao()?.deleteNews(item)
                }
            })
        }
    }
}
