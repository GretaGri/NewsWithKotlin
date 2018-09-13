package com.example.android.newswithkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient


class NewsDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)
        val myWebView: WebView = findViewById(R.id.web_view)
        myWebView.webViewClient = WebViewClient()
        val url: String = intent.getStringExtra("newsUrl")
        val title: String = intent.getStringExtra("newsTitle")
        setTitle(title)
        myWebView.loadUrl(url)
    }
}