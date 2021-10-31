package com.app.aktham.newsapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.databinding.FragmentConfigStyleBinding
import com.app.aktham.newsapplication.ui.viewModels.ConfigViewModel

private const val CARD_STROKE_WIDTH = 10

class ConfigAppStyleFragment : Fragment(R.layout.fragment_config_style) {

    private var _binding: FragmentConfigStyleBinding? = null
    private val binding get() = _binding!!

    private val configViewModel: ConfigViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentConfigStyleBinding.bind(view)

        // init state
        selectedCard()
        showNextButton()

        // Dark Style Card
        binding.darkCard.setOnClickListener {
            configViewModel.saveStyleState(AppCompatDelegate.MODE_NIGHT_YES)
            selectedCard()
            // Change Application Style
            configViewModel.changAppStyle()
        }

        // Auto Style Card
        binding.autoCard.setOnClickListener {
            configViewModel.saveStyleState(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            selectedCard()
            // Change Application Style
            configViewModel.changAppStyle()
        }

        // Light Style Card
        binding.lightCard.setOnClickListener {
            configViewModel.saveStyleState(AppCompatDelegate.MODE_NIGHT_NO)
            selectedCard()
            // Change Application Style
            configViewModel.changAppStyle()
        }

        // Back Button OnClick Action
        binding.configBackBut.setOnClickListener {
            findNavController().popBackStack()
        }

        // Next Button OnClick Action
        binding.configNextBut.setOnClickListener {
            // Go To Select Country Fragment
            findNavController()
                .navigate(R.id.action_configAppStyleFragment_to_configCountryFragment)
        }

    }

    private fun selectedCard() {
        when (configViewModel.loadStyleState()) {
            AppCompatDelegate.MODE_NIGHT_YES -> { // Dark Mode
                binding.autoCard.strokeWidth = 0
                binding.lightCard.strokeWidth = 0
                binding.darkCard.strokeWidth = CARD_STROKE_WIDTH
                binding.darkCard.strokeColor =
                    ContextCompat.getColor(requireContext(), R.color.purple_500)
            }
            AppCompatDelegate.MODE_NIGHT_NO -> { // Light Mode
                binding.autoCard.strokeWidth = 0
                binding.darkCard.strokeWidth = 0
                binding.lightCard.strokeWidth = CARD_STROKE_WIDTH
                binding.lightCard.strokeColor =
                    ContextCompat.getColor(requireContext(), R.color.purple_500)
            }

            else -> {
                binding.darkCard.strokeWidth = 0
                binding.lightCard.strokeWidth = 0
                binding.autoCard.strokeWidth = CARD_STROKE_WIDTH
                binding.autoCard.strokeColor =
                    ContextCompat.getColor(requireContext(), R.color.purple_500)
            }
        }
    }

    private fun showNextButton() {
        binding.configNextBut.animate().apply {
            translationY(0f)
            duration = 500
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}