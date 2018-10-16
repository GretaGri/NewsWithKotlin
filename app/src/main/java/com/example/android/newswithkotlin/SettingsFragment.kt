package com.example.android.newswithkotlin

import android.os.Bundle
import android.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.widget.Toast


class SettingsFragment :
        PreferenceFragmentCompat(),
        Preference.OnPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle, rootKey: String) {
        addPreferencesFromResource(R.xml.pref_settings)
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
        val error = Toast.makeText(getContext(), "Please select a number between 0.1 and 10", Toast.LENGTH_SHORT)

        val editTextPrefKey = getString(R.string.pref_editTextPref_key)
        if (preference.key == editTextPrefKey) {
            var stringInputValue = (newValue as String).trim { it <= ' ' }
            if (stringInputValue == "") stringInputValue = "1"
            try {
                val inputFloatValue = java.lang.Float.parseFloat(stringInputValue)
                if (inputFloatValue > 10 || inputFloatValue <= 0) {
                    error.show()
                    return false
                }
            } catch (nfe: NumberFormatException) {
                error.show()
                return false
            }

        }
        return true
    }
}