package com.app.aktham.newsapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.adapters.NewsListAdapter
import com.app.aktham.newsapplication.databinding.FragmentMainListNewsBinding
import com.app.aktham.newsapplication.models.NewsModel
import com.app.aktham.newsapplication.ui.viewModels.NewsViewModel
import com.app.aktham.newsapplication.utils.Constants
import com.app.aktham.newsapplication.utils.MyListsListener
import com.app.aktham.newsapplication.utils.NewsRecourses
import com.google.android.material.tabs.TabLayout

private const val TAG = "MainListNewsFragment"

class MainListNewsFragment : Fragment(R.layout.fragment_main_list_news),
    MyListsListener<NewsModel> {

    private var _binding: FragmentMainListNewsBinding? = null
    private val binding get() = _binding!!

    private val newsViewModel: NewsViewModel by activityViewModels()
    private lateinit var newsListAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get new data
        newsViewModel.setNewsEvent(NewsViewModel.NewsDataEvents.NewsByCategory())

        Log.d(TAG, "Main News List Fragment onCreated")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainListNewsBinding.bind(view)
        Log.d(TAG, "Main News List Fragment onViewCreated")

        // setup news recycler list
        initNewsList()
        // Set Up TabLayout For News Category
        initNewsCategoryTab()
        // Observe News Data
        observingNewsData()
    }

    // SetUp News RecyclerList
    private fun initNewsList() {
        // init news list adapter
        newsListAdapter = NewsListAdapter(this)
        binding.mainNewsRecyclerView.setHasFixedSize(true)
        binding.mainNewsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // set adapter to news recyclerList
        binding.mainNewsRecyclerView.adapter = newsListAdapter
    }

    private fun initNewsCategoryTab() {
        Constants.NewsCategories.forEach { newsCategory ->
            val tabItem = binding.categoryTabLayout.newTab().also {
                it.text = newsCategory.uppercase()
            }

            val isSelected =
                newsCategory.lowercase() == newsViewModel.newsSelectedCategory.lowercase()
            binding.categoryTabLayout.selectTab(tabItem, true)
            // add tab item to tab layout
            binding.categoryTabLayout.addTab(tabItem, isSelected)
        }

        // News Categories Tab OnSelected Action
        binding.categoryTabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // save selected item in viewModel
                newsViewModel.newsSelectedCategory = tab?.text.toString().lowercase()
                // get news with specific category
                tab?.let {
                    newsViewModel.setNewsEvent(
                        NewsViewModel.NewsDataEvents.NewsByCategory(
                            newsViewModel.newsSelectedCategory
                        )
                    )
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })

    }

    // Observe New Data
    private fun observingNewsData() {
        // observe data
        newsViewModel.newsLiveData.observe(viewLifecycleOwner) {
            binding.newsProgress.visibility = View.GONE
            binding.messageFeedBackTv.visibility = View.GONE
            binding.mainNewsRecyclerView.visibility = View.GONE

            when (it) {
                is NewsRecourses.Loading -> {
                    // Show Progress View
                    binding.newsProgress.visibility = View.VISIBLE
                }

                is NewsRecourses.Success<*> -> {
                    // Update News List Data
                    newsListAdapter.submitList((it.dataList as List<NewsModel>))
                    // Show News RecyclerList
                    binding.mainNewsRecyclerView.visibility = View.VISIBLE
                }

                is NewsRecourses.Errors -> {
                    // Show Error View
                    binding.messageFeedBackTv.apply {
                        text = it.error_message
                        visibility = View.VISIBLE
                    }
                }

                else -> {}
            }
        }
    }


    // News List On Click item
    override fun onItemClick(position: Int, itemObject: NewsModel) {
        // Go To News Details Fragment
        val action = MainListNewsFragmentDirections
            .actionMainListNewsFragmentToNewsDetailsFragment(newsObject = itemObject)
        findNavController().navigate(action)
    }

    // on list item long click listener
    override fun onItemLongPress(position: Int, itemView: View, itemObjet: NewsModel) {
        PopupMenu(requireContext(), itemView).apply {
            // inflate menu xml
            menuInflater.inflate(R.menu.list_item_popup_menu, menu)
            // on menu click item listener
            setOnMenuItemClickListener {
                // save in db
                val s = newsViewModel.insertNewsArticle(itemObjet)
                Toast.makeText(
                    requireContext(),
                    "Saved $s",
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