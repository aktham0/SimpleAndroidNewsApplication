package com.app.aktham.newsapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.adapters.SearchAdapter
import com.app.aktham.newsapplication.databinding.FragmentSearchNewsBinding
import com.app.aktham.newsapplication.models.NewsSearchModel
import com.app.aktham.newsapplication.ui.viewModels.NewsViewModel
import com.app.aktham.newsapplication.utils.NewsRecourses

class SearchNewsFragment : Fragment(R.layout.fragment_search_news),
    SearchAdapter.SearchListListener {

    private var _binding: FragmentSearchNewsBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel: NewsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchNewsBinding.bind(view)


        // Set Up Search List 
        val searchAdapter = SearchAdapter(this)
        binding.searchRecyclerView.setHasFixedSize(true)
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchRecyclerView.adapter = searchAdapter

        // Search View Listener
        binding.newsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean =
                false

            override fun onQueryTextSubmit(query: String?): Boolean {
                newsViewModel.getNewsData(
                    NewsViewModel.GetNewsDataEvents.NewsByQuery(
                        query?.trim().toString()
                    )
                )
                return true
            }
        })

        // Observe Search Data
        newsViewModel.newsSearchLiveData.observe(viewLifecycleOwner, { searchResult ->
            when (searchResult) {
                is NewsRecourses.Loading -> {
                    // Show Loading Progress
                    binding.newsLoadingLayout.visibility = View.VISIBLE
                    binding.newsProgress.visibility = View.VISIBLE
                    binding.messageFeedBack.text = "Please White"
                }

                is NewsRecourses.Success<*> -> {
                    // Hide Loading Progress
                    binding.newsLoadingLayout.visibility = View.GONE
                    // Submit Data To List
                    searchAdapter.submitList((searchResult.dataList as List<NewsSearchModel>))
                }

                is NewsRecourses.Empty -> {
                    // No Data
                    binding.newsLoadingLayout.visibility = View.VISIBLE
                    binding.newsProgress.visibility = View.GONE
                    binding.messageFeedBack.apply {
                        visibility = View.VISIBLE
                        text = "No Data Found!"
                    }
                }

                is NewsRecourses.Errors -> {
                    // Hide Loading Progress
                    binding.newsLoadingLayout.visibility = View.GONE
                    // Show Error Message To User
                    binding.newsLoadingLayout.visibility = View.VISIBLE
                    binding.messageFeedBack.apply {
                        visibility = View.VISIBLE
                        text = searchResult.message
                    }
                }
            }
        })

        binding.newsSearchView.isFocusable = true
        binding.newsSearchView.requestFocus()

    }

    // On Search Item Click Listener
    override fun onClick(position: Int, listItemData: NewsSearchModel) {
        // Go To News Details
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}