package com.example.android.newswithkotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmApiCallReceiver : BroadcastReceiver() {

    companion object {
        val TAG: String = AlarmApiCallReceiver::class.java.simpleName
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("my_tag", "AlarmApiCallReceiver onReceive called")
        fetchDatInBackgroundService(context)
    }

    private fun fetchDatInBackgroundService(context: Context) {
        context.startService(Intent(context, FetchNewsFromApiService::class.java))
    }
}