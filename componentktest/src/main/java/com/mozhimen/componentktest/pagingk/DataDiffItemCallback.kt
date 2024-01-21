package com.mozhimen.componentktest.pagingk

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.mozhimen.componentktest.pagingk.restful.mos.DataRes

/**
 * @ClassName DataDiffItemCallback
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/21 22:56
 * @Version 1.0
 */
class DataDiffItemCallback : DiffUtil.ItemCallback<DataRes.DataBean.DatasBean>() {

    override fun areItemsTheSame(
        oldItem: DataRes.DataBean.DatasBean,
        newItem: DataRes.DataBean.DatasBean
    ): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: DataRes.DataBean.DatasBean,
        newItem: DataRes.DataBean.DatasBean
    ): Boolean {
        return oldItem == newItem
    }
}