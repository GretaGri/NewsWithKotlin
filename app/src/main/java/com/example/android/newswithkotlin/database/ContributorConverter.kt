package com.example.android.newswithkotlin.database

import android.arch.persistence.room.TypeConverter

class ContributorConverter {

    @TypeConverter
    fun toContributorContent(contributorArrayList: ArrayList<ContributorContent>): ContributorContent? {
        return if (contributorArrayList.isEmpty()) null else createContributorContentObject(contributorArrayList)
    }

    private fun createContributorContentObject(contributorArrayList: ArrayList<ContributorContent>): ContributorContent {
        var webTitle: String = ""
        var webUrl: String = ""
        for ((index, contributor) in contributorArrayList.withIndex()) {
            webTitle += contributor.title
            webUrl += contributor.apiUrl
            if (contributorArrayList.size > 0 && index != (contributorArrayList.size) - 1) {
                webTitle += ", "
                webUrl += ", "
            }
        }
        return ContributorContent(webTitle, webUrl)
    }

    @TypeConverter
    fun toContributorArrayList(contributorContent: ContributorContent): ArrayList<ContributorContent>? {
        var arrayList = arrayListOf<ContributorContent>(contributorContent)
        return arrayList
    }
}