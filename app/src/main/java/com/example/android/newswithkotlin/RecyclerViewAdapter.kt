package com.example.android.newswithkotlin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
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
                            Log.v("my_tagggg", "For authors: idContributor = " + author.idContributor + "---title is: " + author.title + "---apiUrl is: " + author.apiUrl)
                        }
                    }
                })
            }
            textViewNewsWebUrl?.text = item.webUrl
            Log.v("my_tagggg", "For news: id = " + item.id + "---title is: " + item.title + "---webUrl is: " + item.webUrl)
            favButton.setOnClickListener { view ->
                saveOrDeleteNews(item, item.tags, isFav, favButton, context)
            }
            //this code below needs to run outside of AppExecutors
            //favButton.setImageResource(R.drawable.ic_favorite_red_24dp)
        }

        private fun saveOrDeleteNews(item: News, authors: ArrayList<ContributorContent>, isFav: Boolean, favButton: ImageButton, context: Context) {

            AppExecutors.instance.diskIO.execute(Runnable {

                if (!isFav) {
                    //add the news and the author setails to the database on fav click
                    mDb?.newsDao()?.insertNews(item)
                    Log.v("my_tagggg", "For news: id = " + item.id + "---title is: " + item.title + "---webUrl is: " + item.webUrl)
                    if (authors.size > 0) {
                        mDb?.newsDao()?.insertAuthorsForNews(authors.get(0))
                        Log.v("my_tagggg", "For authors: idContributor = " + authors.get(0).idContributor + "---title is: " + authors.get(0).title + "---apiUrl is: " + authors.get(0).apiUrl)
                    }

                    Log.v("my_taggg", "\n")
                } else {
                    mDb?.newsDao()?.deleteNews(item)
                }
            })
        }
    }
}
