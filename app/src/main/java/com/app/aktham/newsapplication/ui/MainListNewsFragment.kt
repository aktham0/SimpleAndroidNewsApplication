package com.app.aktham.newsapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.adapters.NewsListAdapter
import com.app.aktham.newsapplication.databinding.FragmentMainListNewsBinding
import com.app.aktham.newsapplication.models.NewsListModel
import com.app.aktham.newsapplication.ui.viewModels.NewsViewModel
import com.app.aktham.newsapplication.utils.Constants
import com.app.aktham.newsapplication.utils.NewsRecourses
import com.google.android.material.chip.Chip

class MainListNewsFragment : Fragment(R.layout.fragment_main_list_news),
    NewsListAdapter.NewsListListener {

    private var _binding: FragmentMainListNewsBinding? = null
    private val binding get() = _binding!!

    private val newsViewModel: NewsViewModel by activityViewModels()
    private lateinit var newsListAdapter: NewsListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainListNewsBinding.bind(view)

        // setup news recycler list
        initNewsList()
        // Set Up TabLayout For News Category
        initNewsCategory()

        // Observe News Data
        newsViewModel.newsLiveData.observe(viewLifecycleOwner, {
            binding.newsProgress.visibility = View.GONE
            binding.errorLayout.visibility = View.GONE
            binding.mainNewsRecyclerView.visibility = View.GONE
            when (it) {
                is NewsRecourses.Loading -> {
                    // Show Progress View
                    binding.newsProgress.visibility = View.VISIBLE
                }

                is NewsRecourses.Success<*> -> {
                    // Show News RecyclerList View
                    binding.mainNewsRecyclerView.visibility = View.VISIBLE
                    // Set News Data To List Adapter
                    newsListAdapter.submitList((it.dataList as List<NewsListModel>))
                }

                is NewsRecourses.Empty -> {
                    // Show Error Message
                    errorView("No Data Found !") { }
                }

                is NewsRecourses.Errors -> {
                    // Show Error View
                    errorView(it.message) {
                        // Reload
                        newsViewModel.getNewsData(
                            NewsViewModel.GetNewsDataEvents.TopHeadlinesNews()
                        )
                    }

                }
            }
        })

    }

    // SetUp News RecyclerList
    private fun initNewsList() {
        newsListAdapter = NewsListAdapter(this)
        binding.mainNewsRecyclerView.setHasFixedSize(true)
        binding.mainNewsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.mainNewsRecyclerView.adapter = newsListAdapter
    }

//    // News Category's
//    private fun initNewsCategory() {
//        Constants.NewsCategories.forEach {
//            val tab = binding.categoryTabLayout.newTab().apply {
//                text = it
//            }
//            // Add Chip To Chips Group
//            binding.categoryTabLayout.addTab(tab)
//        }
//
//        // TabLayout OnSelect Tab Action Listener
//        binding.categoryTabLayout.addOnTabSelectedListener(object :
//            TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                newsViewModel.getNewsData(
//                    NewsViewModel.GetNewsDataEvents.NewsByCategory(
//                        tab?.text.toString().lowercase()
//                    )
//                )
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {}
//            override fun onTabUnselected(tab: TabLayout.Tab?) {}
//        })
//    }

    private fun initNewsCategory() {
        Constants.NewsCategories.forEach { newsCategory ->
            val chip = Chip(requireContext()).apply {
                text = newsCategory.uppercase()
                isCheckable = true
                if (newsCategory.lowercase() == newsViewModel.newsSelectedCategory.lowercase()) {
                    isSelected = true
                }
            }
            binding.categoryChipsGroup.addView(chip)
        }

        // News Categories Chips OnSelected Action
        binding.categoryChipsGroup.setOnCheckedChangeListener { _, checkedId ->
            binding.categoryChipsGroup.findViewById<Chip>(checkedId).apply {
                isSelected = true
                newsViewModel.newsSelectedCategory = text.toString()
                newsViewModel.getNewsData(
                    NewsViewModel.GetNewsDataEvents.NewsByCategory(
                        text.toString()
                    )
                )
            }
            Toast.makeText(requireContext(), "Click", Toast.LENGTH_SHORT).show()
        }
    }

    // Errors View Layout
    private fun errorView(errorMsg: String = "", onReload: () -> Unit = {}) {
        // Show Error View
        binding.errorLayout.visibility = View.VISIBLE
        // Set Error Message To Error Text View
        binding.errorMsgTv.text = errorMsg
        // Reload Button On Click
        binding.reloadBut.setOnClickListener {
            // Hide Error View
            binding.errorLayout.visibility = View.GONE
            // Reload News Feed
            onReload()
        }
    }

    // List Item OnClick
    override fun onClick(position: Int, newsData: NewsListModel) {
        // Go To News Details Fragment
        val action = MainListNewsFragmentDirections
            .actionMainListNewsFragmentToNewsDetailsFragment(itemPosition = position)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}