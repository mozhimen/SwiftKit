package com.mozhimen.basick.extsk.view

import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.utilk.view.UtilKViewRecycler

/**
 * @ClassName ExtsKViewRecycler
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 15:02
 * @Version 1.0
 */
/**
 * 是否滑动到底部
 * @receiver recyclerView RecyclerView
 * @return Boolean
 */
fun RecyclerView.isScroll2Top(): Boolean =
    UtilKViewRecycler.isScroll2Top(this)

/**
 * 是否滑动到底部
 * @receiver recyclerView RecyclerView
 * @return Boolean
 */
fun RecyclerView.isScroll2End(): Boolean =
    UtilKViewRecycler.isScroll2End(this)

/**
 * 滑动到顶部2
 * @receiver recyclerView RecyclerView
 * @return Boolean
 */
fun RecyclerView.isScroll2Top2(): Boolean =
    UtilKViewRecycler.isScroll2Top2(this)

/**
 * 滑动到底部2
 * @receiver recyclerView RecyclerView
 * @return Boolean
 */
fun RecyclerView.isScroll2End2(): Boolean =
    UtilKViewRecycler.isScroll2End2(this)

/**
 * 是否滑动到边缘
 * @receiver recyclerView RecyclerView
 * @return Boolean
 */
fun RecyclerView.isScroll2VerticalEdge(): Boolean =
    UtilKViewRecycler.isScroll2VerticalEdge(this)

/**
 * 是否滑动到边缘2
 * @receiver recyclerView RecyclerView
 * @return Boolean
 */
fun RecyclerView.isScroll2VerticalEdge2(): Boolean =
    UtilKViewRecycler.isScroll2VerticalEdge2(this)

/**
 * 是否向上滚动
 * @receiver dy Int
 * @return Boolean
 */
fun RecyclerView.isScrollUp(dy: Int): Boolean =
    UtilKViewRecycler.isScrollUp(dy)

/**
 * 是否向下滚动
 * @receiver dx Int
 * @return Boolean
 */
fun RecyclerView.isScrollDown(dx: Int): Boolean =
    UtilKViewRecycler.isScrollDown(dx)

/**
 * 找到最后一个可视的Item
 * @receiver recyclerView RecyclerView
 * @return Int
 */
fun RecyclerView.findLastVisibleItem(): Int =
    UtilKViewRecycler.findLastVisibleItem(this)

/**
 * 找到第一个可视的View
 * @receiver recyclerView RecyclerView
 * @return Int
 */
fun RecyclerView.findFirstVisibleItem(): Int =
    UtilKViewRecycler.findFirstVisibleItem(this)