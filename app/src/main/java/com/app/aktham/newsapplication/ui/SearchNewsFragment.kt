package com.app.aktham.newsapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.adapters.SearchAdapter
import com.app.aktham.newsapplication.databinding.FragmentSearchNewsBinding
import com.app.aktham.newsapplication.models.NewsSearchModel
import com.app.aktham.newsapplication.ui.viewModels.NewsViewModel
import com.app.aktham.newsapplication.utils.NewsRecourses

class SearchNewsFragment : Fragment(
    R.layout.fragment_search_news
), SearchAdapter.SearchListListener {

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
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.length > 3) {
                        newsViewModel.getNewsData(
                            NewsViewModel.GetNewsDataEvents.NewsByQuery(
                                newText.trim()
                            )
                        )
                    }
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
        })

        // Observe Search Data
        newsViewModel.newsSearchLiveData.observe(viewLifecycleOwner, { searchResult ->
            // Hide Views
            binding.newsProgress.visibility = View.GONE
            binding.messageFeedBack.visibility = View.GONE
            binding.imageNoData.visibility = View.GONE
            binding.searchRecyclerView.visibility = View.GONE

            when (searchResult) {
                is NewsRecourses.Loading -> {
                    // Show Loading Progress
                    binding.newsProgress.visibility = View.VISIBLE
                }

                is NewsRecourses.Success<*> -> {
                    binding.searchRecyclerView.visibility = View.VISIBLE
                    // Submit Data To List
                    searchAdapter.submitList((searchResult.dataList as List<NewsSearchModel>))
                }

                is NewsRecourses.Empty -> {
                    // No Data
                    // Show Image No Data Founds
                    binding.imageNoData.visibility = View.VISIBLE
                }

                is NewsRecourses.Errors -> {
                    // Errors
                    // Show Error Message
                    binding.messageFeedBack.apply {
                        text = searchResult.message
                        visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    // On Search Item Click Listener
    override fun onClick(position: Int, listItemData: NewsSearchModel) {
        // Go To News Details
        // TODO: 11/20/2021
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}