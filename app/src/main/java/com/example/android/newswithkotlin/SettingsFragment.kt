package com.example.android.newswithkotlin

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat


class SettingsFragment :
        PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_settings)
    }
}