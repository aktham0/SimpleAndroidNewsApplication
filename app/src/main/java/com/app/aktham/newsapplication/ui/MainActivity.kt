package com.app.aktham.newsapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        // Set Color For StatusBar And Activity Window
        window?.let {
            it.statusBarColor = ContextCompat.getColor(this,
                android.R.color.transparent)
            it.setBackgroundDrawableResource(R.drawable.main_activity_gradient_background)
        }

    }

    override fun onStart() {
        super.onStart()
        // Set Up Bottom Navigation View
        val navController = findNavController(R.id.main_nav_host)
        binding.bottomNavView.setupWithNavController(navController)

        // On Navigation Navigate
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Sync Tool bar Title With Fragment Label
            binding.toolbar.title = destination.label

            when(destination.id) {
                // Hide Bottom Navigation View In Details News Fragment
                R.id.newsDetailsFragment -> {
                    binding.bottomNavView.animate().apply {
                        translationY(300f)
                        duration = 500L
                        withEndAction { binding.bottomNavView.visibility = View.GONE }
                        start()
                    }
                }

                // Show Bottom Navigation View
                else -> {
                    binding.bottomNavView.animate().apply {
                        withStartAction { binding.bottomNavView.visibility = View.VISIBLE }
                        translationY(0f)
                        duration = 500L
                        start()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.main_nav_host)
        if (!navController.popBackStack()) {
            super.onBackPressed()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}