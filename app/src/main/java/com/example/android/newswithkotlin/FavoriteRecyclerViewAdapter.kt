package com.example.android.newswithkotlin


import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.support.constraint.ConstraintLayout
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.android.newswithkotlin.database.AppDatabase
import com.example.android.newswithkotlin.database.News
import kotlinx.android.synthetic.main.news_list_item.view.*


class FavoriteRecyclerViewAdapter(val items: ArrayList<News>,
                                  val context: Context) : RecyclerView.Adapter<FavoriteRecyclerViewAdapter.MyListViewHolder>() {

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

        // Create AppDatabase member variable for the Database
        // Member variable for the Database
        private var mDb: AppDatabase? = null
        // Holds the TextView that will add each item to recyclerView
        val textViewNewsTitle = view.text_view_title
        val textViewAuthorTitle = view.text_view_author
        val textViewNewsWebUrl = view.text_view_web_url
        val favButton: ImageButton = view.fav_image_button
        //handle news details
        val newsListItem: ConstraintLayout = view.news_list_item

        fun bindList(item: News, context: Context) {
            mDb = AppDatabase.getInstance(context)
            textViewNewsTitle?.text = item.title
            //need to handle author's part
            if (item.tags.size > 0) {
                textViewAuthorTitle?.text = item.tags[0].title
            }
            textViewNewsWebUrl?.text = item.webUrl
            favButton.setImageResource(R.drawable.ic_favorite_red_24dp)
            favButton.setOnClickListener {
                deleteNewsFromDatabase(item)
            }
            newsListItem.setOnClickListener {
                setupNewsDetailsActivity(context, item.webUrl, item.title)
            }
        }

        private fun setupNewsDetailsActivity(context: Context, itemUrl: String, itemTitle: String) {
            val newsDetailsActivity = Intent(context, NewsDetailsActivity::class.java)
            newsDetailsActivity.putExtra("newsUrl", itemUrl)
            newsDetailsActivity.putExtra("newsTitle", itemTitle)
            context.startActivity(newsDetailsActivity)
        }

        private fun deleteNewsFromDatabase(item: News) {
            AppExecutors.instance.diskIO.execute {
                mDb?.newsDao()?.deleteNews(item)
                val h = Handler(Looper.getMainLooper())
                h.post {
                    favButton.setImageResource(R.drawable.ic_favorite_border_red_24dp)
                }
            }
        }
    }

    fun onNewData(newData: ArrayList<News>) {

        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(newData, items))
        this.items.clear()
        this.items.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

}
