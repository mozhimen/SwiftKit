package com.mozhimen.basick.utilk.android.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.utilk.androidx.recyclerview.isScroll2top
import java.util.ArrayDeque
import java.util.Deque

/**
 * @ClassName UtilKScroll
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/22 22:30
 * @Version 1.0
 */
fun ViewGroup.getViewOfInflate(@LayoutRes layoutId: Int): View =
    UtilKViewGroup.getViewOfInflate(this, layoutId)

object UtilKViewGroup {

    /**
     * 查找可以滚动的child
     */
    @JvmStatic
    fun getChildScrollable(viewGroup: ViewGroup): View {
        var child = viewGroup.getChildAt(1)
        if (child is RecyclerView || child is AdapterView<*>)
            return child
        if (child is ViewGroup) { //往下多找一层
            val tempChild = child.getChildAt(0)
            if (tempChild is RecyclerView || tempChild is AdapterView<*>)
                child = tempChild
        }
        return child
    }

    @JvmStatic
    fun getViewOfInflate(viewGroup: ViewGroup, @LayoutRes layoutId: Int): View =
        LayoutInflater.from(viewGroup.context).inflate(layoutId, viewGroup, false)

    /**
     * 获取指定类型的子View
     * @param viewGroup ViewGroup?
     * @param clazz Class<T>
     * @return T?
     */
    @JvmStatic
    fun <T> getChildViewForType(viewGroup: ViewGroup, clazz: Class<T>): T? {
        val viewDeque: Deque<View> = ArrayDeque()
        viewDeque.add(viewGroup)
        while (!viewDeque.isEmpty()) {
            val node = viewDeque.removeFirst()
            if (clazz.isInstance(node)) {
                return clazz.cast(node)
            } else if (node is ViewGroup) {
                var i = 0
                val count = node.childCount
                while (i < count) {
                    viewDeque.add(node.getChildAt(i))
                    i++
                }
            }
        }
        return null
    }

    @JvmStatic
    fun getAllChildViews(viewGroup: ViewGroup): List<View> {
        val viewDeque: Deque<View> = ArrayDeque()
        viewDeque.add(viewGroup)
        val views = mutableListOf<View>()
        while (!viewDeque.isEmpty()) {
            val node = viewDeque.removeFirst()
            if (node is ViewGroup) {
                var i = 0
                val count = node.childCount
                while (i < count) {
                    viewDeque.add(node.getChildAt(i))
                    i++
                }
            } else views.add(node)
        }
        return views
    }

    //////////////////////////////////////////////////////////////////////////////////////

    /**
     * 判断child是否发生了滚动
     */
    @JvmStatic
    fun isChildScrolled(viewGroup: ViewGroup): Boolean {
        if (viewGroup is AdapterView<*>) {
            if (viewGroup.firstVisiblePosition != 0 || viewGroup.firstVisiblePosition == 0 && viewGroup.getChildAt(0) != null && viewGroup.getChildAt(0).top < 0)
                return true
        } else if (viewGroup.scrollY > 0)
            return true
        if (viewGroup is RecyclerView) {
            val firstPosition = viewGroup.getChildAdapterPosition(viewGroup.getChildAt(0))
            return firstPosition != 0 || !viewGroup.isScroll2top()
        }
        return false
    }
}