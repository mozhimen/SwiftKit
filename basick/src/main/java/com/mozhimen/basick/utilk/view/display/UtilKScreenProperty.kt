package com.mozhimen.basick.utilk.view.display

import android.os.Bundle
import android.util.Log
import android.view.View

/**
 * @ClassName UtilKScreenProperty
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/26 23:53
 * @Version 1.0
 */
object UtilKScreenProperty {
    private const val TAG = "UtilKScreenProperty>>>>>"
    const val VIEW_INFO_EXTRA = "view_into_extra"
    const val PROPNAME_SCREENLOCATION_LEFT = "propname_sreenlocation_left"
    const val PROPNAME_SCREENLOCATION_TOP = "propname_sreenlocation_top"
    const val PROPNAME_WIDTH = "propname_width"
    const val PROPNAME_HEIGHT = "propname_height"

    @JvmStatic
    fun getViewBundle(view: View): Bundle {
        val screenLocation = IntArray(2)
        view.getLocationOnScreen(screenLocation) //获取view在整个屏幕中的位置
        val bundle = Bundle().apply {
            putInt(PROPNAME_SCREENLOCATION_LEFT, screenLocation[0])
            putInt(PROPNAME_SCREENLOCATION_TOP, screenLocation[1])
            putInt(PROPNAME_WIDTH, view.width)
            putInt(PROPNAME_HEIGHT, view.height)
        }
        Log.w(TAG, "Left: ${screenLocation[0]} Top: ${screenLocation[1]} Width: ${view.width} Height: ${view.height}")
        return bundle
    }
}