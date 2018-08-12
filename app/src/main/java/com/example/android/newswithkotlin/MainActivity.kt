package com.example.android.newswithkotlin


import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import okhttp3.OkHttpClient
import android.os.AsyncTask.execute
import okhttp3.Request


class MainActivity : AppCompatActivity() {

    //recyclerView
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

        //Add a recyclerView with dummy data
        //setupRecyclerView()
        recyclerView = findViewById(R.id.recycler_view)
        //create asyncTask
        GetNewsTask(this, this.recyclerView, this.no_news_found_text_view).execute()
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
            R.id.action_settings ->  {
                Toast.makeText(this, "Sorry, this feature is not available",
                        Toast.LENGTH_SHORT).show();
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    class GetNewsTask(context: Context, recyclerView: RecyclerView, textView: TextView) : AsyncTask<Unit, Unit, ArrayList<NewsContent>?>() {

        val noNewsTextView: TextView? = textView
        val context:Context = context
        val recyclerView:RecyclerView = recyclerView
        //To use OkHttp also do not forget to add dependency and internet permission:)
        var client = OkHttpClient()

        override fun doInBackground(vararg params: Unit?): ArrayList<NewsContent>? {
            val stringUrl = URL("http://content.guardianapis.com/search?q=sport&order-by=newest&api-key=0a397f99-4b95-416f-9c51-34c711f0069a&show-tags=contributor")

            val builder = Request.Builder()
            builder.url(stringUrl)
            val request = builder.build()

            try {
                val response = client.newCall(request).execute()
                val jsonUtils = JsonUtils()
                var news: ArrayList<NewsContent>? = null
                if (response!= null){news = jsonUtils.extractFeatureFromJson(response.body()!!.string())}
                return news
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null

           // val httpClient = url.openConnection() as HttpURLConnection
           // if (httpClient.responseCode == HttpURLConnection.HTTP_OK) {
             //   try {
              //      val stream = BufferedInputStream(httpClient.inputStream)
                //    val data: String = readStream(inputStream = stream)
                    //Log.v("my_tag", "data received is: " + data)

               //     val jsonUtils = JsonUtils()
              //      var news = jsonUtils.extractFeatureFromJson(data)

                //    return news
            //    } catch (e: Exception) {
             //       e.printStackTrace()
            //    } finally {
             //       httpClient.disconnect()
              //  }
          //  } else {
           //     println("ERROR ${httpClient.responseCode}")
         //   }
         //   return null
        }

        fun readStream(inputStream: BufferedInputStream): String {
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            bufferedReader.forEachLine { stringBuilder.append(it) }
            return stringBuilder.toString()
        }

        override fun onPostExecute(result: ArrayList<NewsContent>?) {
            super.onPostExecute(result)
            if(result!=null) setupRecyclerView(context, result)
            //if(result!=null) noNewsTextView?.text = result[0].title + result[0].author + result[0].webUrl

            /**
             * ... Work with the data:
             * https://engineering.kitchenstories.io/data-classes-and-parsing-json-a-story-about-converting-models-to-kotlin-caf8a599df9e
             * https://github.com/square/moshi - a modern JSON library for Android and Java.
             * It makes it easy to parse JSON into Java objects (example in article above).
             * https://github.com/cbeust/klaxon -  library to parse json in Kotlin
             */

        }

        fun setupRecyclerView(context: Context, myList: ArrayList<NewsContent>) {
            //val myList: ArrayList<String> = ArrayList()
            // for (i in 1..10)
            //     myList.add("news $i")
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = DummyListAdapter(myList, context)
        }
    }

}
