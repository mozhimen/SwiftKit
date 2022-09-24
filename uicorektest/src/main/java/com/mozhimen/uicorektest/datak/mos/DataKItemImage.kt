package com.mozhimen.uicorektest.datak.mos

import android.widget.ImageView
import com.mozhimen.uicorek.datak.commons.DataKItem
import com.mozhimen.uicorek.datak.helpers.DataKViewHolder
import com.mozhimen.uicorektest.R

/**
 * @ClassName DataKItemImage
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/2 15:33
 * @Version 1.0
 */
class DataKItemImage(private var spanCount: Int) : DataKItem<Any, DataKViewHolder>() {

    override fun onBindData(holder: DataKViewHolder, position: Int) {
        holder.findViewById<ImageView>(R.id.datak_item_image_img)?.setImageResource(R.mipmap.datak_item_image)
    }

    override fun getItemLayoutRes() = R.layout.item_datak_image

    override fun getSpanSize() = spanCount
}
