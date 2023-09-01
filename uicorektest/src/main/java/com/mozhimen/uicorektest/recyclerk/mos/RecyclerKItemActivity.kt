package com.mozhimen.uicorektest.recyclerk.mos

import android.widget.ImageView
import com.mozhimen.uicorek.recyclerk.bases.BaseRecyclerKItem
import com.mozhimen.uicorek.vhk.VHKRecycler
import com.mozhimen.uicorektest.R

/**
 * @ClassName DataKItemActivity
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/2 14:44
 * @Version 1.0
 */
class RecyclerKItemActivity : BaseRecyclerKItem<VHKRecycler>() {

    override fun onBindItem(holder: VHKRecycler, position: Int) {
        super.onBindItem(holder, position)
        holder.findViewById<ImageView>(R.id.datak_item_activity_img)?.setImageResource(R.mipmap.datak_item_activity)
    }

    override fun getItemLayoutId() = R.layout.item_recyclerk_activity

    override fun getItemSpanSize(): Int {
        return 2
    }
}
