package com.mozhimen.uicorektest.recyclerk.mos

import android.widget.ImageView
import com.mozhimen.uicorek.recyclerk.RecyclerKItem
import com.mozhimen.uicorek.vhk.VHKRecycler
import com.mozhimen.uicorektest.R

/**
 * @ClassName DataKItemActivity
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/2 14:44
 * @Version 1.0
 */
class RecyclerKItemActivity :
    RecyclerKItem<Any, VHKRecycler>() {

    override fun onBindData(holder: VHKRecycler, position: Int) {
        super.onBindData(holder, position)
        holder.findViewById<ImageView>(R.id.datak_item_activity_img)?.setImageResource(R.mipmap.datak_item_activity)
    }

    override fun getItemLayoutRes() = R.layout.item_recyclerk_activity
}
