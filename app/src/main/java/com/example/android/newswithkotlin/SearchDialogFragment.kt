package com.example.android.newswithkotlin

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView


class SearchDialogFragment : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.fragment_search
                , container, false)

        val searchView: SearchView = view.findViewById(R.id.search_view)
        searchView.setQueryHint("Search View Hint")

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                //Log.e("onQueryTextChange", "called");
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {


                // Do your task here

                return false
            }

        })



        return view
    }
}