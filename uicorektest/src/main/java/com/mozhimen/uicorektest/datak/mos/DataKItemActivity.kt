package com.mozhimen.uicorektest.datak.mos

import android.widget.ImageView
import com.mozhimen.uicorek.datak.commons.DataKItem
import com.mozhimen.uicorek.datak.helpers.DataKViewHolder
import com.mozhimen.uicorektest.R

/**
 * @ClassName DataKItemActivity
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/2 14:44
 * @Version 1.0
 */
class DataKItemActivity :
    DataKItem<Any, DataKViewHolder>() {

    override fun onBindData(holder: DataKViewHolder, position: Int) {
        holder.findViewById<ImageView>(R.id.datak_item_activity_img)?.setImageResource(R.mipmap.datak_item_activity)
    }

    override fun getItemLayoutRes() = R.layout.item_datak_activity
}
