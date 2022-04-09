package com.mozhimen.app.uicorek.datak

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.app.R
import com.mozhimen.uicorek.datak.DataK
import com.mozhimen.uicorek.datak.commons.DataKItem

/**
 * @ClassName DataKItemActivity
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/2 14:44
 * @Version 1.0
 */
class DataKItemActivity(data: DataK?) :
    DataKItem<DataK, DataKItemActivity.ActivityViewHolder>(data) {

    override fun onBindData(holder: ActivityViewHolder, position: Int) {
        holder.imageView.setImageResource(R.mipmap.datak_item_activity)
    }

    override fun getItemLayoutRes() = R.layout.datak_item_activity

    class ActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.datak_item_activity_img)
    }
}