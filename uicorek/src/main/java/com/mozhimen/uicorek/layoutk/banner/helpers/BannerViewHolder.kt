package com.mozhimen.uicorek.layoutk.banner.helpers

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import com.mozhimen.basick.elemk.kotlin.cons.CSuppress

/**
 * @ClassName BannerViewHolder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/7 2:26
 * @Version 1.0
 */
class BannerViewHolder(var rootView: View) {
    private var _viewSparseArray: SparseArray<View>? = null

    @Suppress(CSuppress.UNCHECKED_CAST)
    fun <V : View> findViewById(id: Int): V {
        if (rootView !is ViewGroup) {
            return rootView as V
        }
        if (_viewSparseArray == null) {
            _viewSparseArray = SparseArray(1)
        }
        var childView = _viewSparseArray!![id] as V?
        if (childView == null) {
            childView = rootView.findViewById(id)
            _viewSparseArray!!.put(id, childView)
        }
        return childView!!
    }
}