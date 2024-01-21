package com.mozhimen.componentktest.pagingk

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.ItemPagingkLoadstateBinding

/**
 * @author huanglinqing
 * @project PagingDataDemo
 * @date 2020/11/14
 * @desc 尾部adapter
 */
class LoadStateViewHolder(parent: ViewGroup, var onRetry: I_Listener) :
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pagingk_loadstate, parent, false)) {

    var vb: ItemPagingkLoadstateBinding = ItemPagingkLoadstateBinding.bind(itemView)

    fun bindState(loadState: LoadState) {
        when (loadState) {
            is LoadState.Error -> {
                vb.btnRetry.visibility = View.VISIBLE
                vb.btnRetry.setOnClickListener {
                    onRetry()
                }
                Log.d("MainActivity", "error了吧")
            }

            is LoadState.Loading -> {
                vb.llLoading.visibility = View.VISIBLE
            }

            else -> {
                Log.d("MainActivity", "--其他的错误")
            }
        }

    }
}