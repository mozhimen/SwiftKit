package com.mozhimen.app.uicoremk.datamk

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.app.R
import com.mozhimen.uicoremk.datamk.DataMK
import com.mozhimen.uicoremk.datamk.commons.DataMKItem

/**
 * @ClassName DataMKItemActivity
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/2 14:44
 * @Version 1.0
 */
class DataMKItemActivity(data: DataMK?) :
    DataMKItem<DataMK, DataMKItemActivity.ActivityViewHolder>(data) {

    override fun onBindData(holder: ActivityViewHolder, position: Int) {
        holder.imageView.setImageResource(R.mipmap.datamk_item_activity)
    }

    override fun getItemLayoutRes() = R.layout.datamk_item_activity

    class ActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.datamk_item_activity_img)
    }
}