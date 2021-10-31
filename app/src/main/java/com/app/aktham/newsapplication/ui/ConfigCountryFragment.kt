package com.app.aktham.newsapplication.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.adapters.CountryListAdapter
import com.app.aktham.newsapplication.databinding.FragmentConfigCountryBinding
import com.app.aktham.newsapplication.models.CountryListModel
import com.app.aktham.newsapplication.ui.viewModels.ConfigViewModel
import java.util.*

class ConfigCountryFragment : Fragment(R.layout.fragment_config_country),
    CountryListAdapter.CountryListAction {

    private var _binding: FragmentConfigCountryBinding? = null
    private val binding get() = _binding!!

    private val configViewModel: ConfigViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentConfigCountryBinding.bind(view)

        // setup country list
        initCountryList()

        // back button onClick
        binding.configBackBut.setOnClickListener {
            findNavController().popBackStack()
        }

        // finish button onClick
        binding.configFinishBut.setOnClickListener {
            // Save Configuration
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }
    }

    // Show Finish Button With animation
    private fun showFinishButton() {
        if (configViewModel.loadCountry().isNotBlank()) {
            binding.configFinishBut.animate().apply {
                translationY(0f)
                duration = 500
            }
        }
    }

    // Setup Countries RecyclerList
    private fun initCountryList() {
        val adapter = CountryListAdapter(listListener = this, listData = getCountries())
        binding.countryRecyclerView.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.countryRecyclerView.setHasFixedSize(true)
        binding.countryRecyclerView.adapter = adapter
    }

    // OnList Item Click Listener
    override fun onItemClick(position: Int, itemData: CountryListModel) {
        configViewModel.saveCountry(itemData.countryName)
        showFinishButton()
    }

    // Get Countries List
    private fun getCountries(): List<CountryListModel> {
        val local = Locale.getAvailableLocales()
        val countriesList = mutableListOf<CountryListModel>()
        local.forEach {
            if (it.country.isNotBlank() && it.displayCountry.isNotBlank()) {
                countriesList.add(
                    CountryListModel(
                        countryName = it.displayCountry,
                        countryCode = it.country ?: ""
                    )
                )
            }
        }

        countriesList.sortBy { it.countryName }
        return countriesList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}