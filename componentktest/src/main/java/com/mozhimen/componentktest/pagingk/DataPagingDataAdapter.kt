package com.mozhimen.componentktest.pagingk

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.ItemPagingkBinding
import com.mozhimen.componentktest.pagingk.restful.mos.DataRes

/**
 * @ClassName DataPagingDataAdapter
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/21 22:51
 * @Version 1.0
 */
class DataPagingDataAdapter(
    private val _onItemUpdate: (Int, DataRes.DataBean.DatasBean?, DataPagingDataAdapter) -> Unit
) : PagingDataAdapter<DataRes.DataBean.DatasBean, DataViewHolder>(DataDiffItemCallback()) {
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val dataBean = getItem(position)
        holder.vb.itemData = dataBean
        holder.vb.btnUpdate.setOnClickListener {
            _onItemUpdate(position, dataBean, this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val vb: ItemPagingkBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_pagingk, parent, false)
        return DataViewHolder(vb)
    }
}