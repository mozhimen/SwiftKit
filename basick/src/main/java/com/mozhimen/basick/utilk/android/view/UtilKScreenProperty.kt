package com.mozhimen.basick.utilk.android.view

import android.os.Bundle
import android.util.Log
import android.view.View
import com.mozhimen.basick.utilk.bases.BaseUtilK

/**
 * @ClassName UtilKScreenProperty
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/26 23:53
 * @Version 1.0
 */
object UtilKScreenProperty : BaseUtilK() {

    @JvmStatic
    fun getViewBundle(view: View): Bundle {
        val screenLocation = IntArray(2)
        view.getLocationOnScreen(screenLocation) //获取view在整个屏幕中的位置
        val bundle = Bundle().apply {
            putInt("propname_sreenlocation_left", screenLocation[0])
            putInt("propname_sreenlocation_top", screenLocation[1])
            putInt("propname_width", view.width)
            putInt("propname_height", view.height)
        }
        Log.w(TAG, "Left: ${screenLocation[0]} Top: ${screenLocation[1]} Width: ${view.width} Height: ${view.height}")
        return bundle
    }
}