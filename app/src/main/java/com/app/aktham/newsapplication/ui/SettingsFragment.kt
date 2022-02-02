package com.app.aktham.newsapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.adapters.SettingsAdapter
import com.app.aktham.newsapplication.databinding.FragmentSettingsBinding
import com.app.aktham.newsapplication.models.SettingsModel
import com.app.aktham.newsapplication.ui.viewModels.NewsViewModel
import com.app.aktham.newsapplication.utils.Constants
import com.app.aktham.newsapplication.utils.Constants.SETTING_CONTENT_LANG_TAG
import com.app.aktham.newsapplication.utils.Constants.SETTING_STYLE_TAG
import com.app.aktham.newsapplication.utils.MyListsListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SettingsFragment : Fragment(R.layout.fragment_settings),
    MyListsListener<SettingsModel> {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel: NewsViewModel by activityViewModels()
    private lateinit var settingListAdapter: SettingsAdapter
    private lateinit var settingListItems: List<SettingsModel>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)
    }

    override fun onStart() {
        super.onStart()
        initDataSettingList()
        setUpSettingRecyclerList()
    }

    private fun setUpSettingRecyclerList() {
        // init setting list adapter
        settingListAdapter = SettingsAdapter(this)
        binding.settingsRecyclerList.setHasFixedSize(true)
        binding.settingsRecyclerList.layoutManager = LinearLayoutManager(requireContext())

        // set data to adapter
        settingListAdapter.submitList(settingListItems)
        // set adapter to settings recyclerList
        binding.settingsRecyclerList.adapter = settingListAdapter
    }

    private fun initDataSettingList() {
        val selectedStyle = when (newsViewModel.loadAppStyle()) {
            AppCompatDelegate.MODE_NIGHT_YES -> getString(R.string.dark)
            AppCompatDelegate.MODE_NIGHT_NO -> getString(R.string.light)
            else -> getString(R.string.style_follow_system)
        }

        val selectedLanguage = when (newsViewModel.loadContentLanguageState()) {
            Constants.AR_LANGUAGE -> getString(R.string.lang_ar)
            else -> getString(R.string.lang_en)
        }

        // Set Up Settings List
        settingListItems = listOf(
            SettingsModel(
                title = getString(R.string.settings_title_content_language),
                subTitle = selectedLanguage,
                icon = R.drawable.ic_baseline_language,
                tag = SETTING_CONTENT_LANG_TAG
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


    }

    // On Setting Item List Click
    override fun onItemClick(position: Int, newItem: SettingsModel) {
        when (newItem.tag) {
            SETTING_CONTENT_LANG_TAG -> {
                showSelectLanguageDialog()
            }

            SETTING_STYLE_TAG -> {
                showAppStyleDialog()
            }
        }
    }

    // News Content Language Dialog
    private fun showSelectLanguageDialog() {
        // get language list from string resources file
        val langList = resources.getStringArray(R.array.NewsContentLanguage)
        // get selected language
        var checkedItem =
            if (newsViewModel.loadContentLanguageState() == Constants.EN_LANGUAGE) 1 else 0
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.content_lang_dialog_title))
            // save change button
            .setPositiveButton(getString(R.string.save_lang_dialog_but)) { dialog, _ ->
                // save new selected language
                newsViewModel.saveContentLanguageState(
                    when(checkedItem) {
                        1 -> Constants.EN_LANGUAGE
                        else -> Constants.AR_LANGUAGE
                    }
                )
                // notify setting list to update list subtitle
                initDataSettingList()
                settingListAdapter.submitList(settingListItems)

                // close dialog
                dialog.dismiss()
            }
            // cancel dialog button
            .setNegativeButton(getString(R.string.cancel_lang_dialog_but)) { dialog, _ ->
                // close dialog without save
                dialog.dismiss()
            }
            // items dialog on select
            .setSingleChoiceItems(langList, checkedItem) { _, which ->
                checkedItem = which
            }
            // Shoe Dialog
            .show()
    }

    // Application style Dialog
    private fun showAppStyleDialog() {
        // get styles list from string resources file
        val stylesList = resources.getStringArray(R.array.AppStyles)
        // get selected style
        var checkedStyle = when (newsViewModel.loadAppStyle()) {
            AppCompatDelegate.MODE_NIGHT_YES -> 1
            AppCompatDelegate.MODE_NIGHT_NO -> 0
            else -> 2
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.select_your_application_style))
            // save change button
            .setPositiveButton(getString(R.string.select_style_dialog_button)) { dialog, _ ->
                // save new selected style
                newsViewModel.changeAppStyle(
                    when(checkedStyle) {
                        0 -> AppCompatDelegate.MODE_NIGHT_NO
                        1 -> AppCompatDelegate.MODE_NIGHT_YES
                        else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    }
                )
                // notify setting list to update list subtitle
                initDataSettingList()
                settingListAdapter.submitList(settingListItems)

                // close dialog
                dialog.dismiss()
            }
            // cancel dialog button
            .setNegativeButton(getString(R.string.cancel_style_dialog_but)) { dialog, _ ->
                // close dialog without save
                dialog.dismiss()
            }
            // items dialog on select
            .setSingleChoiceItems(stylesList, checkedStyle) { _, which ->
                checkedStyle = which
            }
            // Shoe Dialog
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}