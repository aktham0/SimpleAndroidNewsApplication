package com.app.aktham.newsapplication.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.databinding.ActivityConfigurationBinding
import com.app.aktham.newsapplication.ui.viewModels.ConfigViewModel
import com.app.aktham.newsapplication.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfigurationActivity : AppCompatActivity() {

    private var _binding :ActivityConfigurationBinding? = null
    private val binding get() = _binding!!

    private val configViewModel :ConfigViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Chick Application Style (Dark Or Light)
        configViewModel.changAppStyle()
        // chick if configuration is already shown
        val firstAppStart = getSharedPreferences(Constants.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
            .getBoolean(Constants.PREFERENCE_FIRST_START_KET, false)
        if (firstAppStart){
            // close Configuration Activity and go to mainActivity
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }

        setTheme(R.style.Theme_NewsApplication)

        _binding = ActivityConfigurationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Change Background & StatusBar Color
        window?.let {
            it.statusBarColor = getColor(android.R.color.transparent)
            it.setBackgroundDrawableResource(R.drawable.config_activity_gradient_background)
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