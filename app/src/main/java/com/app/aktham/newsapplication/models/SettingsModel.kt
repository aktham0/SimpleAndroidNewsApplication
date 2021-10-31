package com.app.aktham.newsapplication.models

data class SettingsModel(
    val title :String,
    var subTitle :String = "",
    var icon :Int,
    val tag :String = ""
)
