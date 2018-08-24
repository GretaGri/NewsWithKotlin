package com.example.android.newswithkotlin

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.android.newswithkotlin.database.AppDatabase
import com.example.android.newswithkotlin.database.News

class FavoriteActivity : AppCompatActivity() {


    private var mDb: AppDatabase? = null
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle("Favorite")
        mDb = AppDatabase.getInstance(applicationContext)


        //initialize views
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerViewAdapter(ArrayList<News>(), this)


        setupViewModel()
    }

    private fun setupViewModel() {
        val viewModel = ViewModelProviders.of(this).get(AllNewsViewModel::class.java)
        viewModel.newses.observe(this, object : Observer<List<News>> {
            override fun onChanged(newsEntries: List<News>?) {
                setupRecyclerView(this@FavoriteActivity, newsEntries!!)
            }
        })
    }

    fun setupRecyclerView(context: Context, listOfNews: List<News>) {
        recyclerView.visibility = View.VISIBLE
        val arrayListOfNews: ArrayList<News> = ArrayList()

        for (item in listOfNews) {
            arrayListOfNews.add(item)
        }
        recyclerView.adapter = RecyclerViewAdapter(arrayListOfNews, context)
    }
}
