package com.app.aktham.newsapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.app.aktham.newsapplication.R
import com.app.aktham.newsapplication.databinding.NewsListItemViewBinding
import com.app.aktham.newsapplication.models.NewsModel
import com.app.aktham.newsapplication.utils.MyListsListener

class NewsListAdapter(
    private val newsListListener: MyListsListener<NewsModel>
) : ListAdapter<NewsModel, NewsListAdapter.NewsListViewHolder>(DiffUtil) {

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
                    newsListListener.onItemClick(
                        absoluteAdapterPosition,
                        getItem(absoluteAdapterPosition)
                    )
                }
            }

            // OnList Item Long Press Action
            binding.root.setOnLongClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                    newsListListener.onItemLongPress(
                        absoluteAdapterPosition,
                        it,
                        getItem(absoluteAdapterPosition)
                    )
                    true
                } else false
            }
        }

        fun bind(newsData: NewsModel) {
            binding.newsTitle.text = newsData.newsTitle
            binding.newsPublishByTv.text = newsData.newsPublishBy
            binding.newsPublishDateTv.text = newsData.newsPublishDate

            // Load Image Using Coil Library
            binding.newsImage.load(newsData.newsImageUrl) {
                crossfade(750)
                error(R.drawable.ic_baseline_error_outline_24)
            }
        }

    }
}