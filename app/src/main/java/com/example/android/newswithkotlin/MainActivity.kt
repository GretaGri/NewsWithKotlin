package com.example.android.newswithkotlin


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MainActivity : AppCompatActivity() {

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
        fetchDataFromGuardianUsingRetrofit()
    }

    private fun fetchDataFromGuardianUsingRetrofit() {
        // TODO("not implemented") //Make the api call here and also setup the recyclerView after parsing data using Gson
        val apiInterface = ApiClient.client.create<ApiInterface>(ApiInterface::class.java)
        //how to call interface
        val call: Call<NewsResponse> = apiInterface.searchArticle("sport")
        Log.d("my_log", "call is: " + call.toString())
                call.enqueue(object : Callback<NewsResponse> {
                    override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {

                        Log.d("TAG", response.code().toString() + "")


                        val resource = response.body()
                        val newsArray = resource!!.response.newsItem


                        Log.d("TAG", "Array list is: " + newsArray.toString())

                    }

                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        call.cancel()
                    }
                })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.ml.
        return when (item.itemId) {
            R.id.action_settings ->  {
                Toast.makeText(this, "Sorry, this feature is not available",
                        Toast.LENGTH_SHORT).show();
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
