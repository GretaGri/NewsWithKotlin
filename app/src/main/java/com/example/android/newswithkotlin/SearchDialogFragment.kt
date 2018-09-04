package com.example.android.newswithkotlin

import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class SearchDialogFragment : DialogFragment() {

    //callback code referenced from: https://stackoverflow.com/a/14440095/5770629
    //create callback interface
    interface userQueryListener {
        fun someEvent(query: String)
    }

    var queryListener: userQueryListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_search
                , container, false)


        //initialize the callback interface
        queryListener = activity as userQueryListener


        val searchView: SearchView = view.findViewById(R.id.search_view)
        searchView.setQueryHint(getString(R.string.search_hint))
        /*
        expand searchView as soon as the it's instantiated>
        Source: @Link: https://stackoverflow.com/a/11710098
        */
        searchView.setIconifiedByDefault(false)
        //set textListener to serachView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                //callback the interface with the users query so that action can be performed on that
                queryListener!!.someEvent(query);

                //when users query has been received, dismiss the dialog fragment and do the further operation
                //dismiss dialog fragment
                dismiss()
                return true
            }

        })
        return view
    }
}