package com.example.android.newswithkotlin

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.android.newswithkotlin.database.News
import com.example.android.newswithkotlin.database.NewsDataBase
import kotlinx.android.synthetic.main.activity_main.*


class FavoriteActivity : AppCompatActivity() {
   // private var mDb: NewsDataBase? = null
    private var news: List<News>? = null
    lateinit var recyclerView: RecyclerView
    lateinit var emptyView: TextView
    lateinit var queriedForTextView: TextView
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize views and hide unnecessary views
        toolbar = findViewById(R.id.toolbar)
        toolbar.visibility = View.GONE
        emptyView = findViewById(R.id.empty_view)
        emptyView.visibility = View.GONE
        queriedForTextView = findViewById(R.id.queried_for_text_view)
        queriedForTextView.visibility = View.GONE
        fab.visibility = View.GONE

        //initialize Database instance
        AppExecutors.instance.diskIO.execute(Runnable {news = NewsDataBase.getInstance(applicationContext)
                .newsDao().loadFavoritesInAList(1)})

        //set up RecyclerView with the list of favorite items form DB.
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
       //Need to get favorite news from db..

        setupViewModel()
    }

    fun setupViewModel(){
        val viewModel = ViewModelProviders.of(this).get(FavoriteNewsViewModel::class.java!!)
        viewModel.news.observe(this, object : Observer<List<News>> {
            override fun onChanged(news: List<News>?) {
                Log.d("My tag", "Updating list of news from LiveData in FavoriteNewsViewModel")

                setupRecyclerView(this@FavoriteActivity, news)
            }
        })
    }
    fun setupRecyclerView(context: Context, listOfNews: List<News>?) {
        val arrayListOfNews: ArrayList<News> = ArrayList()
        if(listOfNews!=null){
        for (item in listOfNews) {
            arrayListOfNews.add(item)
        }
        }
        recyclerView.adapter = RecyclerViewAdapter(arrayListOfNews, context)
    }
}
