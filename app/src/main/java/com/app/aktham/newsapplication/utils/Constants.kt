package com.app.aktham.newsapplication.utils

object Constants {
    const val SHARED_PREFERENCE_FILE_NAME = "newsAppStateFile"
    const val PREFERENCE_LANG_KEY = "languageKey"
    const val PREFERENCE_COUNTRY_KEY = "langCountry"
    const val PREFERENCE_STYLE_KEY = "applicationStyle"

    const val BASE_URL = "https://newsapi.org/"
    const val API_KEY = "7b30511ca5044213b235c243209e2b6f"

    const val EN_LANGUAGE = "en"
    const val AR_LANGUAGE = "ar"

    const val PAGE_SIZE_MAX = 100

    val NewsCategories = listOf(
        "general",
        "business",
        "entertainment",
        "health",
        "science",
        "sports",
        "technology",
        "crypto"
    )

    // Settings List Items Tags
    const val SETTING_CONTENT_LANG_TAG = "settingsLang"
    const val SETTING_COUNTRY_TAG = "country"
    const val SETTING_STYLE_TAG = "appStyle"
}