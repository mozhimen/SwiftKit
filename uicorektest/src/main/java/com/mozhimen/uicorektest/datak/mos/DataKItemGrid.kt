package com.mozhimen.uicorektest.datak.mos

import android.view.View
import android.widget.ImageView
import com.mozhimen.uicorek.datak.commons.DataKItem
import com.mozhimen.uicorek.datak.helpers.DataKViewHolder
import com.mozhimen.uicorektest.R

/**
 * @ClassName DataKItemGrid
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/1 14:54
 * @Version 1.0
 */
class DataKItemGrid : DataKItem<Any, DataKItemGrid.MyViewHolder>() {
    override fun onBindData(holder: MyViewHolder, position: Int) {
        holder.imageView.setImageResource(R.mipmap.datak_item_grid)
    }

    override fun getItemLayoutRes() = R.layout.item_datak_grid

    class MyViewHolder(view: View) : DataKViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.datak_item_grid_img)
    }
}
