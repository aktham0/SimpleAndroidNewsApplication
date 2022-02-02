package com.app.aktham.newsapplication.utils

import android.view.View

interface MyListsListener<T> {
    fun onItemClick(position: Int, itemObject: T)
    fun onItemLongPress(position: Int, itemView: View, itemObjet: T) {}
}