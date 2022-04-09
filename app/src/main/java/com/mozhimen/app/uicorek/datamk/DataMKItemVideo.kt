package com.mozhimen.app.uicorek.datak

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.app.R
import com.mozhimen.uicorek.datak.DataK
import com.mozhimen.uicorek.datak.commons.DataKItem

/**
 * @ClassName DataKItemVideo
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/2 16:21
 * @Version 1.0
 */
class DataKItemVideo(var spanCount: Int, data: DataK) :
    DataKItem<DataK, RecyclerView.ViewHolder>(data) {
    override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.datak_item_video_img)
        imageView.setImageResource(R.mipmap.datak_item_video)
    }

    override fun getItemLayoutRes() = R.layout.datak_item_video

    override fun getSpanSize() = spanCount
}