package com.app.aktham.newsapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.app.aktham.newsapplication.databinding.CountryListItemViewBinding
import com.app.aktham.newsapplication.models.CountryListModel
import com.app.aktham.newsapplication.utils.Constants

class CountryListAdapter(
    val listData :List<CountryListModel>,
    val listListener :CountryListAction
) : RecyclerView.Adapter<CountryListAdapter.CountryListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListViewHolder {
        val listBinding = CountryListItemViewBinding.inflate(
            LayoutInflater.from(parent.context)
        )
        return CountryListViewHolder(listBinding)
    }

    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount() = listData.size

    inner class CountryListViewHolder(
        private val binding :CountryListItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                listData.forEach { it.isSelected = false }
                listData[absoluteAdapterPosition].isSelected = true
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION)
                    listListener.onItemClick(absoluteAdapterPosition, listData[absoluteAdapterPosition])
                notifyDataSetChanged()
            }
        }

        // Bind View Data
        fun bind(data: CountryListModel) {
            binding.countryListItemText.text = data.countryName
            binding.countryListStateIv.isVisible = data.isSelected
            binding.countryListItemCode.text = data.countryCode
        }

    }

    // List Action Listener
    interface CountryListAction {
        fun onItemClick(position: Int, itemData :CountryListModel)
    }
}