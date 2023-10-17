package com.mozhimen.uicorek.recyclerk.manager

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

/**
 * @ClassName RecyclerKCatchGridLayoutManager
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/17 17:51
 * @Version 1.0
 */
class RecyclerKCatchGridLayoutManager : GridLayoutManager {
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    constructor(context: Context, spanCount: Int) : super(context, spanCount)

    constructor(context: Context, spanCount: Int, orientation: Int, reverseLayout: Boolean) : super(context, spanCount, orientation, reverseLayout)

    override fun onLayoutChildren(recycler: Recycler?, state: RecyclerView.State?) {
        try {
            //try catch一下
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }
    }
}