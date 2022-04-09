package com.mozhimen.app.uicorek.datak

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.app.R
import com.mozhimen.uicorek.datak.DataK
import com.mozhimen.uicorek.datak.commons.DataKItem

/**
 * @ClassName DataKItemGrid
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/1 14:54
 * @Version 1.0
 */
class DataKItemGrid(data: DataK) : DataKItem<DataK, DataKItemGrid.GridViewHolder>(data) {
    override fun onBindData(holder: GridViewHolder, position: Int) {
        holder.imageView.setImageResource(R.mipmap.datak_item_grid)
    }

    override fun getItemLayoutRes() = R.layout.datak_item_grid

    class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.datak_item_grid_img)
    }
}