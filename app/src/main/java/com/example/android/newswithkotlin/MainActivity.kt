package com.example.android.newswithkotlin


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

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
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val myList: ArrayList<String> = ArrayList()
        for (i in 1..10)
            myList.add("news $i")
        recyclerView = findViewById(R.id.recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DummyListAdapter(myList, this)


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
}
