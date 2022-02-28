package com.mozhimen.app.uicoremk.datamk

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.app.R
import com.mozhimen.uicoremk.datamk.DataMK
import com.mozhimen.uicoremk.datamk.commons.DataMKItem

/**
 * @ClassName DataMKItemTab
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/2 16:03
 * @Version 1.0
 */
class DataMKItemTab(data: DataMK?) : DataMKItem<DataMK, DataMKItemTab.TabViewHolder>(data) {
    override fun onBindData(holder: TabViewHolder, position: Int) {
        holder.imageView.setImageResource(R.mipmap.datamk_item_tab)
    }

    override fun getItemLayoutRes() = R.layout.datamk_item_tab

    class TabViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.datamk_item_tab_img)
    }
}