package com.mozhimen.basick.utilk.view.bar

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.util.Log
import android.view.Display
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.content.activity.UtilKActivity
import com.mozhimen.basick.utilk.content.activity.UtilKActivity.getActivityByContext
import com.mozhimen.basick.utilk.content.activity.UtilKActivity.isDestroyed
import com.mozhimen.basick.utilk.res.UtilKRes
import com.mozhimen.basick.utilk.view.window.UtilKWindow
import com.mozhimen.basick.utilk.view.window.UtilKWindowManager
import java.util.*

/**
 * @ClassName UtilKNavBar
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/23 23:37
 * @Version 1.0
 */
object UtilKNavigationBar {
    private val TAG = "UtilKNavBar>>>>>"
    private val NAVIGATION_BAR_NAMES: HashMap<String, Void?> by lazy { hashMapOf(
        "navigationbarbackground" to null,
        "immersion_navigation_bar_view" to null
    ) }


    @JvmStatic
    fun appendNavigationBarID(id: String) {
        NAVIGATION_BAR_NAMES[id] = null
    }

    /**
     * 方法参考
     * https://juejin.im/post/5bb5c4e75188255c72285b54
     */
    @JvmStatic
    fun getNavigationBarBounds(rect: Rect, context: Context) {
        val activity = getActivityByContext(context, true)
        if (activity == null || isDestroyed(activity)) return
        val decorView = UtilKWindow.getDecorView(activity) as ViewGroup
        val childCount = decorView.childCount
        for (i in childCount - 1 downTo 0) {
            val child = decorView.getChildAt(i)
            if (child.id == View.NO_ID || !child.isShown) continue
            try {
                val resourceEntryName = UtilKRes.getAppResources().getResourceEntryName(child.id)
                if (NAVIGATION_BAR_NAMES.containsKey(resourceEntryName.lowercase(Locale.getDefault()))) {
                    rect[child.left, child.top, child.right] = child.bottom
                    return
                }
            } catch (e: Exception) {
                //do nothing
            }
        }
    }

    /**
     * 获取导航栏Gravity
     * @param navigationBarBounds Rect
     * @return Int
     */
    @JvmStatic
    fun getNavigationBarGravity(navigationBarBounds: Rect): Int {
        if (navigationBarBounds.isEmpty) return Gravity.NO_GRAVITY
        return if (navigationBarBounds.left <= 0) {
            if (navigationBarBounds.top <= 0) {
                if (navigationBarBounds.width() > navigationBarBounds.height()) Gravity.TOP else Gravity.LEFT
            } else Gravity.BOTTOM
        } else Gravity.RIGHT
    }

    /**
     * Return the navigation bar's height.
     * @return Int
     */
    @JvmStatic
    fun getNavigationBarHeight(): Int {
        val dimensionId = UtilKRes.getSystemResources().getIdentifier("navigation_bar_height", "dimen", "android")
        return if (dimensionId != 0) {
            UtilKRes.getDimensionPixelSize(dimensionId, UtilKRes.getSystemResources())
        } else 0
    }

    /**
     * 获得View所在界面 NavigationBar 高度
     * @param view View 目标View
     * @return Int 如果存在NavigationBar则返回高度，否则0
     */
    @JvmStatic
    fun getNavigationBarHeight(view: View): Int {
        val activity: Activity? = UtilKActivity.getActivityByView(view)
        if (activity != null) {
            val display = UtilKWindowManager.getDefaultDisplay(activity)
            val size = Point()
            display.getSize(size)
            val usableHeight = size.y
            if (Build.VERSION.SDK_INT >= CVersionCode.V_17_42_J1) {
                display.getRealSize(size) // getRealMetrics is only available with API 17 and +
            } else {
                try {
                    size.x = (Display::class.java.getMethod("getRawWidth").invoke(display) as Int)
                    size.y = (Display::class.java.getMethod("getRawHeight").invoke(display) as Int)
                } catch (e: Exception) {
                    Log.e(TAG, "getNavBarHeight: error", e)
                }
            }
            val realHeight = size.y
            return if (realHeight > usableHeight) realHeight - usableHeight else 0
        }
        return 0
    }
}