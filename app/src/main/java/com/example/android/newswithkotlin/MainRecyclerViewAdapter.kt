package com.example.android.newswithkotlin

import android.content.Context
import android.os.Handler
import android.os.Looper.getMainLooper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.android.newswithkotlin.database.AppDatabase
import com.example.android.newswithkotlin.database.ContributorContent
import com.example.android.newswithkotlin.database.News
import kotlinx.android.synthetic.main.news_list_item.view.*


class MainRecyclerViewAdapter(var items: ArrayList<News>,
                              val context: Context,
                              var favNewsList: ArrayList<News>) :
        RecyclerView.Adapter<MainRecyclerViewAdapter.MyListViewHolder>() {

    override fun onBindViewHolder(holder: MyListViewHolder, position: Int) {
        holder.bindList(items[position], context, favNewsList)
    }


    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyListViewHolder {
        return MyListViewHolder(LayoutInflater.from(context).inflate(R.layout.news_list_item, parent,
                false))
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
        fun bindList(item: News, context: Context, favNewsFromDatabase: ArrayList<News>) {
            mDb = AppDatabase.getInstance(context)
            textViewNewsTitle?.text = item.title

            //need to handle author's part as it's not getting initialized properly
            if (item.tags.size > 0) {
                textViewAuthorTitle?.text = item.tags[0].title
            }


            var isFav = false
            var favNews = News()
            for (news in favNewsFromDatabase) {
                if (news.webUrl.equals(item.webUrl)) {
                    isFav = true
                    favNews = news
                    favButton.setImageResource(R.drawable.ic_favorite_red_24dp)
                    break
                } else {
                    isFav = false
                    favButton.setImageResource(R.drawable.ic_favorite_border_red_24dp)
                }
            }
            favButton.setOnClickListener { view ->
                //deleteNews(item, isFav, favButton, item.tags)
                saveOrDeleteNews(item, isFav, favNews, favButton, item.tags)
            }
            textViewNewsWebUrl?.text = item.webUrl

        }

        private fun saveOrDeleteNews(item: News, isFav: Boolean, favNews: News, imgButton: ImageButton, authorList: ArrayList<ContributorContent>) {
            if (isFav) {
                AppExecutors.instance.diskIO.execute(Runnable {
                    if (authorList.size > 0)
                        mDb?.newsDao()?.deleteNewsAuthors(favNews.tags[0])
                    mDb?.newsDao()?.deleteNews(favNews)
                    val h = Handler(getMainLooper())
                    h.post(Runnable {
                        imgButton.setImageResource(R.drawable.ic_favorite_border_red_24dp)
                    })
                })
            } else {
                AppExecutors.instance.diskIO.execute(Runnable {
                    mDb?.newsDao()?.insertNews(item)
                    if (item.tags.size > 0 && !item.tags.get(0).apiUrl.isEmpty())
                        mDb?.newsDao()?.insertAuthorsForNews(item.tags[0])
                    else
                        mDb?.newsDao()?.insertAuthorsForNews(ContributorContent())

                    val h = Handler(getMainLooper())
                    h.post(Runnable {
                        imgButton.setImageResource(R.drawable.ic_favorite_red_24dp)
                    })
                })

            }
        }
    }
}
