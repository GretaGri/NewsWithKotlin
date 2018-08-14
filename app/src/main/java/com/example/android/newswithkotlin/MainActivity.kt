package com.example.android.newswithkotlin

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    // declare recyclerView instance with lateinit so that it can be handled without any NPE
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Toast.makeText(this, "Sorry, this feature is not available", Toast.LENGTH_SHORT).show()
            //Suggestion to use Anko extension to simplify some code - toast is one of the examples: https://github.com/Kotlin/anko
            // TODO: Open search activity
        }

        //TODO: Add content to recyclerView.(kt files needed: POJO - News, RecyclerViewAdapter,
        // TODO: AsyncTaskLoader, QueryUtils (future dev: SettingsActivity, SearchActivity))

        //Make a method call to fetch data from the Guardian api using Retrofit and Gson and to setup RecyclerView alongwith
        recyclerView = findViewById(R.id.recycler_view)
        fetchDataFromGuardianUsingRetrofit()
    }

    private fun fetchDataFromGuardianUsingRetrofit() {
        val apiInterface = APIClient.client.create(APIInterface::class.java)


        /**
        GET List of news
         **/
        val call = apiInterface.getNewsList("results")


        call.enqueue(object : Callback<GsonNewsResponse> {
            override fun onResponse(call: Call<GsonNewsResponse>, response: Response<GsonNewsResponse>?) {
                val resource = response?.body()
                this@MainActivity.runOnUiThread {
                    setupRecyclerView(applicationContext, resource?.response?.newsItem!!)

                }
            }

            override fun onFailure(call: Call<GsonNewsResponse>, t: Throwable?) {
                Log.v("my_tag", "fail message is: " + t?.message)
            }
        })
    }

    fun setupRecyclerView(context: Context, listOfNews: ArrayList<News>) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = RecyclerViewAdapter(listOfNews, context)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, "Sorry, this feature is not available",
                        Toast.LENGTH_SHORT).show();
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
