package com.example.android.newswithkotlin

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor



/**
 * Created by Greta Grigutė on 2018-08-14.
 */
internal object ApiClient {
    val client: Retrofit
        get() {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    val retrofit = Retrofit.Builder()
            .baseUrl("http://content.guardianapis.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

 //   val apiInterface = retrofit.create(ApiInterface::class.java)

            return retrofit
        }

}