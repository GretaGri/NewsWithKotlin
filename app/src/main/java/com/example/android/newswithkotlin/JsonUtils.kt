package com.example.android.newswithkotlin

import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Greta Grigutė on 2018-08-09.
 */
class JsonUtils {
    private lateinit var webTitle: String
    private lateinit var newsWebUrl: String
    private lateinit var authorTitle: String

    /**
     * Create a private constructor because no one should ever create a [JsonUtils] object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private fun JsonUtils() {}

    /**
     * Return a list of [News] objects that has been built up from
     * parsing a JSON response.
     */
   fun extractFeatureFromJson(jsonResponse: String?): ArrayList<News> {

        // Create an empty ArrayList that we can start adding news to
        val news = ArrayList<News>()

        /*
        Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        is formatted, a JSONException exception object will be thrown.
        Catch the exception so the app doesn't crash, and handle exception.
        */
        try {
            // TODO: Parse the response given by the jsonResponse string

            val news_json_response = JSONObject(jsonResponse)
            if (news_json_response.has("response")) {
                val response = news_json_response.getJSONObject("response")
                if (response.has("results")) {
                    val resultsArray = response.getJSONArray("results")


                    for (i in 0 until resultsArray.length()) {
                        var webTitle: String = "No title"
                        var newsWebUrl: String = "No name"
                        var authorTitle: String = "No author"

                        val resultDetails = resultsArray.getJSONObject(i)
                        if (resultDetails.has("webTitle")) {
                            webTitle = resultDetails.getString("webTitle")
                        }
                        if (resultDetails.has("webUrl")) {
                            newsWebUrl = resultDetails.getString("webUrl")
                        }
                        if (resultDetails.has("tags")) {
                            val tagsArray = resultDetails.getJSONArray("tags")
                            if (tagsArray.length() > 0) {
                                val tagsDetails = tagsArray.getJSONObject(0)
                                if (tagsDetails.has("webTitle")) {
                                    authorTitle = tagsDetails.getString("webTitle")
                                }
                            }
                        }
                        news.add(News(webTitle, authorTitle, newsWebUrl))
                    }
                }
            }
        } catch (e: JSONException) {
            //handle exception
        }

        // Return the list of news
        return news
    }
}

