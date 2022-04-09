package com.mozhimen.app.uicorek.datak

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.app.R
import com.mozhimen.uicorek.datak.DataK
import com.mozhimen.uicorek.datak.commons.DataKItem

/**
 * @ClassName DataKItemImage
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/2 15:33
 * @Version 1.0
 */
class DataKItemImage(var spanCount: Int, data: DataK) :
    DataKItem<DataK, RecyclerView.ViewHolder>(data) {

    override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.datak_item_image_img)
        imageView.setImageResource(R.mipmap.datak_item_image)
    }

    override fun getItemLayoutRes() = R.layout.datak_item_image

    override fun getSpanSize() = spanCount
}