package com.mozhimen.swiftmk.widget.list

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @ClassName EmptyRecyclerView
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/28 16:29
 * @Version 1.0
 */
class EmptyRecyclerViewMK : RecyclerView {
    private var emptyView: View? = null
    private val observer: AdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            checkIfEmpty()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            Log.i(TAG, "onItemRangeInserted$itemCount")
            checkIfEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }
    }

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(
        context: Context?, attrs: AttributeSet?,
        defStyle: Int,
    ) : super(context!!, attrs, defStyle)

    private fun checkIfEmpty() {
        if (emptyView != null && adapter != null) {
            val emptyViewVisible = adapter!!.itemCount == 0
            emptyView!!.visibility = if (emptyViewVisible) VISIBLE else GONE
            visibility = if (emptyViewVisible) GONE else VISIBLE
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        val oldAdapter = getAdapter()
        oldAdapter?.unregisterAdapterDataObserver(observer)
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(observer)
        checkIfEmpty()
    }

    //设置没有内容时，提示用户的空布局
    fun setEmptyView(emptyView: View?) {
        this.emptyView = emptyView
        checkIfEmpty()
    }

    companion object {
        private const val TAG = "EmptyRecyclerView"
    }
}