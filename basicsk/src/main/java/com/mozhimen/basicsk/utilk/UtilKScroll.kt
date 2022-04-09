package com.mozhimen.basicsk.utilk

import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView

/**
 * @ClassName UtilKScroll
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/22 22:30
 * @Version 1.0
 */
object UtilKScroll {
    /**
     * 判断child是否发生了滚动
     * @param child
     * @return
     */
    fun childScrolled(child: View): Boolean {
        if (child is AdapterView<*>) {
            if (child.firstVisiblePosition != 0 || child.firstVisiblePosition == 0 && child.getChildAt(0) != null && child.getChildAt(0).top < 0) {
                return true
            }
        } else if (child.scrollY > 0) {
            return true
        }
        if (child is RecyclerView) {
            val view = child.getChildAt(0)
            val firstPosition = child.getChildAdapterPosition(view)
            return firstPosition != 0 || view.top != 0
        }
        return false
    }

    /**
     * 查找可以滚动的child
     * @param viewGroup
     * @return
     */
    fun findScrollableChild(viewGroup: ViewGroup): View? {
        var child = viewGroup.getChildAt(1)
        if (child is RecyclerView || child is AdapterView<*>) {
            return child
        }
        if (child is ViewGroup) { //往下多找一层
            val tempChild = child.getChildAt(0)
            if (tempChild is RecyclerView || tempChild is AdapterView<*>) {
                child = tempChild
            }
        }
        return child
    }
}