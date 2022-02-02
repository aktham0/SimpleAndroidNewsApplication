package com.app.aktham.newsapplication.ui.viewModels

import androidx.lifecycle.ViewModel
import com.app.aktham.newsapplication.repositories.ConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val configRepository: ConfigRepository
) : ViewModel() {

    // Save And Load SharedPreferences Configuration
    fun saveContentLanguageState(langState: String) = configRepository.saveContentLanguageState(langState)
    fun loadContentLanguageState() = configRepository.loadContentLanguageState()
    fun saveCountry(country: String) = configRepository.saveCountry(country)
    fun loadCountry() = configRepository.loadCountry()

    fun loadStyleState() = configRepository.loadAppStyle()
    fun saveStyleState(style: Int) = configRepository.saveAppStyle(style)
    fun changAppStyle() = configRepository.changeAppStyle(loadStyleState())

}