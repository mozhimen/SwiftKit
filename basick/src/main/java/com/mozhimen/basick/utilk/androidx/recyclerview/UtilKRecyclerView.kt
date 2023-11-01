package com.mozhimen.basick.utilk.androidx.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * @ClassName UtilKViewRecycler
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 0:30
 * @Version 1.0
 */
fun RecyclerView.getItemCount(): Int =
    UtilKRecyclerView.getItemCount(this)

fun RecyclerView.getSpanCount(): Int =
    UtilKRecyclerView.getSpanCount(this)

fun RecyclerView.getLastVisibleItemPosition(): Int =
    UtilKRecyclerView.getLastVisibleItemPosition(this)

fun RecyclerView.getFirstVisibleItemPosition(): Int =
    UtilKRecyclerView.getFirstVisibleItemPosition(this)

///////////////////////////////////////////////////////////////////////////////////

fun RecyclerView.isScroll2top(): Boolean =
    UtilKRecyclerView.isScroll2top(this)

fun RecyclerView.isScroll2end(): Boolean =
    UtilKRecyclerView.isScroll2end(this)

fun RecyclerView.isScroll2top2(): Boolean =
    UtilKRecyclerView.isScroll2top2(this)

fun RecyclerView.isScroll2end2(): Boolean =
    UtilKRecyclerView.isScroll2end2(this)

fun RecyclerView.isScroll2VerticalEdge(): Boolean =
    UtilKRecyclerView.isScroll2VerticalEdge(this)

fun RecyclerView.isScroll2VerticalEdge2(): Boolean =
    UtilKRecyclerView.isScroll2VerticalEdge2(this)

fun RecyclerView.isScrollUp(dy: Int): Boolean =
    UtilKRecyclerView.isScrollUp(dy)

fun RecyclerView.isScrollDown(dx: Int): Boolean =
    UtilKRecyclerView.isScrollDown(dx)

///////////////////////////////////////////////////////////////////////////////////

fun RecyclerView.requireLinearLayoutManager(): LinearLayoutManager? =
    UtilKRecyclerView.requireLinearLayoutManager(this)

fun RecyclerView.requireGridLayoutManager(): GridLayoutManager? =
    UtilKRecyclerView.requireGridLayoutManager(this)

object UtilKRecyclerView {

    /**
     * 获取 spanCount
     * 注：此方法只针对设置 LayoutManager 为 GridLayoutManager 的 RecyclerView 生效
     */
    @JvmStatic
    fun getSpanCount(recyclerView: RecyclerView): Int {
        return requireGridLayoutManager(recyclerView)?.spanCount ?: 0
    }

    /**
     * 获取item数
     */
    @JvmStatic
    fun getItemCount(recyclerView: RecyclerView): Int =
        recyclerView.layoutManager?.itemCount ?: 0

    /**
     * 找到最后一个可视的Item
     */
    @JvmStatic
    fun getLastVisibleItemPosition(recyclerView: RecyclerView): Int {
        when (val layoutManager = recyclerView.layoutManager) {
            //layoutManager is GridLayoutManager
            is LinearLayoutManager -> return layoutManager.findLastVisibleItemPosition()
            is StaggeredGridLayoutManager -> return layoutManager.findLastVisibleItemPositions(null)[0]
        }
        return -1
    }

    /**
     * 找到第一个可视的View
     */
    @JvmStatic
    fun getFirstVisibleItemPosition(recyclerView: RecyclerView): Int {
        when (val layoutManager = recyclerView.layoutManager) {
            //layoutManager is GridLayoutManager
            is LinearLayoutManager -> return layoutManager.findFirstVisibleItemPosition()
            is StaggeredGridLayoutManager -> return layoutManager.findFirstVisibleItemPositions(null)[0]
        }
        return -1
    }

    ///////////////////////////////////////////////////////////////////////////////////

    /**
     * 是否滑动到底部
     */
    @JvmStatic
    fun isScroll2top(recyclerView: RecyclerView): Boolean =
        !recyclerView.canScrollVertically(-1)

    /**
     * 是否滑动到底部
     */
    @JvmStatic
    fun isScroll2end(recyclerView: RecyclerView): Boolean =
        !recyclerView.canScrollVertically(1)

    /**
     * 滑动到顶部2
     */
    @JvmStatic
    fun isScroll2top2(recyclerView: RecyclerView): Boolean {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstItemPos = linearLayoutManager.findFirstVisibleItemPosition()
            val lastItemPos = linearLayoutManager.findLastVisibleItemPosition()
            val itemCount = linearLayoutManager.itemCount
            val lastChild: View = recyclerView.getChildAt(lastItemPos - firstItemPos)
            if (lastItemPos == itemCount - 1 && lastChild.bottom <= recyclerView.measuredHeight)
                return true
        } else if (recyclerView.layoutManager is StaggeredGridLayoutManager) {
            val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
            val firstVisibleItems =
                (recyclerView.layoutManager as StaggeredGridLayoutManager).findFirstVisibleItemPositions(null)
            // 真实Position就是position - firstVisibleItems[0]
            val itemCount = layoutManager.itemCount
            val lastPositions = IntArray(layoutManager.spanCount)
            layoutManager.findLastVisibleItemPositions(lastPositions)
            val lastPosition: Int = lastPositions.maxOf { it }
            val lastChild: View? = recyclerView.getChildAt(lastPosition - firstVisibleItems[0])
            if (lastPosition == itemCount - 1 && lastChild != null && lastChild.bottom <= recyclerView.measuredHeight)
                return true
        }
        return false
    }

    /**
     * 滑动到底部2
     */
    @JvmStatic
    fun isScroll2end2(recyclerView: RecyclerView): Boolean {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstItem = linearLayoutManager.findFirstVisibleItemPosition()
            return recyclerView.getChildAt(0).y == 0f && firstItem == 0
        } else if (recyclerView.layoutManager is StaggeredGridLayoutManager) {
            val firstItem = (recyclerView.layoutManager as StaggeredGridLayoutManager).findFirstVisibleItemPositions(null)
            return recyclerView.getChildAt(0).y == 0f && firstItem[0] == 0
        }
        return false
    }

    /**
     * 是否滑动到边缘
     */
    @JvmStatic
    fun isScroll2VerticalEdge(recyclerView: RecyclerView): Boolean =
        isScroll2end(recyclerView) || isScroll2top(recyclerView)

    /**
     * 是否滑动到边缘2
     * @param recyclerView RecyclerView
     * @return Boolean
     */
    @JvmStatic
    fun isScroll2VerticalEdge2(recyclerView: RecyclerView): Boolean =
        isScroll2end2(recyclerView) || isScroll2top2(recyclerView)

    /**
     * 是否向上滚动
     */
    @JvmStatic
    fun isScrollUp(dy: Int): Boolean =
        dy < 0

    /**
     * 是否向下滚动
     */
    @JvmStatic
    fun isScrollDown(dx: Int): Boolean =
        dx > 0

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun requireLinearLayoutManager(recyclerView: RecyclerView): LinearLayoutManager? {
        val layoutManager = recyclerView.layoutManager ?: return null
        if (layoutManager !is LinearLayoutManager)
            throw IllegalStateException("Make sure you are using the LinearLayoutManager")
        return layoutManager
    }

    /**
     * 检查 RecyclerView 设置的 GridLayoutManager
     */
    @JvmStatic
    fun requireGridLayoutManager(recyclerView: RecyclerView): GridLayoutManager? {
        val layoutManager = recyclerView.layoutManager ?: return null
        if (layoutManager !is GridLayoutManager) {
            throw IllegalStateException("Make sure you are using the GridLayoutManager！")
        }
        return layoutManager
    }

    /**
     * {@link ItemDecoration#getItemOffsets(outRect: Rect,view: View,parent: RecyclerView)} or
     * {@link ItemDecoration#getItemOffsets(outRect: Rect,view: View,parent: RecyclerView,state: RecyclerView.State)}.
     * 均分 LinearLayoutManager 间距的便捷方法
     */
    @JvmStatic
    fun equilibriumAssignmentOfLinearLayoutManager(recyclerView: RecyclerView, itemView: View, outRect: Rect, gapOuter: Int, gapInner: Int = gapOuter / 2) {
        val itemCount = recyclerView.getItemCount()// item 的个数
        val itemPosition = recyclerView.getChildAdapterPosition(itemView)// 当前 item 的 position
        val layoutManager = recyclerView.requireLinearLayoutManager() ?: return
        val orientation = layoutManager.orientation// 获取 LinearLayoutManager 的布局方向
        for (index in 0..itemCount) {// 遍历所有 item
            when (itemPosition) {
                0 -> {// 第一行/列
                    if (orientation == RecyclerView.VERTICAL) {// 第一行/列 && VERTICAL 布局方式 -> 对item的底部特殊处理
                        outRect.top = gapOuter
                        outRect.bottom = gapInner
                        outRect.left = gapOuter
                        outRect.right = gapOuter
                    } else {// 第一行/列 && HORIZONTAL 布局方式 -> 对item的右边特殊处理
                        outRect.top = gapOuter
                        outRect.bottom = gapOuter
                        outRect.left = gapOuter
                        outRect.right = gapInner
                    }
                }

                itemCount - 1 -> {// 最后一行/列
                    if (orientation == RecyclerView.VERTICAL) {// 最后一行/列 && VERTICAL 布局方式 -> 对item的顶部特殊处理
                        outRect.top = gapInner
                        outRect.bottom = gapOuter
                        outRect.left = gapOuter
                        outRect.right = gapOuter
                    } else {// 最后一行/列 && HORIZONTAL 布局方式 -> 对item的左边特殊处理
                        outRect.top = gapOuter
                        outRect.bottom = gapOuter
                        outRect.left = gapInner
                        outRect.right = gapOuter
                    }
                }

                else -> {// 中间的行/列
                    if (orientation == RecyclerView.VERTICAL) {// 中间的行/列 && VERTICAL 布局方式 -> 对item的顶部和底部特殊处理
                        outRect.top = gapInner
                        outRect.bottom = gapInner
                        outRect.left = gapOuter
                        outRect.right = gapOuter
                    } else {// 中间的行/列 && HORIZONTAL 布局方式 -> 对item的左边和右边特殊处理
                        outRect.top = gapOuter
                        outRect.bottom = gapOuter
                        outRect.left = gapInner
                        outRect.right = gapInner
                    }
                }
            }
        }
    }

    /**
     * {@link ItemDecoration#getItemOffsets(outRect: Rect,view: View,parent: RecyclerView)} or
     * {@link ItemDecoration#getItemOffsets(outRect: Rect,view: View,parent: RecyclerView,state: RecyclerView.State)}.
     * 均分 GridLayoutManager 间距的便捷方法
     */
    @JvmStatic
    fun equilibriumAssignmentOfGridLayoutManager(recyclerView: RecyclerView, itemView: View, outRect: Rect, gapOuter: Int, gapInner: Int) {
        val itemCount = recyclerView.getItemCount()// item 的个数
        val spanCount = recyclerView.getSpanCount()// 网格布局的跨度数
        val itemPosition = recyclerView.getChildAdapterPosition(itemView)// 当前 item 的 position
        val layoutManager = recyclerView.requireGridLayoutManager() ?: return
        if (spanCount < 2) {
            equilibriumAssignmentOfLinearLayoutManager(recyclerView, itemView, outRect, gapOuter, gapInner)
            return
        }
        val orientation = layoutManager.orientation// 获取 GridLayoutManager 的布局方向
        if (orientation == RecyclerView.HORIZONTAL)
            throw UnsupportedOperationException("You can’t set a horizontal grid layout because we don’t support!")// 暂不支持横向布局的 GridLayoutManager
        for (index in 0..itemCount) {// 遍历所有 item
            when {
                itemPosition % spanCount == 0 -> {// 最左边的那一列
                    outRect.left = gapOuter
                    outRect.right = gapInner
                }

                (itemPosition - (spanCount - 1)) % spanCount == 0 -> {// 最右边的那一列
                    outRect.left = gapInner
                    outRect.right = gapOuter
                }

                else -> {// 中间的列（可能有多列）
                    outRect.left = gapInner
                    outRect.right = gapInner
                }
            }
            when (itemPosition) {
                in 0 until spanCount -> {
                    outRect.top = gapOuter
                    outRect.bottom = gapInner
                }
                in (itemCount - spanCount) until itemCount -> {// 判断是否为最后一行，最后一行单独添加底部的间距
                    outRect.bottom = gapOuter
                    outRect.top = gapInner
                }
                else -> {
                    outRect.top = gapInner
                    outRect.bottom = gapInner
                }
            }
        }
    }
}