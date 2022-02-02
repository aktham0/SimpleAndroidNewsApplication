package com.app.aktham.newsapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.aktham.newsapplication.databinding.SettingsItemViewBinding
import com.app.aktham.newsapplication.models.SettingsModel
import com.app.aktham.newsapplication.utils.MyListsListener

class SettingsAdapter(
    private val settingListener: MyListsListener<SettingsModel>
) : ListAdapter<SettingsModel, SettingsAdapter.SettingsViewHolder>(diffUtil) {

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<SettingsModel>() {
            override fun areContentsTheSame(
                oldItem: SettingsModel,
                newItem: SettingsModel
            ) = oldItem.tag == newItem.tag

            override fun areItemsTheSame(oldItem: SettingsModel, newItem: SettingsModel) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val viewBinding = SettingsItemViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SettingsViewHolder(viewBinding, parent.context)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // Settings List ViewHolder
    inner class SettingsViewHolder(val binding: SettingsItemViewBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // Item OnClick
            binding.settingItemCard.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                    settingListener.onItemClick(
                        absoluteAdapterPosition,
                        getItem(absoluteAdapterPosition)
                    )
                }
            }
        }

        fun bind(data: SettingsModel) {
            binding.settingItemTitle.text = data.title

            if (data.subTitle.isNotBlank())
                binding.settingItemSubTitle.text = data.subTitle
            else binding.settingItemSubTitle.visibility = View.GONE

            binding.settingItemIcon.setImageDrawable(
                ContextCompat.getDrawable(context, data.icon)
            )
        }
    }

}