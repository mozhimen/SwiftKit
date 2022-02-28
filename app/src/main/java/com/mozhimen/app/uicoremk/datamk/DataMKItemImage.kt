package com.mozhimen.app.uicoremk.datamk

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.app.R
import com.mozhimen.uicoremk.datamk.DataMK
import com.mozhimen.uicoremk.datamk.commons.DataMKItem

/**
 * @ClassName DataMKItemImage
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/2 15:33
 * @Version 1.0
 */
class DataMKItemImage(var spanCount: Int, data: DataMK) :
    DataMKItem<DataMK, RecyclerView.ViewHolder>(data) {

    override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.datamk_item_image_img)
        imageView.setImageResource(R.mipmap.datamk_item_image)
    }

    override fun getItemLayoutRes() = R.layout.datamk_item_image

    override fun getSpanSize() = spanCount
}