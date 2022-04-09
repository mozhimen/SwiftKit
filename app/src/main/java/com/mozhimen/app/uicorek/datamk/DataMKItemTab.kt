package com.mozhimen.app.uicorek.datak

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.app.R
import com.mozhimen.uicorek.datak.DataK
import com.mozhimen.uicorek.datak.commons.DataKItem

/**
 * @ClassName DataKItemTab
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/2 16:03
 * @Version 1.0
 */
class DataKItemTab(data: DataK?) : DataKItem<DataK, DataKItemTab.TabViewHolder>(data) {
    override fun onBindData(holder: TabViewHolder, position: Int) {
        holder.imageView.setImageResource(R.mipmap.datak_item_tab)
    }

    override fun getItemLayoutRes() = R.layout.datak_item_tab

    class TabViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.datak_item_tab_img)
    }
}