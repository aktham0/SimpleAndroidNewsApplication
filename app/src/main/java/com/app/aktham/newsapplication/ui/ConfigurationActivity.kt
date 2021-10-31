package com.app.aktham.newsapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.databinding.ActivityConfigurationBinding
import com.app.aktham.newsapplication.ui.viewModels.ConfigViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfigurationActivity : AppCompatActivity() {

    private var _binding :ActivityConfigurationBinding? = null
    private val binding get() = _binding!!

    private val configViewModel :ConfigViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NewsApplication)
        // Chick Application Style (Dark Or Light)
        configViewModel.changAppStyle()
        setContentView(R.layout.activity_configuration)
        // Change Background & StatusBar Color
        window?.let {
            it.statusBarColor = getColor(android.R.color.transparent)
            it.setBackgroundDrawableResource(R.drawable.config_activity_gradient_background)
        }

    }

    override fun onStart() {
        super.onStart()
        val configNavController = findNavController(R.id.config_nav_host)
        configNavController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.configLanguageFragment -> {

                }

                R.id.configCountryFragment -> {

                }
            }
        }
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.config_nav_host)
        if (!navController.popBackStack()){
            super.onBackPressed()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}