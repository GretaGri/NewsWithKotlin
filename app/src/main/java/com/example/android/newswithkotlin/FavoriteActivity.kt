package com.example.android.newswithkotlin

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.android.newswithkotlin.database.News
import com.example.android.newswithkotlin.database.NewsDataBase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.util.Log


class FavoriteActivity : AppCompatActivity() {
    private var mDb: NewsDataBase? = null
    lateinit var recyclerView: RecyclerView
    lateinit var emptyView: TextView
    lateinit var queriedForTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // my_child_toolbar is defined in the layout file
        setSupportActionBar(findViewById(R.id.toolbar))

        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // set title Favorites
        supportActionBar?.setTitle(R.string.favorites)

        //initialize views and hide unnecessary views
        emptyView = findViewById(R.id.empty_view)
        emptyView.visibility = View.GONE
        queriedForTextView = findViewById(R.id.queried_for_text_view)
        queriedForTextView.visibility = View.GONE
        fab.visibility = View.GONE

        //initialize Database instance
        mDb = NewsDataBase.getInstance(applicationContext)

        //set up RecyclerView with the list of favorite items form DB.
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerViewAdapter(ArrayList<News>(), this)
        setupViewModel()


    }

    fun setupViewModel(){
        val viewModel = ViewModelProviders.of(this).get(MainNewsViewModel::class.java!!)
        viewModel.news.observe(this, object : Observer<List<News>> {
            override fun onChanged(news: List<News>?) {
                Log.d("My tag", "Updating list of news from LiveData in MainNewsViewModel")
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
