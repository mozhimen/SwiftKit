package com.mozhimen.uicorektest.recyclerk.mos

import android.view.View
import android.widget.ImageView
import com.mozhimen.uicorek.recyclerk.item.RecyclerKItem
import com.mozhimen.uicorek.vhk.VHKRecycler
import com.mozhimen.uicorektest.R

/**
 * @ClassName DataKItemGrid
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/1 14:54
 * @Version 1.0
 */
class RecyclerKItemGrid : RecyclerKItem<RecyclerKItemGrid.MyVHKRecycler>() {
    override fun onBindItem(holder: MyVHKRecycler, position: Int) {
        super.onBindItem(holder, position)
        holder.imageView.setImageResource(R.mipmap.datak_item_grid)
    }

    override fun getItemLayoutId() = R.layout.item_recyclerk_grid

    class MyVHKRecycler(view: View) : VHKRecycler(view) {
        val imageView: ImageView = view.findViewById(R.id.datak_item_grid_img)
    }

    override fun getItemSpanSize(): Int {
        return 2
    }
}
