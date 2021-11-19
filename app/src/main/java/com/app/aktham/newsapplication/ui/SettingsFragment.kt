package com.app.aktham.newsapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.adapters.SettingsAdapter
import com.app.aktham.newsapplication.databinding.FragmentConfigLanguageBinding
import com.app.aktham.newsapplication.databinding.FragmentSettingsBinding
import com.app.aktham.newsapplication.models.SettingsModel
import com.app.aktham.newsapplication.ui.viewModels.NewsViewModel
import com.app.aktham.newsapplication.utils.Constants
import com.app.aktham.newsapplication.utils.Constants.SETTING_CONTENT_LANG_TAG
import com.app.aktham.newsapplication.utils.Constants.SETTING_COUNTRY_TAG
import com.app.aktham.newsapplication.utils.Constants.SETTING_STYLE_TAG
import com.google.android.material.bottomsheet.BottomSheetDialog

class SettingsFragment : Fragment(R.layout.fragment_settings),
    SettingsAdapter.SettingsListListener {

    private var _binding :FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel :NewsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)
    }

    override fun onStart() {
        super.onStart()
        val selectedStyle = when(newsViewModel.loadAppStyle()){
            AppCompatDelegate.MODE_NIGHT_YES -> getString(R.string.dark)
            AppCompatDelegate.MODE_NIGHT_NO -> getString(R.string.light)
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
                AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY,
                AppCompatDelegate.MODE_NIGHT_UNSPECIFIED -> getString(R.string.style_follow_system)
            else -> ""
        }

        val selectedLanguage = when(newsViewModel.loadContentLanguageState()){
            Constants.EN_LANGUAGE -> getString(R.string.lang_en)
            Constants.AR_LANGUAGE -> getString(R.string.lang_ar)
            else -> ""
        }

        // Set Up Settings List
        val settingListItems = listOf(
            SettingsModel(
                title = getString(R.string.settings_title_content_language),
                subTitle = selectedLanguage,
                icon = R.drawable.ic_baseline_language,
                tag = SETTING_CONTENT_LANG_TAG
            ),
            SettingsModel(
                title = getString(R.string.settings_title_country),
                subTitle = newsViewModel.loadCountrySelected(),
                icon = R.drawable.ic_baseline_outlined_flag,
                tag = SETTING_COUNTRY_TAG
            ),
            SettingsModel(
                title = getString(R.string.settings_title_app_style),
                subTitle = selectedStyle,
                icon = R.drawable.ic_baseline_style,
                tag = SETTING_STYLE_TAG
            ),
            SettingsModel(
                title = getString(R.string.settings_title_ratting_app),
                icon = R.drawable.ic_baseline_star_rate,
                tag = ""
            ),
            SettingsModel(
                title = getString(R.string.settings_title_more_apps),
                icon = R.drawable.ic_baseline_menu,
                tag = ""
            ),
            SettingsModel(
                title = getString(R.string.settings_title_contact_us),
                icon = R.drawable.ic_baseline_contact_mail,
                tag = ""
            ),
            SettingsModel(
                title = getString(R.string.settings_title_version),
                subTitle = "1.0",
                icon = R.drawable.ic_baseline_verified,
                tag = ""
            )
        )

        val settingsAdapter = SettingsAdapter(settingListItems, this)
        binding.settingsRecyclerList.setHasFixedSize(true)
        binding.settingsRecyclerList.layoutManager = LinearLayoutManager(requireContext())
        binding.settingsRecyclerList.adapter = settingsAdapter
    }


    // On Setting Item List Click
    override fun onClick(position: Int, itemData: SettingsModel) {
        when(itemData.tag) {
            SETTING_CONTENT_LANG_TAG -> {
                // TODO: 11/20/2021
            }

            SETTING_COUNTRY_TAG -> {
                // TODO: 11/20/2021
            }

            SETTING_STYLE_TAG -> {
                // TODO: 11/20/2021
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}