package com.mozhimen.basick.utilk.androidx.recyclerview

import android.view.View
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
fun RecyclerView.isRVScroll2top(): Boolean =
    UtilKRecyclerView.isRVScroll2top(this)

fun RecyclerView.isRVScroll2end(): Boolean =
    UtilKRecyclerView.isRVScroll2end(this)

fun RecyclerView.isRVScroll2top2(): Boolean =
    UtilKRecyclerView.isRVScroll2top2(this)

fun RecyclerView.isRVScroll2end2(): Boolean =
    UtilKRecyclerView.isRVScroll2end2(this)

fun RecyclerView.isRVScroll2VerticalEdge(): Boolean =
    UtilKRecyclerView.isRVScroll2VerticalEdge(this)

fun RecyclerView.isRVScroll2VerticalEdge2(): Boolean =
    UtilKRecyclerView.isRVScroll2VerticalEdge2(this)

fun RecyclerView.isRVScrollUp(dy: Int): Boolean =
    UtilKRecyclerView.isRVScrollUp(dy)

fun RecyclerView.isRVScrollDown(dx: Int): Boolean =
    UtilKRecyclerView.isRVScrollDown(dx)

fun RecyclerView.getRVLastVisibleItemPosition(): Int =
    UtilKRecyclerView.getRVLastVisibleItemPosition(this)

fun RecyclerView.getRVFirstVisibleItemPosition(): Int =
    UtilKRecyclerView.getRVFirstVisibleItemPosition(this)

object UtilKRecyclerView {
    /**
     * 是否滑动到底部
     * @param recyclerView RecyclerView
     * @return Boolean
     */
    @JvmStatic
    fun isRVScroll2top(recyclerView: RecyclerView): Boolean =
        !recyclerView.canScrollVertically(-1)

    /**
     * 是否滑动到底部
     * @param recyclerView RecyclerView
     * @return Boolean
     */
    @JvmStatic
    fun isRVScroll2end(recyclerView: RecyclerView): Boolean =
        !recyclerView.canScrollVertically(1)

    /**
     * 滑动到顶部2
     * @param recyclerView RecyclerView
     * @return Boolean
     */
    @JvmStatic
    fun isRVScroll2top2(recyclerView: RecyclerView): Boolean {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstItemPos = linearLayoutManager.findFirstVisibleItemPosition()
            val lastItemPos = linearLayoutManager.findLastVisibleItemPosition()
            val itemCount = linearLayoutManager.itemCount
            val lastChild: View = recyclerView.getChildAt(lastItemPos - firstItemPos)
            if (lastItemPos == itemCount - 1 && lastChild.bottom <= recyclerView.measuredHeight) {
                return true
            }
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
            if (lastPosition == itemCount - 1 && lastChild != null && lastChild.bottom <= recyclerView.getMeasuredHeight()) {
                return true
            }
        }
        return false
    }

    /**
     * 滑动到底部2
     * @param recyclerView RecyclerView
     * @return Boolean
     */
    @JvmStatic
    fun isRVScroll2end2(recyclerView: RecyclerView): Boolean {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstItem = linearLayoutManager.findFirstVisibleItemPosition()
            return recyclerView.getChildAt(0).y == 0f && firstItem == 0
        } else if (recyclerView.layoutManager is StaggeredGridLayoutManager) {
            val aa = (recyclerView.layoutManager as StaggeredGridLayoutManager).findFirstVisibleItemPositions(null)
            return recyclerView.getChildAt(0).y == 0f && aa[0] == 0
        }
        return false
    }

    /**
     * 是否滑动到边缘
     * @param recyclerView RecyclerView
     * @return Boolean
     */
    @JvmStatic
    fun isRVScroll2VerticalEdge(recyclerView: RecyclerView): Boolean =
        isRVScroll2end(recyclerView) || isRVScroll2top(recyclerView)

    /**
     * 是否滑动到边缘2
     * @param recyclerView RecyclerView
     * @return Boolean
     */
    @JvmStatic
    fun isRVScroll2VerticalEdge2(recyclerView: RecyclerView): Boolean =
        isRVScroll2end2(recyclerView) || isRVScroll2top2(recyclerView)

    /**
     * 是否向上滚动
     * @param dy Int
     * @return Boolean
     */
    @JvmStatic
    fun isRVScrollUp(dy: Int): Boolean =
        dy < 0

    /**
     * 是否向下滚动
     * @param dx Int
     * @return Boolean
     */
    @JvmStatic
    fun isRVScrollDown(dx: Int): Boolean =
        dx > 0

    /**
     * 找到最后一个可视的Item
     * @param recyclerView RecyclerView
     * @return Int
     */
    @JvmStatic
    fun getRVLastVisibleItemPosition(recyclerView: RecyclerView): Int {
        when (val layoutManager = recyclerView.layoutManager) {
            //layoutManager is GridLayoutManager
            is LinearLayoutManager -> {
                return layoutManager.findLastVisibleItemPosition()
            }
            is StaggeredGridLayoutManager -> {
                return layoutManager.findLastVisibleItemPositions(null)[0]
            }
        }
        return -1
    }

    /**
     * 找到第一个可视的View
     * @param recyclerView RecyclerView
     * @return Int
     */
    @JvmStatic
    fun getRVFirstVisibleItemPosition(recyclerView: RecyclerView): Int {
        when (val layoutManager = recyclerView.layoutManager) {
            //layoutManager is GridLayoutManager
            is LinearLayoutManager -> {
                return layoutManager.findFirstVisibleItemPosition()
            }
            is StaggeredGridLayoutManager -> {
                return layoutManager.findFirstVisibleItemPositions(null)[0]
            }
        }
        return -1
    }
}