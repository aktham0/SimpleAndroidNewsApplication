package com.app.aktham.newsapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.databinding.FragmentConfigLanguageBinding
import com.app.aktham.newsapplication.ui.viewModels.ConfigViewModel
import com.app.aktham.newsapplication.utils.Constants

private const val CARD_STROKE_WIDTH = 10
private const val TAG = "ConfigLanguageFragment"

class ConfigLanguageFragment : Fragment(R.layout.fragment_config_language) {

    private var _binding :FragmentConfigLanguageBinding? = null
    private val binding get() = _binding!!

    private val configViewModel :ConfigViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentConfigLanguageBinding.bind(view)

        // English Card
        binding.enCard.setOnClickListener {
            configViewModel.saveContentLanguageState(Constants.EN_LANGUAGE)
            selectedCard(Constants.EN_LANGUAGE)
            showNextButton()
        }

        // Arabic Card
        binding.arCard.setOnClickListener {
            selectedCard(Constants.AR_LANGUAGE)
            configViewModel.saveContentLanguageState(Constants.AR_LANGUAGE)
            showNextButton()
        }

        // Next Button OnClick Action
        binding.configNextBut.setOnClickListener {
            // Go To Select Country Fragment
            if (configViewModel.loadContentLanguageState().isNotBlank()) {
                findNavController()
                    .navigate(R.id.action_configLanguageFragment_to_configAppStyleFragment)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // init state
        selectedCard(configViewModel.loadContentLanguageState())
        showNextButton()
    }

    private fun selectedCard(selectedLang :String = "") {
        if (selectedLang == Constants.EN_LANGUAGE){
            binding.arCard.strokeWidth = 0
            binding.enCard.strokeWidth = CARD_STROKE_WIDTH
            binding.enCard.strokeColor = ContextCompat.getColor(requireContext(),R.color.red_500)
        } else if (selectedLang == Constants.AR_LANGUAGE) {
            binding.enCard.strokeWidth = 0
            binding.arCard.strokeWidth = CARD_STROKE_WIDTH
            binding.arCard.strokeColor = ContextCompat.getColor(requireContext(),R.color.red_500)
        } else {
            Toast.makeText(requireContext(), "Not Lang Save", Toast.LENGTH_SHORT).show()
        }
        Log.d(TAG, "Language Saved -> ${configViewModel.loadContentLanguageState()}")
    }

    private fun showNextButton(){
        if (configViewModel.loadContentLanguageState().isNotBlank()) {
            binding.configNextBut.animate().apply {
                translationY(0f)
                duration = 500
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}