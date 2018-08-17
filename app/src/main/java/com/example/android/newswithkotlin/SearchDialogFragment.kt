package com.example.android.newswithkotlin

import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class SearchDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.fragment_search
                , container, false)

        val searchView: SearchView = view.findViewById(R.id.search_view)
        searchView.setQueryHint(getString(R.string.search_hint))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                //Log.e("onQueryTextChange", "called");
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                Log.v("my_tag", "text received is: " + query)
                return false
            }

        })
        return view
    }
}