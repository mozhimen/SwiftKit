package com.mozhimen.basick.utilk.android.view

import android.view.View
import android.view.ViewGroup
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.lang.UtilKReflect
import com.mozhimen.basick.utilk.android.util.et

/**
 * @ClassName UtilKDragAndDrop
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/19 17:10
 * @Version 1.0
 */
object UtilKViewDrag : BaseUtilK() {

    @JvmStatic
    @Throws(Exception::class)
    fun fixDragLeak(vararg views: View) {
        var tempView: View
        views.forEach { v ->
            tempView = v
            while (tempView.parent != null && tempView.parent is ViewGroup) {
                tempView = tempView.parent as ViewGroup
                fixDragLeak(tempView)
            }
        }
    }

    @JvmStatic
    @Throws(Exception::class)
    fun fixDragLeak(view: View) {
        try {
            val fieldMCurrentDragChild = UtilKReflect.getField(view, "mCurrentDragChild")
            if (!fieldMCurrentDragChild.isAccessible)
                fieldMCurrentDragChild.isAccessible = true
            val childView = fieldMCurrentDragChild.get(view)
            if (childView != null) {
                fieldMCurrentDragChild.set(childView, null)
                "fixLeak: set viewGroup ${childView.javaClass.simpleName} mCurrentDragChild null".dt(TAG)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
    }
}