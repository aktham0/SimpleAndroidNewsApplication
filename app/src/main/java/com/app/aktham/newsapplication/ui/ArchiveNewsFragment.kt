package com.app.aktham.newsapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.adapters.NewsArchiveListAdapter
import com.app.aktham.newsapplication.databinding.FragmentArchiveNewsBinding
import com.app.aktham.newsapplication.models.NewsModel
import com.app.aktham.newsapplication.ui.viewModels.NewsViewModel
import com.app.aktham.newsapplication.utils.MyListsListener

class ArchiveNewsFragment : Fragment(R.layout.fragment_archive_news),
    MyListsListener<NewsModel> {

    private var _binding: FragmentArchiveNewsBinding? = null
    private val binding get() = _binding!!

    private val newsViewModel: NewsViewModel by activityViewModels()
    private lateinit var adapter: NewsArchiveListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArchiveNewsBinding.bind(view)

        // setUp Recycler View
        initArchiveRecyclerList()
        // observing data
        observingListData()
    }

    // observe data from view model
    private fun observingListData() {
        // get news archive data
        newsViewModel.setNewsEvent(NewsViewModel.NewsDataEvents.GetArchiveNews)

        // observe news archive liveData
        newsViewModel.newsArchiveArticlesLiveData.observe(viewLifecycleOwner) { newsArchiveList ->
            binding.newsArchiveRecyclerView.visibility = View.GONE
            binding.notFoundDataLayout.visibility = View.GONE
            if (newsArchiveList.isEmpty()) {
                // show not found data layout
                binding.notFoundDataLayout.visibility = View.VISIBLE
            } else {
                // show recycler list view
                binding.newsArchiveRecyclerView.visibility = View.VISIBLE
                // set data to lise adapter
                adapter.submitList(newsArchiveList)
            }
        }
    }

    private fun initArchiveRecyclerList() {
        adapter = NewsArchiveListAdapter(this)

        binding.newsArchiveRecyclerView.setHasFixedSize(true)
        binding.newsArchiveRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.newsArchiveRecyclerView.adapter = adapter

        // add swipe functionality to list
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // remove from news article from DataBase
                Toast.makeText(requireContext(), "Swipe", Toast.LENGTH_LONG).show()
            }

        }).attachToRecyclerView(binding.newsArchiveRecyclerView)
    }


    // on list item click
    override fun onItemClick(position: Int, itemObject: NewsModel) {
        // navigate to details fragment
        val action = ArchiveNewsFragmentDirections.actionArchiveNewsFragmentToNewsDetailsFragment(
            newsObject = itemObject
        )
        findNavController().navigate(action)
    }

    override fun onItemLongPress(position: Int, itemView: View, itemObjet: NewsModel) {
        // delete item from list
        newsViewModel.setNewsEvent(NewsViewModel.NewsDataEvents.DeleteNewsArticle(
            news = itemObjet
        ))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}