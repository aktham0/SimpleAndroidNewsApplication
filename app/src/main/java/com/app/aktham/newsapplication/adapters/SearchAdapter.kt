package com.app.aktham.newsapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.databinding.SearchListItemViewBinding
import com.app.aktham.newsapplication.models.NewsModel
import com.app.aktham.newsapplication.utils.MyListsListener

class SearchAdapter(
    private val searchListListener: MyListsListener<NewsModel>
) : ListAdapter<NewsModel, SearchAdapter.SearchViewHolder>(diffUtil) {

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<NewsModel>() {
            override fun areContentsTheSame(
                oldItem: NewsModel,
                newItem: NewsModel
            ) = oldItem.newsTitle == newItem.newsTitle

            override fun areItemsTheSame(
                oldItem: NewsModel,
                newItem: NewsModel
            ) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val listBinding = SearchListItemViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SearchViewHolder(listBinding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // Search List View Holder
    inner class SearchViewHolder(private val binding: SearchListItemViewBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {

        init {
            // On List Item Click
            binding.searchCard.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION){
                    searchListListener.onItemClick(absoluteAdapterPosition,
                        getItem(absoluteAdapterPosition))
                }
            }
            // On List Item Loong Press
            binding.searchCard.setOnLongClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                    searchListListener.onItemLongPress(
                        absoluteAdapterPosition,
                        it,
                        getItem(absoluteAdapterPosition)
                    )
                    true
                } else {
                    false
                }
            }
        }

        fun bind(data: NewsModel) {
            binding.searchTitle.text = data.newsTitle
            binding.searchPublishBy.text = data.newsPublishBy
            binding.searchImage.load(
                data.newsImageUrl
            ) {
                crossfade(750)
                placeholder(R.drawable.ic_baseline_image_search_24)
                error(R.drawable.ic_baseline_error_outline_24)
            }
        }
    }

}