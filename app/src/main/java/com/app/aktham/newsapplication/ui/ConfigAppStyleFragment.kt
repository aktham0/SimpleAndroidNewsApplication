package com.app.aktham.newsapplication.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.databinding.FragmentConfigStyleBinding
import com.app.aktham.newsapplication.ui.viewModels.ConfigViewModel
import com.app.aktham.newsapplication.utils.Constants

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

        // Finish Button OnClick Action
        binding.configFinishBut.setOnClickListener {
            // Go To Main Activity
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            // Close Configuration Activity
            requireActivity().finish()
            // Save App First Start State To Share Preference
            requireActivity().getSharedPreferences(Constants.SHARED_PREFERENCE_FILE_NAME,
                Context.MODE_PRIVATE).edit {
                putBoolean(Constants.PREFERENCE_FIRST_START_KET, true)
            }
        }
    }

    private fun selectedCard() {
        when (configViewModel.loadStyleState()) {
            AppCompatDelegate.MODE_NIGHT_YES -> { // Dark Mode
                binding.autoCard.strokeWidth = 0
                binding.lightCard.strokeWidth = 0
                binding.darkCard.strokeWidth = CARD_STROKE_WIDTH
                binding.darkCard.strokeColor =
                    ContextCompat.getColor(requireContext(), R.color.red_500)
            }
            AppCompatDelegate.MODE_NIGHT_NO -> { // Light Mode
                binding.autoCard.strokeWidth = 0
                binding.darkCard.strokeWidth = 0
                binding.lightCard.strokeWidth = CARD_STROKE_WIDTH
                binding.lightCard.strokeColor =
                    ContextCompat.getColor(requireContext(), R.color.red_500)
            }

            else -> {
                binding.darkCard.strokeWidth = 0
                binding.lightCard.strokeWidth = 0
                binding.autoCard.strokeWidth = CARD_STROKE_WIDTH
                binding.autoCard.strokeColor =
                    ContextCompat.getColor(requireContext(), R.color.red_500)
            }
        }
    }

    private fun showNextButton() {
        binding.configFinishBut.animate().apply {
            translationY(0f)
            duration = 500
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}