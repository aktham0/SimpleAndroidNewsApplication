package com.app.aktham.newsapplication.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.forEach
import androidx.navigation.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    companion object {

        fun intent(context: Activity) {
            context.startActivity(Intent(
                context, MainActivity::class.java
            ))

            context.finish()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init navigation controller
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_nav_host) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onStart() {
        super.onStart()
        // link bottom navigation view with navigation controller
        binding.bottomNavView.setupWithNavController(navController)

        // On Navigation Navigate
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Change Toolbar title to fragment label name
            binding.actionTitleTv.text = destination.label

            // disable selected item in bottom navigation view to avoid multiples click
            disableBottomViewSelectedItem(destination.id)

            when(destination.id) {
                // Hide Bottom Navigation View In Details News Fragment
                R.id.newsDetailsFragment -> {
                    binding.bottomNavView.animate().apply {
                        translationY(300f)
                        duration = 500L
                        withEndAction { binding.bottomNavView.visibility = View.GONE }
                        start()
                    }

                    // Hide action bar card in details news fragment
                    binding.headerCard.animate().apply {
                        translationY(-200f)
                        duration = 200L
                        withEndAction { binding.headerCard.visibility = View.GONE }
                        start()
                    }
                }

                // Show Bottom Navigation View
                else -> {
                    // Show Bottom Nav View
                    binding.bottomNavView.animate().apply {
                        withStartAction { binding.bottomNavView.visibility = View.VISIBLE }
                        translationY(0f)
                        duration = 500L
                        start()
                    }
                    // Show action bar card
                    binding.headerCard.animate().apply {
                        translationY(0f)
                        duration = 200L
                        withStartAction { binding.headerCard.visibility = View.VISIBLE }
                        start()
                    }
                }
            }
        }
    }

    // Disable Bottom Item To avoid multiples click on it
    private fun disableBottomViewSelectedItem(itemId: Int) {
        binding.bottomNavView.menu.forEach {
            it.isEnabled = itemId != it.itemId
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onBackPressed() {
        if (!navController.popBackStack()) {
            super.onBackPressed()
            finish()
        }

        Log.d(TAG, "Stack Size -> ${navController.backStack.size}")
    }

    override fun onNavigateUp(): Boolean {
        Log.d(TAG, "OnNavigate Up")
        return super.onNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}