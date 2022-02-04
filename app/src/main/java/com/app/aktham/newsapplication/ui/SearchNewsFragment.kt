package com.app.aktham.newsapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.adapters.SearchAdapter
import com.app.aktham.newsapplication.databinding.FragmentSearchNewsBinding
import com.app.aktham.newsapplication.models.NewsModel
import com.app.aktham.newsapplication.ui.viewModels.NewsViewModel
import com.app.aktham.newsapplication.utils.MyListsListener
import com.app.aktham.newsapplication.utils.NewsRecourses

private const val TAG = "SearchNewsFragment"

class SearchNewsFragment : Fragment(
    R.layout.fragment_search_news
), MyListsListener<NewsModel> {

    private var _binding: FragmentSearchNewsBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel: NewsViewModel by activityViewModels()

    private lateinit var searchListAdapter: SearchAdapter
    private var oldQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // clear search live data to init value
        newsViewModel.setNewsEvent(NewsViewModel.NewsDataEvents.ClearSearchLiveData)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchNewsBinding.bind(view)

        // init search recyclerView
        initSearchList()
        // observing Search list Data
        observingSearchData()

        // Search View Listener
        binding.newsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it != oldQuery) // to avoid mack search on return from details fragment
                        search(it)

                    oldQuery = it
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
        })

        // on close search view
        binding.newsSearchView.setOnCloseListener {
            // clear search livedata
            newsViewModel.setNewsEvent(NewsViewModel.NewsDataEvents.ClearSearchLiveData)
            true
        }

    }

    private fun search(query: String) {
        if (query.length >= 3) {
            newsViewModel.setNewsEvent(
                NewsViewModel.NewsDataEvents.NewsByQuery(
                    query.trim()
                )
            )
        }
    }

    // SetUp Search RecyclerList View
    private fun initSearchList() {
        // init list adapter
        searchListAdapter = SearchAdapter(this)

        binding.searchRecyclerView.setHasFixedSize(true)
        // set linear shape to list
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        // add adapter to search recyclerList
        binding.searchRecyclerView.adapter = searchListAdapter
    }

    // Observing Data
    private fun observingSearchData() {
        // Observe Search Data
        newsViewModel.newsSearchLiveData.observe(viewLifecycleOwner) { searchResult ->
            // Hide Views
            binding.searchRecyclerView.visibility = View.GONE
            binding.newsSearchProgress.visibility = View.GONE
            binding.messageFeedBackTv.visibility = View.GONE
            binding.searchFeedbackIv.visibility = View.GONE

            Log.d(TAG, "Search LiveData Observe")

            when (searchResult) {
                is NewsRecourses.Loading -> {
                    // Show Loading Progress
                    binding.newsSearchProgress.visibility = View.VISIBLE
                }

                is NewsRecourses.Success<*> -> {
                    binding.searchRecyclerView.visibility = View.VISIBLE
                    // Submit Data To List
                    searchListAdapter.submitList((searchResult.dataList as List<NewsModel>))
                }

                is NewsRecourses.Errors -> {
                    // Errors
                    // Show Error Message
                    binding.messageFeedBackTv.apply {
                        text = searchResult.error_message
                        visibility = View.VISIBLE
                    }
                }

                // start point value
                is NewsRecourses.Init -> {
                    // show search image
                    binding.searchFeedbackIv.visibility = View.VISIBLE
                }
            }

        }
    }

    // On Search Item Click Listener
    override fun onItemClick(position: Int, newItem: NewsModel) {
        // Go To News Details
        val action = SearchNewsFragmentDirections.actionSearchNewsFragmentToNewsDetailsFragment(
            newsObject = newItem
        )
        // navigate to details fragment with news details object as a argument
        findNavController().navigate(action)
    }

    // On Search Item Loong Press
    override fun onItemLongPress(position: Int, itemView: View, itemObjet: NewsModel) {
        PopupMenu(requireContext(), itemView).apply {
            // inflate menu xml
            menuInflater.inflate(R.menu.list_item_popup_menu, menu)
            // on menu click item listener
            setOnMenuItemClickListener {
                // save in db
                newsViewModel.setNewsEvent(NewsViewModel.NewsDataEvents.InsertNewsArticle(itemObjet))
                Toast.makeText(
                    requireContext(),
                    "Saved",
                    Toast.LENGTH_SHORT
                ).show()
                true
            }

            // show menu
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}