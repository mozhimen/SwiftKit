package com.mozhimen.uicorektest.recyclerk.mos

import android.widget.ImageView
import com.mozhimen.uicorek.recyclerk.RecyclerKItem
import com.mozhimen.uicorek.vhk.VHKRecycler
import com.mozhimen.uicorektest.R

/**
 * @ClassName DataKItemVideo
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/2 16:21
 * @Version 1.0
 */
class RecyclerKItemVideo(private var _spanCount: Int) :
    RecyclerKItem<Any, VHKRecycler>() {
    override fun onBindData(holder: VHKRecycler, position: Int) {
        super.onBindData(holder, position)
        holder.findViewById<ImageView>(R.id.datak_item_video_img)?.setImageResource(R.mipmap.datak_item_video)
    }

    override fun getItemLayoutRes() = R.layout.item_recyclerk_video

    override fun getSpanSize() = _spanCount
}
