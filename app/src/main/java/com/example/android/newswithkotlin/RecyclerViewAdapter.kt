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
                          val context: Context) : RecyclerView.Adapter<RecyclerViewAdapter.MyListViewHolder>() {

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

        companion object {
            // Extra for the task ID to be received in the intent
            val EXTRA_TASK_ID = "extraTaskId"
            // Extra for the task ID to be received after rotation
            val INSTANCE_TASK_ID = "instanceTaskId"
        }


        // Constant for default task id to be used when not in update mode
        val DEFAULT_TASK_ID = -1
        var mTaskId = DEFAULT_TASK_ID

        // Create AppDatabase member variable for the Database
        // Member variable for the Database
        private var mDb: AppDatabase? = null

        // Holds the TextView that will add each item to recyclerView
        val dummyTextViewTitle = view.text_view_title
        val dummyTextViewAuthor = view.text_view_author
        val dummyTextViewWebUrl = view.text_view_web_url
        val favButton: ImageButton = view.fav_image_button
        fun bindList(item: News, context: Context) {
            dummyTextViewTitle?.text = item.title

            //need to handle author's part as it's not getting initialized properly
            if (item.tags.size > 0)
                dummyTextViewAuthor?.text = item.tags[0].title
            dummyTextViewWebUrl?.text = item.webUrl

            favButton.setOnClickListener { view ->
                saveNews(item, item.tags)
            }
        }

        private fun saveNews(item: News, authors: ArrayList<ContributorContent>) {
            AppExecutors.instance.diskIO.execute(Runnable {
                //for now only add the news to the database on fav click
                Log.v("my_tag", "insert called")
                mDb?.newsDao()?.insertNews(item)
                if (authors.size > 0)
                    mDb?.newsDao()?.insertAuthorsForNews(authors.get(0))
                else
                    mDb?.newsDao()?.insertAuthorsForNews(ContributorContent("webTitle", "apiUrl"))
            })
        }
    }
}
