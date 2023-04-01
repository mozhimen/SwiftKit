package com.mozhimen.uicorektest.recyclerk.mos

import android.widget.ImageView
import com.mozhimen.uicorek.recyclerk.RecyclerKItem
import com.mozhimen.uicorek.vhk.VHKRecycler
import com.mozhimen.uicorektest.R

/**
 * @ClassName DataKItemImage
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/2 15:33
 * @Version 1.0
 */
class RecyclerKItemImage(private var spanCount: Int) : RecyclerKItem<Any, VHKRecycler>() {

    override fun onBindData(holder: VHKRecycler, position: Int) {
        super.onBindData(holder, position)
        holder.findViewById<ImageView>(R.id.datak_item_image_img)?.setImageResource(R.mipmap.datak_item_image)
    }

    override fun getItemLayoutRes() = R.layout.item_recyclerk_image

    override fun getSpanSize() = spanCount
}
