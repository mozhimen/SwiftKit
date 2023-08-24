package com.mozhimen.basick.utilk.android.view

import android.view.View
import android.widget.GridView
import androidx.core.view.children


/**
 * @ClassName UtilKGridView
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/24 17:05
 * @Version 1.0
 */
fun GridView.applyItemSelect(position: Int, selectBlock: View.() -> Unit, otherBlock: View.() -> Unit) {
    UtilKGridView.applyItemSelect(this, position, selectBlock, otherBlock)
}

fun GridView.applyItemClick(position: Int) {
    UtilKGridView.applyItemClick(this, position)
}

object UtilKGridView {
    @JvmStatic
    fun applyItemSelect(gridView: GridView, position: Int, selectBlock: View.() -> Unit, otherBlock: View.() -> Unit) {
        gridView.children.forEachIndexed { index, view ->
            if (index == position)
                view.selectBlock()
            else
                view.otherBlock()
        }
    }

    @JvmStatic
    fun applyItemClick(gridView: GridView, position: Int) {
        gridView.performItemClick(gridView.getChildAt(position), position, gridView.getItemIdAtPosition(position))
    }
}