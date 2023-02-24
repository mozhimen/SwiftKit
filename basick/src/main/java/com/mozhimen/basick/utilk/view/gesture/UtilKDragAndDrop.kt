package com.mozhimen.basick.utilk.view.gesture

import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.mozhimen.basick.utilk.java.UtilKReflect
import com.mozhimen.basick.utilk.exts.et
import java.lang.reflect.Field

/**
 * @ClassName UtilKDragAndDrop
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/19 17:10
 * @Version 1.0
 */
object UtilKDragAndDrop {
    private const val TAG = "UtilKDragAndDrop>>>>>"

    @JvmStatic
    @Throws(Exception::class)
    fun fixDragAndDropLeak(vararg views: View) {
        var tempView: View
        views.forEach { v ->
            tempView = v
            while (tempView.parent != null && tempView.parent is ViewGroup) {
                tempView = tempView.parent as ViewGroup
                fixDragAndDropLeak(tempView)
            }
        }
    }

    @JvmStatic
    @Throws(Exception::class)
    fun fixDragAndDropLeak(view: View) {
        val field: Field
        val fieldObj: Any?
        try {
            field = UtilKReflect.getField(view, "mCurrentDragChild")
            if (!field.isAccessible) field.isAccessible = true
            fieldObj = field.get(view)
            if (fieldObj != null) {
                field.set(view, null)
                Log.d(TAG, "fixDragAndDropLeak: set viewGroup ${view.javaClass.simpleName} mCurrentDragChild null")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
    }
}