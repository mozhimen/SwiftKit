package com.mozhimen.uicorek.layoutk

import android.annotation.SuppressLint
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
class LayoutKRecyclerEmpty @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RecyclerView(context, attrs, defStyleAttr) {
    private val TAG = "ViewKRecyclerEmpty>>>>>"

    private var emptyView: View? = null
    private val observer: AdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            checkIfEmpty()
        }

        @SuppressLint("LongLogTag")
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            Log.d(TAG, "onItemRangeInserted $itemCount")
            checkIfEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }
    }

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
    /**
     * 作用: 设置空布局
     * 用法:  emptyView=findViewById(R.id.item_empty)
     * vb.recycler.setEmptyView(emptyView)
     */
    fun setEmptyView(emptyView: View?) {
        this.emptyView = emptyView
        checkIfEmpty()
    }
}