package com.app.aktham.newsapplication.utils

object Constants {

    // Constants Keys From SharedPreference
    const val SHARED_PREFERENCE_FILE_NAME = "newsAppStateFile"
    const val PREFERENCE_LANG_KEY = "languageKey"
    const val PREFERENCE_COUNTRY_KEY = "langCountry"
    const val PREFERENCE_STYLE_KEY = "applicationStyle"
    // First Start App use to show configuration activity for first time only
    const val PREFERENCE_FIRST_START_KET = "configurationActivity"

    // Api Base URL
    const val BASE_URL = "https://newsapi.org/"
    // Api Key
    // TODO: 11/20/2021 Add Your Api Key
    const val API_KEY = "7b30511ca5044213b235c243209e2b6f" // Api Key from https://newsapi.org/

    const val EN_LANGUAGE = "en"
    const val AR_LANGUAGE = "ar"

    const val PAGE_SIZE_MAX = 50

    // News Category's available in News api
    val NewsCategories = listOf(
        "general",
        "business",
        "entertainment",
        "health",
        "science",
        "sports",
        "technology"
    )

    // Settings List Items Tags for onClick item functionality
    const val SETTING_CONTENT_LANG_TAG = "settingsLang"
    const val SETTING_COUNTRY_TAG = "country"
    const val SETTING_STYLE_TAG = "appStyle"

    const val DB_NAME = "NewsDataBase"

}
