package com.example.android.newswithkotlin

import android.app.DialogFragment
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.android.newswithkotlin.database.GsonNewsResponse
import com.example.android.newswithkotlin.database.News
import kotlinx.android.synthetic.main.main_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), SearchDialogFragment.userQueryListener {

    // declare recyclerView instance with lateinit so that it can be handled without any NPE
    lateinit var recyclerView: RecyclerView
    lateinit var emptyView: TextView
    lateinit var queriedForTextView: TextView
    lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        setSupportActionBar(toolbar)

        //initialize views
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerViewAdapter(ArrayList<News>(), this)

        emptyView = findViewById(R.id.empty_view)
        queriedForTextView = findViewById(R.id.queried_for_text_view)
        progressBar = findViewById(R.id.progress_bar)

        //if there is no news data from earlier time, show the dialog and ask for users input
        if (savedInstanceState == null) {
            showDialogFragment()
        }
        fab.setOnClickListener { view ->
            showDialogFragment()
        }
    }

    private fun showDialogFragment() {
        queriedForTextView.visibility = View.GONE
        val searchDialogFragment = SearchDialogFragment() as DialogFragment
        val ft = fragmentManager
        searchDialogFragment.show(ft, "dialog")
    }

    override fun someEvent(usersQuery: String) {
        if (internetIsActive()) {
            fetchDataFromGuardianUsingRetrofit(usersQuery)
        } else {
            emptyView.text = getString(R.string.internet_not_connected)
        }
    }

    private fun internetIsActive(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    private fun fetchDataFromGuardianUsingRetrofit(usersQuery: String) {
        emptyView.text = getString(R.string.loading_news)
        progressBar.visibility = View.VISIBLE
        val apiInterface = APIClient.client.create(APIInterface::class.java)
        /**
        GET List of news
         **/
        val call = apiInterface.getNewsList(usersQuery)
        call.enqueue(object : Callback<GsonNewsResponse> {
            override fun onResponse(call: Call<GsonNewsResponse>, response: Response<GsonNewsResponse>?) {
                val resource = response?.body()
                this@MainActivity.runOnUiThread {
                    progressBar.visibility = View.GONE
                    setupRecyclerView(applicationContext, resource?.response?.newsItem!!, usersQuery)

                }
            }

            override fun onFailure(call: Call<GsonNewsResponse>, t: Throwable?) {
                emptyView.text = getString(R.string.failed_server_response, t?.message)
            }
        })
    }

    fun setupRecyclerView(context: Context, listOfNews: ArrayList<News>, usersQuery: String) {
        if (listOfNews.isEmpty() || listOfNews.size == 0) {
            emptyView.text = getString(R.string.empty_response_received)
        } else {
            emptyView.visibility = View.GONE
            queriedForTextView.visibility = View.VISIBLE
            queriedForTextView.text = getString(R.string.search_queried_for, usersQuery)
            recyclerView.visibility = View.VISIBLE
            runOnUiThread(Runnable() {
                run() {
                    recyclerView.adapter = RecyclerViewAdapter(listOfNews, context)
                }
            })

        }
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
                launchIntent(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun launchIntent(context: Context) {
        //start new intent on fav click
        val favIntent = Intent(context, FavoriteActivity::class.java)
        context.startActivity(favIntent)
    }
}
