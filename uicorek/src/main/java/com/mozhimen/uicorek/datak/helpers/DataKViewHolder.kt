package com.mozhimen.uicorek.datak.helpers

import android.util.SparseArray
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class DataKViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val containerView: View
        get() = view

    private var _viewCache = SparseArray<View>()

    /**
     * 根据资源ID找到View
     * @param viewId Int
     * @return T?
     */
    fun <T : View> findViewById(viewId: Int): T? {
        var view = _viewCache.get(viewId)
        if (view == null) {
            view = itemView.findViewById<T>(viewId)
            _viewCache.put(viewId, view)
        }
        return view as? T
    }
}