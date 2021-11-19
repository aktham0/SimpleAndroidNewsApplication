package com.app.aktham.newsapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.databinding.NewsListItemViewBinding
import com.app.aktham.newsapplication.models.NewsListModel

class NewsListAdapter(
    private val newsListListener: NewsListListener
) : ListAdapter<NewsListModel, NewsListAdapter.NewsListViewHolder>(DiffUtil) {

    companion object {
        private val DiffUtil = object : DiffUtil.ItemCallback<NewsListModel>() {
            override fun areContentsTheSame(
                oldItem: NewsListModel,
                newItem: NewsListModel
            ) = oldItem.newsTitle == newItem.newsTitle

            override fun areItemsTheSame(oldItem: NewsListModel, newItem: NewsListModel) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        val listBinding = NewsListItemViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NewsListViewHolder(listBinding)
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // List View Holder
    inner class NewsListViewHolder(private val binding: NewsListItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // OnList Item Click Action
            binding.root.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                    newsListListener.onClick(
                        absoluteAdapterPosition,
                        getItem(absoluteAdapterPosition)
                    )
                }
            }
        }

        fun bind(newsData: NewsListModel) {
            binding.newsTitle.text = newsData.newsTitle
            binding.newsPublishByTv.text = newsData.newsPublishBy
            binding.newsPublishDateTv.text = newsData.newsPublishDate

            // Load Image Using Coil Library
            binding.newsImage.load(newsData.newsImageUrl) {
                crossfade(750)
                scale(Scale.FILL)
                placeholder(R.drawable.ic_baseline_image_search_24)
                error(R.drawable.ic_baseline_error_outline_24)
            }
        }
    }

    interface NewsListListener {
        fun onClick(position: Int, newsData: NewsListModel)
    }

}