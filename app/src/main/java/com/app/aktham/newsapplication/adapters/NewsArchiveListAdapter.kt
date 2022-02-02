package com.app.aktham.newsapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.app.aktham.newsapplication.databinding.NewsArchiveListItemViewBinding
import com.app.aktham.newsapplication.models.NewsModel
import com.app.aktham.newsapplication.utils.MyListsListener

class NewsArchiveListAdapter(
    private val newsListListener: MyListsListener<NewsModel>
) : ListAdapter<NewsModel, NewsArchiveListAdapter.NewsArchiveListViewHolder>(DiffUtil) {

    companion object {
        private val DiffUtil = object : DiffUtil.ItemCallback<NewsModel>() {
            override fun areContentsTheSame(
                oldItem: NewsModel,
                newItem: NewsModel
            ) = oldItem.newsTitle == newItem.newsTitle

            override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArchiveListViewHolder {
        val listBinding = NewsArchiveListItemViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NewsArchiveListViewHolder(listBinding)
    }

    override fun onBindViewHolder(holder: NewsArchiveListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // List View Holder
    inner class NewsArchiveListViewHolder(private val binding: NewsArchiveListItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // OnList Item Click Action
            binding.root.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                    newsListListener.onItemClick(
                        absoluteAdapterPosition,
                        getItem(absoluteAdapterPosition)
                    )
                }
            }
        }

        fun bind(newsData: NewsModel) {
            binding.newsArchiveItemTitleTv.text = newsData.newsTitle
            binding.newsArchiveItemIv.load(newsData.newsImageUrl)
        }

    }
}