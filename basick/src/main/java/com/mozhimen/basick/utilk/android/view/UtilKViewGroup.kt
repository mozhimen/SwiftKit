package com.mozhimen.basick.utilk.android.view

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.utilk.androidx.recyclerview.isScroll2top
import com.mozhimen.basick.utilk.commons.IUtilK
import java.util.ArrayDeque
import java.util.Deque

/**
 * @ClassName UtilKScroll
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/22 22:30
 * @Version 1.0
 */
fun ViewGroup.getView_ofInflate(@LayoutRes layoutId: Int): View =
    UtilKView.get_ofInflate(this, layoutId)

fun ViewGroup.addViewMatchParent(view: View) {
    UtilKViewGroup.addViewMatchParent(this, view)
}

object UtilKViewGroup : IUtilK {

    fun getView_ofInflate(viewGroup: ViewGroup, @LayoutRes intResLayout: Int): View =
        UtilKView.get_ofInflate(viewGroup, intResLayout)

    //////////////////////////////////////////////////////////////////////////////////////

    /**
     * 查找可以滚动的child
     */
    @JvmStatic
    fun getChildView_ofScrollable(viewGroup: ViewGroup): View {
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


    /**
     * 获取指定类型的子View
     * @param viewGroup ViewGroup?
     * @param clazz Class<T>
     * @return T?
     */
    @JvmStatic
    fun <T> getChildView_ofType(viewGroup: ViewGroup, clazz: Class<T>): T? {
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
    fun getChildViews(viewGroup: ViewGroup): List<View> {
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
    fun isChildViewScrolled(viewGroup: ViewGroup): Boolean {
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

    //////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun addViewMatchParent(viewGroup: ViewGroup, view: View) {
        if (view.parent == null) {
            viewGroup.addView(view, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        } else {
            Log.e(TAG, "addViewMatchParent: ")
        }
    }
}