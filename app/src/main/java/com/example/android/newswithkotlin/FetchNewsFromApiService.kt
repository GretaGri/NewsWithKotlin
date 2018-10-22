package com.example.android.newswithkotlin

import android.annotation.TargetApi
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.annotation.Nullable
import android.util.Log
import com.example.android.newswithkotlin.database.GsonNewsResponse
import com.example.android.newswithkotlin.database.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class FetchNewsFromApiService() : Service() {

    companion object {
        val TAG: String = FetchNewsFromApiService::class.java.simpleName
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.d(TAG, "FetchNewsFromApiService onStartCommand called")
        fetchDataFromGuardianUsingRetrofit()
        return START_STICKY
    }


    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
    }

    private fun fetchDataFromGuardianUsingRetrofit() {
        val apiInterface = APIClient.client.create(APIInterface::class.java)
        /**
        GET List of news
         **/
        val call = apiInterface.getNewsList("")
        call.enqueue(object : Callback<GsonNewsResponse> {
            override fun onResponse(call: Call<GsonNewsResponse>, response: Response<GsonNewsResponse>?) {
                val resource = response?.body()
                val newsFromApi = resource?.response?.newsItem!!
                showNotification(newsFromApi)
            }

            override fun onFailure(call: Call<GsonNewsResponse>, t: Throwable?) {
                Log.d(TAG, getString(R.string.failed_server_response, t?.message))
            }
        })

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun showNotification(newsList: ArrayList<News>) {
        val notificationHelper = NotificationHelper(this)
        val index = Random().nextInt(newsList.size - 1)
        val randomNews = newsList.get(index)

        val notificationIntent = Intent(this, MainActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelableArrayList("newsList", newsList)
        notificationIntent.putExtra("newsList", bundle)
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val intent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val nb = notificationHelper.getNotification("Guardian Top News", randomNews.title, intent)
        notificationHelper.notify(101, nb.build())
    }
}
