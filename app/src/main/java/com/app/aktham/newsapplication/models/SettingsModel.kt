package com.app.aktham.newsapplication.models

import androidx.annotation.DrawableRes

data class SettingsModel(
    val title: String,
    val subTitle: String = "",
    val tag: String,
    @DrawableRes val icon: Int
)