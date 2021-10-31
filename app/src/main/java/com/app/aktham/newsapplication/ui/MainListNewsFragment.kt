package com.app.aktham.newsapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
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
import com.google.android.material.tabs.TabLayout

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
            when (it) {
                is NewsRecourses.Loading -> {
                    binding.loadingLayout.isVisible = true
                    binding.mainNewsRecyclerView.visibility = View.GONE
                    errorView(false)
                }

                is NewsRecourses.Success<*> -> {
                    binding.mainNewsRecyclerView.visibility = View.VISIBLE
                    binding.loadingLayout.isVisible = false
                    newsListAdapter.submitList((it.dataList as List<NewsListModel>))
                    errorView(false)
                }

                is NewsRecourses.Errors -> {
                    binding.loadingLayout.isVisible = false
                    // Show Error Message
                    errorView(true, it.message) {
                        // Reload
                        newsViewModel.getNewsData(NewsViewModel.GetNewsDataEvents.TopHeadlinesNews())
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

    // News Category's
    private fun initNewsCategory() {
        Constants.NewsCategories.forEach {
            val tab = binding.categoryTabLayout.newTab().apply {
                text = it
            }
            // Add Chip To Chips Group
            binding.categoryTabLayout.addTab(tab)
        }

        // TabLayout OnSelect Tab Action Listener
        binding.categoryTabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                newsViewModel.getNewsData(
                    NewsViewModel.GetNewsDataEvents.NewsByCategory(
                        tab?.text.toString().lowercase()
                    )
                )
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })
    }

    // Errors View Layout
    private fun errorView(state: Boolean, errorMsg: String = "", onReload: () -> Unit = {}){
        binding.errorLayout.visibility = if (state) View.VISIBLE else View.GONE
        binding.errorMsgTv.text = errorMsg
        binding.reloadBut.setOnClickListener {
            binding.errorLayout.visibility = View.GONE
            // reload
            onReload()
        }
    }

    // List Item OnClick
    override fun onClick(position: Int, newsData: NewsListModel) {
        val action = MainListNewsFragmentDirections
            .actionMainListNewsFragmentToNewsDetailsFragment(itemPosition = position)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}