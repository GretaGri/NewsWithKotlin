package com.example.android.newswithkotlin

import android.app.DialogFragment
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SearchDialogActivity : AppCompatActivity(), SearchDialogFragment.userQueryListener {

    override fun someEvent(usersQuery: String) {
        val deliveringResultIntent = Intent()
        deliveringResultIntent.putExtra("usersQuery", usersQuery)
        setResult(5, deliveringResultIntent)
        //Log.v("my_taggggg", "stringgggggggg is: "+usersQuery)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_search)
        startSearchFragment()
    }

    private fun startSearchFragment() {
        val searchDialogFragment = SearchDialogFragment() as DialogFragment
        val ft = fragmentManager
        searchDialogFragment.show(ft, "dialog")
    }
}