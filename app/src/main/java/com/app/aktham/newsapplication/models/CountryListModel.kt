package com.app.aktham.newsapplication.models

data class CountryListModel(
    val countryName: String,
    val countryCode: String,
    var isSelected: Boolean = false
)
