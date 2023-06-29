package com.mozhimen.basick.utilk.android.view

import android.annotation.SuppressLint
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
import com.mozhimen.basick.elemk.cons.CView
import com.mozhimen.basick.utilk.android.app.UtilKActivity
import com.mozhimen.basick.utilk.android.app.UtilKActivity.getActivityByContext
import com.mozhimen.basick.utilk.android.app.UtilKActivity.isDestroyed
import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.basick.utilk.android.content.UtilKResource
import com.mozhimen.basick.utilk.bases.BaseUtilK
import java.util.*

/**
 * @ClassName UtilKNavBar
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/23 23:37
 * @Version 1.0
 */
object UtilKNavigationBar : BaseUtilK() {
    private val _navigationBarNames: HashMap<String, Void?> by lazy {
        hashMapOf("navigationbarbackground" to null, "immersion_navigation_bar_view" to null)
    }

    @JvmStatic
    fun hide(activity: Activity) {
        UtilKDecorView.setSystemUiVisibilityOr(activity, CView.SystemUi.FLAG_HIDE_NAVIGATION /*or CView.SystemUi.FLAG_LIGHT_STATUS_BAR*/)
    }

    @JvmStatic
    fun isVisible(activity: Activity): Boolean {
        val windowSystemUiVisibility = UtilKDecorView.getWindowSystemUiVisibility(activity)
        return (windowSystemUiVisibility and CView.SystemUi.FLAG_HIDE_NAVIGATION == 0 &&
                windowSystemUiVisibility and CView.SystemUi.FLAG_LAYOUT_HIDE_NAVIGATION == 0)
    }

    @JvmStatic
    fun appendID(id: String) {
        _navigationBarNames[id] = null
    }

    /**
     * 方法参考
     * https://juejin.im/post/5bb5c4e75188255c72285b54
     */
    @JvmStatic
    fun getBounds(rect: Rect, context: Context) {
        val activity = getActivityByContext(context, true)
        if (activity == null || isDestroyed(activity)) return
        val decorView = UtilKDecorView.get(activity) as ViewGroup
        val childCount = decorView.childCount
        for (i in childCount - 1 downTo 0) {
            val child = decorView.getChildAt(i)
            if (child.id == View.NO_ID || !child.isShown) continue
            try {
                val resourceEntryName = UtilKResource.getResourceEntryName(child.id, context)
                if (_navigationBarNames.containsKey(resourceEntryName.lowercase(Locale.getDefault()))) {
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
    @SuppressLint("RtlHardcoded")
    @JvmStatic
    fun getGravity(navigationBarBounds: Rect): Int {
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
    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    @JvmStatic
    fun getHeight(): Int {
        val dimensionId = UtilKResource.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (dimensionId != 0) UtilKRes.getDimensionPixelSize(dimensionId) else 0
    }

    /**
     * 获得View所在界面 NavigationBar 高度
     * @param view View 目标View
     * @return Int 如果存在NavigationBar则返回高度，否则0
     */
    @JvmStatic
    fun getHeight(view: View): Int {
        val activity: Activity? = UtilKActivity.getActivityByView(view)
        if (activity != null) {
            val display = UtilKWindowManager.getDefaultDisplay(activity)
            val size = Point()
            UtilKWindowManager.getSize(activity, size)
            val usableHeight = size.y
            if (Build.VERSION.SDK_INT >= CVersionCode.V_17_42_J1) {
                UtilKWindowManager.getRealSize(activity, size) // getRealMetrics is only available with API 17 and +
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