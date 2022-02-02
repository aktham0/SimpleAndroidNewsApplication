package com.app.aktham.newsapplication.repositories

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.app.aktham.newsapplication.utils.Constants
import javax.inject.Inject

class ConfigRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun saveContentLanguageState(langState: String) {
        sharedPreferences.edit {
            putString(Constants.PREFERENCE_LANG_KEY, langState)
        }
    }

    fun loadContentLanguageState() =
        sharedPreferences.getString(
            Constants.PREFERENCE_LANG_KEY,
            Constants.EN_LANGUAGE
        ) ?: Constants.EN_LANGUAGE

    fun saveCountry(country: String) {
        sharedPreferences.edit {
            putString(Constants.PREFERENCE_COUNTRY_KEY, country)
        }
    }

    fun loadCountry() = sharedPreferences.getString(
        Constants.PREFERENCE_COUNTRY_KEY,
        ""
    ) ?: ""

    fun saveAppStyle(style: Int) {
        sharedPreferences.edit {
            putInt(Constants.PREFERENCE_STYLE_KEY, style)
        }
    }

    fun loadAppStyle() =
        sharedPreferences.getInt(Constants.PREFERENCE_STYLE_KEY, -5)

    fun changeAppStyle(style: Int) {
        val defaultStyle = AppCompatDelegate.getDefaultNightMode()
        if (defaultStyle != style) {
            AppCompatDelegate.setDefaultNightMode(style)
        }
        // save style state in sharedPreferences
        saveAppStyle(style)
    }

}