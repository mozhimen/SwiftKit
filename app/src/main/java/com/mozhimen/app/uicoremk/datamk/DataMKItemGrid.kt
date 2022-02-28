package com.mozhimen.app.uicoremk.datamk

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.app.R
import com.mozhimen.uicoremk.datamk.DataMK
import com.mozhimen.uicoremk.datamk.commons.DataMKItem

/**
 * @ClassName DataMKItemGrid
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/1 14:54
 * @Version 1.0
 */
class DataMKItemGrid(data: DataMK) : DataMKItem<DataMK, DataMKItemGrid.GridViewHolder>(data) {
    override fun onBindData(holder: GridViewHolder, position: Int) {
        holder.imageView.setImageResource(R.mipmap.datamk_item_grid)
    }

    override fun getItemLayoutRes() = R.layout.datamk_item_grid

    class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.datamk_item_grid_img)
    }
}