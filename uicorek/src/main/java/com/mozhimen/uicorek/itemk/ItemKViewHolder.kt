package com.mozhimen.uicorek.itemk

import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @ClassName ItemKViewHolder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/25 15:35
 * @Version 1.0
 */
open class ItemKViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val containerView: View
        get() = view

    private var viewCache = SparseArray<View>()
    fun <T : View> findViewById(viewId: Int): T? {
        var view = viewCache.get(viewId)
        if (view == null) {
            view = itemView.findViewById<T>(viewId)
            viewCache.put(viewId, view)
        }
        return view as? T
    }
}