package com.mozhimen.app.uicorek.datak.mos

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.app.R
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.fitImage
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.datak.commons.DataKItem

/**
 * @ClassName DataKItemTop
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/21 23:29
 * @Version 1.0
 */
class DataKItemTop : DataKItem<Any, RecyclerView.ViewHolder>() {
    private var parentWidth: Int = 0

    override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
        val imageView = holder.itemView as ImageView
        /*data?.let {
            if (data!!.title != null && !TextUtils.isEmpty(data!!.title!!)) {*/
        imageView.fitImage(UtilKRes.getDrawable(R.mipmap.datak_item_top)!!)
        imageView.setImageResource(R.mipmap.datak_item_top)
    }

    override fun getItemView(parent: ViewGroup): View? {
        val imageView = ImageView(parent.context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setBackgroundColor(UtilKRes.getColor(android.R.color.white))
        return imageView
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        //提前给imageview 预设一个高度值  等于parent的宽度
        parentWidth = (holder.itemView.parent as ViewGroup).measuredWidth
        val params = holder.itemView.layoutParams
        if (params.width != parentWidth) {
            params.width = parentWidth
            params.height = 40.dp2px()
            holder.itemView.layoutParams = params
        }
    }
}

