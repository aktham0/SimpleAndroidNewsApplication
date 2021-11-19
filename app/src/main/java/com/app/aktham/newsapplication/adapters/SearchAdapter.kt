package com.app.aktham.newsapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.databinding.SearchListItemViewBinding
import com.app.aktham.newsapplication.models.NewsSearchModel

class SearchAdapter(
    private val searchListListener: SearchListListener
) : ListAdapter<NewsSearchModel, SearchAdapter.SearchViewHolder>(diffUtil) {

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<NewsSearchModel>() {
            override fun areContentsTheSame(
                oldItem: NewsSearchModel,
                newItem: NewsSearchModel
            ) = oldItem.searchTitle == newItem.searchTitle

            override fun areItemsTheSame(
                oldItem: NewsSearchModel,
                newItem: NewsSearchModel
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
                    searchListListener.onClick(absoluteAdapterPosition,
                        getItem(absoluteAdapterPosition))
                }
            }
        }

        fun bind(data: NewsSearchModel) {
            binding.searchTitle.text = data.searchTitle
            binding.searchPublishBy.text = data.searchPublishBy
            binding.searchImage.load(
                data.searchImage
            ) {
                crossfade(750)
                placeholder(R.drawable.ic_baseline_image_search_24)
                error(R.drawable.ic_baseline_error_outline_24)
            }
        }
    }

    // Search List Item Listener Interface
    interface SearchListListener {
        fun onClick(position: Int, listItemData: NewsSearchModel)
    }
}