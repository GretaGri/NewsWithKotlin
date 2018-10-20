package com.example.android.newswithkotlin

import android.app.AlarmManager
import android.app.DialogFragment
import android.app.PendingIntent
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.android.newswithkotlin.database.GsonNewsResponse
import com.example.android.newswithkotlin.database.News
import com.example.android.newswithkotlin.widget.FetchWidgetDataFromBackgroundService
import kotlinx.android.synthetic.main.main_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(),
        SearchDialogFragment.UserQueryListener,
        SharedPreferences.OnSharedPreferenceChangeListener {


    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key!!.contains(getString(R.string.pref_checkbox_key))) {
            handleNotificationAndBackgroundNewsFetching(sharedPreferences)
        }
    }

    private fun handleNotificationAndBackgroundNewsFetching(sharedPreferences: SharedPreferences?) {
        val checkBoxStatus = sharedPreferences?.getBoolean(getString(R.string.pref_checkbox_key),
                resources.getBoolean(R.bool.pref_show_checkbox_default))
        Log.v("my_tag", "checkbox checked is: " + checkBoxStatus)
        if (checkBoxStatus!!) {
            startBackgroundServiceToFetchNewsAndShowNotification()
        } else {
            stopBackgroundServiceToFetchNewsAndShowNotification()
        }

    }


    private fun stopBackgroundServiceToFetchNewsAndShowNotification() {
        manager?.cancel(alarmApiCallPendingIntent)
    }

    private fun startBackgroundServiceToFetchNewsAndShowNotification() {
        manager?.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 1000 * 60 * 60 * 4, alarmApiCallPendingIntent)
    }

    //un-register the sharedPreferenceListener
    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this)
    }

    // declare recyclerView instance with lateinit so that it can be handled without any NPE
    lateinit var recyclerView: RecyclerView
    lateinit var emptyView: TextView
    lateinit var queriedForTextView: TextView
    lateinit var progressBar: ProgressBar

    var newsFromApi = ArrayList<News>()
    var newsFromDatabase = ArrayList<News>()

    //handle background news fetching
    var alarmApiCallPendingIntent: PendingIntent? = null
    var manager: AlarmManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        setSupportActionBar(toolbar)

        //initialize views
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MainRecyclerViewAdapter(newsFromApi, this, newsFromDatabase)

        emptyView = findViewById(R.id.empty_view)
        queriedForTextView = findViewById(R.id.queried_for_text_view)
        progressBar = findViewById(R.id.progress_bar)

        //if there is no news data from earlier time, show the dialog and ask for users input
        if (savedInstanceState == null) {
            showDialogFragment()
        } else {
            val newsList = intent.getParcelableArrayListExtra<News>("newsList")
            Log.d("my_tag", "extra size is: " + newsList.size)
        }


        fab.setOnClickListener {
            showDialogFragment()
        }
        setupViewModel()
        //handle background news fetch mechanism
        val alarmApiCallIntent = Intent(this, AlarmApiCallReceiver::class.java)
        alarmApiCallPendingIntent = PendingIntent.getBroadcast(this, 0, alarmApiCallIntent, 0)
        manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        setupSharedPreferences()
    }

    private fun setupSharedPreferences() {
        // Get all of the values from shared preferences to set it up
        // Get a reference to the default shared preferences from the PreferenceManager class
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        //register the sharedPreferenceListener
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        handleNotificationAndBackgroundNewsFetching(sharedPreferences)

    }

    private fun showDialogFragment() {
        queriedForTextView.visibility = View.GONE
        val searchDialogFragment = SearchDialogFragment() as DialogFragment
        val ft = fragmentManager
        searchDialogFragment.show(ft, "dialog")
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
                    newsFromApi = resource?.response?.newsItem!!
                    setupRecyclerView(applicationContext, resource.response.newsItem, usersQuery)

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
            runOnUiThread {
                run() {
                    recyclerView.adapter = MainRecyclerViewAdapter(listOfNews, context, newsFromDatabase)
                }
            }

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
            R.id.action_favorite -> {
                launchFavoriteIntent(this)
                true
            }
            R.id.action_settings -> {
                val settingsActivity = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(settingsActivity);
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun launchFavoriteIntent(context: Context) {
        //start new intent on fav click
        val favIntent = Intent(context, FavoriteActivity::class.java)
        context.startActivity(favIntent)
    }

    private fun setupViewModel() {
        val viewModel = ViewModelProviders.of(this).get(AllNewsViewModel::class.java)
        viewModel.newses.observe(this, object : Observer<List<News>?> {
            override fun onChanged(t: List<News>?) {
                val arrayListOfNewFromListOfNews = ArrayList<News>()
                for (news in t!!) {
                    arrayListOfNewFromListOfNews.add(news)
                }
                newsFromDatabase = arrayListOfNewFromListOfNews
                recyclerView.adapter = MainRecyclerViewAdapter(newsFromApi, this@MainActivity, newsFromDatabase)

                //start the service to fetch widget data
                fetchWidgetDataInBackgroundService(this@MainActivity)
            }
        })
    }

    private fun fetchWidgetDataInBackgroundService(context: Context) {
        context.startService(Intent(context, FetchWidgetDataFromBackgroundService::class.java))
    }

    override fun userAddedSearchParameter(usersQuery: String) {
        if (internetIsActive()) {
            fetchDataFromGuardianUsingRetrofit(usersQuery)
        } else {
            emptyView.text = getString(R.string.internet_not_connected)
        }
    }
}
