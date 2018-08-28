package com.example.android.newswithkotlin

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import com.example.android.newswithkotlin.GetFavoriteNewsFromDatabaseFragment.FavoriteNewsFetchingRequestListener
import com.example.android.newswithkotlin.database.AppDatabase
import com.example.android.newswithkotlin.database.News
import kotlinx.android.synthetic.main.main_layout.*

class FavoriteActivity : AppCompatActivity(), FavoriteNewsFetchingRequestListener {

    override fun onNewsFetchCallMade(newsList: ArrayList<News>) {
        setupRecyclerView(this@FavoriteActivity, newsList)
    }

    private var mDb: AppDatabase? = null
    lateinit var recyclerView: RecyclerView
    lateinit var emptyView: TextView
    lateinit var queriedForTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle("Favorite")

        //set an actionBar as the theme is NoActionBar and implement UpNavigation
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //initialize Database instance
        mDb = AppDatabase.getInstance(applicationContext)
        fab.visibility = View.GONE

        //initialize views
        recyclerView = findViewById(R.id.recycler_view)
        emptyView = findViewById(R.id.empty_view)
        queriedForTextView = findViewById(R.id.queried_for_text_view)

        /*
        hide emptyView and queriedFor textView as this is not needed for favorite movie,
        in case there is some data present
        */
        emptyView.visibility = View.GONE
        queriedForTextView.visibility = View.GONE

        //setup recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FavoriteRecyclerViewAdapter(ArrayList<News>(), this)
        handleDatabase()
    }

    fun setupRecyclerView(context: Context, listOfNews: ArrayList<News>) {
        recyclerView.visibility = View.VISIBLE
        recyclerView.adapter = FavoriteRecyclerViewAdapter(listOfNews, context)
    }

    private fun handleDatabase() {
        val searchDatabaseFragment = GetFavoriteNewsFromDatabaseFragment()
        supportFragmentManager
                .beginTransaction()
                .add(R.id.favorite_search_fragment, searchDatabaseFragment)
                .commit()
    }
}