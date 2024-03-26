package com.mozhimen.basick.utilk.androidx.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper

/**
 * @ClassName UtilKRecyclerViewLayoutManager
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/26
 * @Version 1.0
 */
fun RecyclerView.isScroll2top(): Boolean =
    UtilKRecyclerViewWrapper.isScroll2top(this)

fun RecyclerView.isScroll2end(): Boolean =
    UtilKRecyclerViewWrapper.isScroll2end(this)

fun RecyclerView.isScroll2verticalEdge(): Boolean =
    UtilKRecyclerViewWrapper.isScroll2verticalEdge(this)

fun RecyclerView.isScroll2top_ofItem(): Boolean =
    UtilKRecyclerViewWrapper.isScroll2top_ofItem(this)

fun RecyclerView.isScroll2end_ofItem(): Boolean =
    UtilKRecyclerViewWrapper.isScroll2end_ofItem(this)

fun RecyclerView.isScroll2verticalEdge_ofItem(): Boolean =
    UtilKRecyclerViewWrapper.isScroll2verticalEdge_ofItem(this)

//fun RecyclerView.isScrollUp(dy: Int): Boolean =
//    UtilKRecyclerView.isScrollUp(dy)
//
//fun RecyclerView.isScrollDown(dx: Int): Boolean =
//    UtilKRecyclerView.isScrollDown(dx)

object UtilKRecyclerViewWrapper {

    //是否滑动到边缘
    @JvmStatic
    fun isScroll2verticalEdge(recyclerView: RecyclerView): Boolean =
        isScroll2end(recyclerView) || isScroll2top(recyclerView)

    //是否滑动到底部
    @JvmStatic
    fun isScroll2top(recyclerView: RecyclerView): Boolean =
        !UtilKRecyclerView.canScrollVertically(recyclerView, -1)

    //是否滑动到底部
    @JvmStatic
    fun isScroll2end(recyclerView: RecyclerView): Boolean =
        !UtilKRecyclerView.canScrollVertically(recyclerView, 1)

    //是否滑动到边缘2
    @JvmStatic
    fun isScroll2verticalEdge_ofItem(recyclerView: RecyclerView): Boolean =
        isScroll2end_ofItem(recyclerView) || isScroll2top_ofItem(recyclerView)

    //滑动到顶部2
    @JvmStatic
    fun isScroll2top_ofItem(recyclerView: RecyclerView): Boolean {
        when (val layoutManager = recyclerView.layoutManager) {
            is LinearLayoutManager, is StaggeredGridLayoutManager -> {
                val firstItemPos = layoutManager.getFirstVisibleItemPosition()
                val lastItemPos = layoutManager.getLastVisibleItemPosition()
                val lastChild: View? = UtilKRecyclerView.getChildAt(recyclerView, lastItemPos - firstItemPos)
                val itemCount = layoutManager.itemCount
                if (lastItemPos == itemCount - 1 && lastChild != null && lastChild.bottom <= recyclerView.measuredHeight)
                    return true
            }
        }
        return false
    }

    //滑动到底部2
    @JvmStatic
    fun isScroll2end_ofItem(recyclerView: RecyclerView): Boolean {
        when (val layoutManager = recyclerView.layoutManager) {
            is LinearLayoutManager, is StaggeredGridLayoutManager -> {
                return UtilKRecyclerView.getChildAt(recyclerView, 0)?.y == 0f && layoutManager.getFirstVisibleItemPosition() == 0
            }
        }
        return false
    }

//    //是否向上滚动
//    @JvmStatic
//    fun isScrollUp(dy: Int): Boolean =
//        dy < 0
//
//    //是否向下滚动
//    @JvmStatic
//    fun isScrollDown(dx: Int): Boolean =
//        dx > 0

    /**
     * {@link ItemDecoration#getItemOffsets(outRect: Rect,view: View,parent: RecyclerView)} or
     * {@link ItemDecoration#getItemOffsets(outRect: Rect,view: View,parent: RecyclerView,state: RecyclerView.State)}.
     * 均分 LinearLayoutManager 间距的便捷方法
     */
    @JvmStatic
    fun equilibriumAssignmentOfLinearLayoutManager(recyclerView: RecyclerView, itemView: View, outRect: Rect, gapOuter: Int, gapInner: Int = gapOuter / 2, gapOther: Int = gapOuter) {
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
                        outRect.left = gapOther
                        outRect.right = gapOther
                    } else {// 第一行/列 && HORIZONTAL 布局方式 -> 对item的右边特殊处理
                        outRect.top = gapOther
                        outRect.bottom = gapOther
                        outRect.left = gapOuter
                        outRect.right = gapInner
                    }
                }

                itemCount - 1 -> {// 最后一行/列
                    if (orientation == RecyclerView.VERTICAL) {// 最后一行/列 && VERTICAL 布局方式 -> 对item的顶部特殊处理
                        outRect.top = gapInner
                        outRect.bottom = gapOuter
                        outRect.left = gapOther
                        outRect.right = gapOther
                    } else {// 最后一行/列 && HORIZONTAL 布局方式 -> 对item的左边特殊处理
                        outRect.top = gapOther
                        outRect.bottom = gapOther
                        outRect.left = gapInner
                        outRect.right = gapOuter
                    }
                }

                else -> {// 中间的行/列
                    if (orientation == RecyclerView.VERTICAL) {// 中间的行/列 && VERTICAL 布局方式 -> 对item的顶部和底部特殊处理
                        outRect.top = gapInner
                        outRect.bottom = gapInner
                        outRect.left = gapOther
                        outRect.right = gapOther
                    } else {// 中间的行/列 && HORIZONTAL 布局方式 -> 对item的左边和右边特殊处理
                        outRect.top = gapOther
                        outRect.bottom = gapOther
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
    fun equilibriumAssignmentOfGridLayoutManager(recyclerView: RecyclerView, itemView: View, outRect: Rect, gapOuter: Int, gapInner: Int = gapOuter / 2, gapOther: Int = gapOuter) {
        val itemCount = recyclerView.getItemCount()// item 的个数
        val spanCount = recyclerView.getSpanCount()// 网格布局的跨度数
        val itemPosition = recyclerView.getChildAdapterPosition(itemView)// 当前 item 的 position
        val lastRowFirstPosition = itemCount - (itemCount % spanCount)
        val layoutManager = recyclerView.requireGridLayoutManager() ?: return
        if (spanCount < 2) {
            equilibriumAssignmentOfLinearLayoutManager(recyclerView, itemView, outRect, gapOuter, gapInner, gapOther)
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

                in lastRowFirstPosition until itemCount /*(itemCount - spanCount) until itemCount*/ -> {// 判断是否为最后一行，最后一行单独添加底部的间距
                    UtilKLogWrapper.d(UtilKRecyclerView.TAG, "equilibriumAssignmentOfGridLayoutManager: itemPosition $itemPosition")
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