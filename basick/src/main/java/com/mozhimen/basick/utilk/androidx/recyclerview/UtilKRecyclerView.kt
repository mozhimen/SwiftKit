package com.mozhimen.basick.utilk.androidx.recyclerview

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mozhimen.basick.utilk.commons.IUtilK

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

fun LayoutManager.getLastVisibleItemPosition(): Int =
    UtilKRecyclerView.getLastVisibleItemPosition(this)

fun LayoutManager.getFirstVisibleItemPosition(): Int =
    UtilKRecyclerView.getFirstVisibleItemPosition(this)

///////////////////////////////////////////////////////////////////////////////////

fun RecyclerView.requireLayoutManager_ofLinear(): LinearLayoutManager? =
    UtilKRecyclerView.requireLayoutManager_ofLinear(this)

fun RecyclerView.requireLayoutManager_ofGrid(): GridLayoutManager? =
    UtilKRecyclerView.requireLayoutManager_ofGrid(this)

///////////////////////////////////////////////////////////////////////////////////


object UtilKRecyclerView : IUtilK {

    @JvmStatic
    fun getChildAt(recyclerView: RecyclerView, index: Int): View? =
        recyclerView.getChildAt(index)

    /**
     * 获取 spanCount
     * 注：此方法只针对设置 LayoutManager 为 GridLayoutManager 的 RecyclerView 生效
     */
    @JvmStatic
    fun getSpanCount(recyclerView: RecyclerView): Int =
        requireLayoutManager_ofGrid(recyclerView)?.spanCount ?: 0

    //获取item数
    @JvmStatic
    fun getItemCount(recyclerView: RecyclerView): Int =
        recyclerView.layoutManager?.itemCount ?: 0

    //找到最后一个可视的Item
    @JvmStatic
    fun getLastVisibleItemPosition(recyclerView: RecyclerView): Int =
        recyclerView.layoutManager?.let { getLastVisibleItemPosition(it) } ?: -1

    //找到最后一个可视的Item
    @JvmStatic
    fun getLastVisibleItemPosition(layoutManager: LayoutManager): Int {
        when (layoutManager) {
            //layoutManager is GridLayoutManager
            is LinearLayoutManager -> return layoutManager.findLastVisibleItemPosition()
            is StaggeredGridLayoutManager -> {
//                return layoutManager.findLastVisibleItemPositions(null)[0]
                val lastItemPoss = IntArray(layoutManager.spanCount)
                layoutManager.findLastVisibleItemPositions(lastItemPoss)
                return lastItemPoss.maxOf { it }
            }
        }
        return -1
    }

    //找到第一个可视的View
    @JvmStatic
    fun getFirstVisibleItemPosition(recyclerView: RecyclerView): Int =
        recyclerView.layoutManager?.let { getFirstVisibleItemPosition(it) } ?: -1

    //找到第一个可视的View
    @JvmStatic
    fun getFirstVisibleItemPosition(layoutManager: LayoutManager): Int {
        when (layoutManager) {
            //layoutManager is GridLayoutManager
            is LinearLayoutManager -> return layoutManager.findFirstVisibleItemPosition()
            is StaggeredGridLayoutManager -> return layoutManager.findFirstVisibleItemPositions(null)[0]
        }
        return -1
    }

    ///////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun canScrollVertically(recyclerView: RecyclerView, direction: Int): Boolean =
        recyclerView.canScrollVertically(direction)

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun requireLayoutManager_ofLinear(recyclerView: RecyclerView): LinearLayoutManager? {
        val layoutManager = recyclerView.layoutManager ?: return null
        if (layoutManager !is LinearLayoutManager)
            throw IllegalStateException("Make sure you are using the LinearLayoutManager")
        return layoutManager
    }

    /**
     * 检查 RecyclerView 设置的 GridLayoutManager
     */
    @JvmStatic
    fun requireLayoutManager_ofGrid(recyclerView: RecyclerView): GridLayoutManager? {
        val layoutManager = recyclerView.layoutManager ?: return null
        if (layoutManager !is GridLayoutManager) {
            throw IllegalStateException("Make sure you are using the GridLayoutManager")
        }
        return layoutManager
    }
}