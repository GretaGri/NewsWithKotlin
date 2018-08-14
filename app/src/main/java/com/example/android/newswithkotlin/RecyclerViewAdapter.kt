package com.example.android.newswithkotlin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.news_list_item.view.*

class RecyclerViewAdapter(val items: ArrayList<News>, val context: Context) : RecyclerView.Adapter<RecyclerViewAdapter.MyListViewHolder>() {
    override fun onBindViewHolder(holder: MyListViewHolder, position: Int) {
        holder.bindList(items[position])
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
        fun bindList(item: News) {
            dummyTextViewTitle?.text = item.title

            //need to handle author's part as it's not getting initialized properly
            if (item.tags.size > 0)
                dummyTextViewAuthor?.text = item.tags[0].title
            dummyTextViewWebUrl?.text = item.webUrl
        }
    }
}