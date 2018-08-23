package com.example.android.newswithkotlin

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.example.android.newswithkotlin.database.AppDatabase
import com.example.android.newswithkotlin.database.News

class FavoriteActivity : AppCompatActivity() {


    private var mDb: AppDatabase? = null
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDb = AppDatabase.getInstance(applicationContext)


        //initialize views
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerViewAdapter(ArrayList<News>(), this)


        setupViewModel()
    }

    private fun setupViewModel() {
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.newses.observe(this, object : Observer<List<News>> {
            override fun onChanged(newsEntries: List<News>?) {
                Log.v("my_tag", "onChanged called")
                setupRecyclerView(this@FavoriteActivity, newsEntries!!, "")
            }
        })
    }

    fun setupRecyclerView(context: Context, listOfNews: List<News>, usersQuery: String) {
        recyclerView.visibility = View.VISIBLE
        Log.v("my_tag", "size of list is: " + listOfNews.size)
        val arrayList: ArrayList<News> = ArrayList()
        for (item in listOfNews) {
            arrayList?.add(item)
        }
        recyclerView.adapter = RecyclerViewAdapter(arrayList!!, context)
    }
}
