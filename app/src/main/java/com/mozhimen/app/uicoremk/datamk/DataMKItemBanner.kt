package com.mozhimen.app.uicoremk.datamk

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.app.R
import com.mozhimen.uicoremk.datamk.DataMK
import com.mozhimen.uicoremk.datamk.commons.DataMKItem

/**
 * @ClassName DataMKItemBanner
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/1 14:39
 * @Version 1.0
 */
class DataMKItemBanner(data: DataMK) : DataMKItem<DataMK, RecyclerView.ViewHolder>(data) {
    override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.datamk_item_banner_img)
        imageView.setImageResource(R.mipmap.datamk_item_banner)
    }

    override fun getItemLayoutRes() = R.layout.datamk_item_banner
}