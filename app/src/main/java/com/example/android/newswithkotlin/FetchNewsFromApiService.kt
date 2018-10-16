package com.example.android.newswithkotlin

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.support.annotation.Nullable
import android.util.Log
import com.example.android.newswithkotlin.database.GsonNewsResponse
import com.example.android.newswithkotlin.database.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FetchNewsFromApiService() : Service() {

    companion object {
        val TAG: String = FetchNewsFromApiService::class.java.simpleName
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.d("my_tag", "FetchNewsFromApiService onStartCommand called")
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
        val call = apiInterface.getNewsList("Udupi")
        call.enqueue(object : Callback<GsonNewsResponse> {
            override fun onResponse(call: Call<GsonNewsResponse>, response: Response<GsonNewsResponse>?) {
                val resource = response?.body()
                val newsFromApi = resource?.response?.newsItem!!

                val getNewsListObject = object : GetNewsList {
                    override fun onNewsFetched(newsList: ArrayList<News>) {}
                }
                Log.d("my_tag", "fetched news size is: " + newsFromApi.size)
                getNewsListObject.onNewsFetched(newsFromApi)
            }

            override fun onFailure(call: Call<GsonNewsResponse>, t: Throwable?) {
                Log.d(TAG, getString(R.string.failed_server_response, t?.message))
            }
        })
    }

    interface GetNewsList {
        fun onNewsFetched(newsList: ArrayList<News>)
    }
}
