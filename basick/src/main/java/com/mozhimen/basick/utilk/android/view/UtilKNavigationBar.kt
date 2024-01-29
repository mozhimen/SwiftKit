package com.mozhimen.basick.utilk.android.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.Rect
import android.view.Display
import android.view.View
import android.view.ViewGroup
import com.mozhimen.basick.elemk.android.view.cons.CGravity
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.elemk.android.view.cons.CView
import com.mozhimen.basick.elemk.android.view.cons.CWinMgr
import com.mozhimen.basick.lintk.optin.OptInApiUse_BaseApplication
import com.mozhimen.basick.utilk.android.app.UtilKActivity
import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.basick.utilk.android.content.UtilKResources
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.et
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
    /**
     * 方法参考
     * https://juejin.im/post/5bb5c4e75188255c72285b54
     */
    @OptInApiUse_BaseApplication
    @JvmStatic
    fun getBounds(rect: Rect, context: Context) {
        val activity = UtilKActivity.getByContext(context, true)
        if (activity == null || UtilKActivity.isDestroyed(activity)) return
        val decorView = UtilKDecorView.get(activity) as ViewGroup
        val childCount = decorView.childCount
        for (i in childCount - 1 downTo 0) {
            val child = decorView.getChildAt(i)
            if (child.id == CView.NO_ID || !child.isShown) continue
            try {
                val resourceEntryName = UtilKResources.getAppResourceEntryName(context, child.id)
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
     */
    @SuppressLint("RtlHardcoded")
    @JvmStatic
    fun getGravity(navigationBarBounds: Rect): Int {
        if (navigationBarBounds.isEmpty) return CGravity.NO_GRAVITY
        return if (navigationBarBounds.left <= 0) {
            if (navigationBarBounds.top <= 0)
                if (navigationBarBounds.width() > navigationBarBounds.height()) CGravity.TOP else CGravity.LEFT
            else CGravity.BOTTOM
        } else CGravity.RIGHT
    }

    /**
     * Return the navigation bar's height.
     * @return Int
     */
    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    @JvmStatic
    fun getHeight(): Int {
        val dimensionId = UtilKResources.getSysIdentifier("navigation_bar_height", "dimen", "android")
        return if (dimensionId != 0) UtilKRes.getDimensionPixelSize(dimensionId) else 0
    }

    /**
     * 获得View所在界面 NavigationBar 高度
     * @param view View 目标View
     * @return Int 如果存在NavigationBar则返回高度，否则0
     */
    @OptInApiUse_BaseApplication
    @JvmStatic
    fun getHeight(view: View): Int {
        val activity: Activity? = UtilKActivity.getByView(view)
        if (activity != null) {
            val defaultDisplay = UtilKDisplay.getDefault(activity)
            val size = Point()
            UtilKDisplay.getDefaultSize(activity, size)
            val usableHeight = size.y
            if (UtilKBuildVersion.isAfterV_17_42_J1()) {
                UtilKDisplay.getDefaultRealSize(activity, size) // getRealMetrics is only available with API 17 and +
            } else {
                try {
                    size.x = (Display::class.java.getMethod("getRawWidth").invoke(defaultDisplay) as Int)
                    size.y = (Display::class.java.getMethod("getRawHeight").invoke(defaultDisplay) as Int)
                } catch (e: Exception) {
                    e.printStackTrace()
                    "getHeight: error ${e.message}".et(TAG)
                }
            }
            val realHeight = size.y
            return if (realHeight > usableHeight) realHeight - usableHeight else 0
        }
        return 0
    }

    private val _navigationBarNames: HashMap<String, Void?> by lazy {
        hashMapOf("navigationbarbackground" to null, "immersion_navigation_bar_view" to null)
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 设置状态栏沉浸式
     */
    @JvmStatic
    @ADescription("需要${CView.SystemUiFlag.LAYOUT_HIDE_NAVIGATION or CView.SystemUiFlag.LAYOUT_STABLE}")
    fun applyTranslucent(activity: Activity) {
        if (UtilKBuildVersion.isAfterV_21_5_L()) {//21//5.0以上状态栏透明
            UtilKWindow.clearFlags(activity, CWinMgr.Lpf.TRANSLUCENT_NAVIGATION)//清除透明状态栏
            //UtilKDecorView.setSystemUiVisibility(activity, CView.SystemUiFlag.LAYOUT_FULLSCREEN or CView.SystemUiFlag.LAYOUT_STABLE)
            UtilKWindow.addFlags(activity, CWinMgr.Lpf.DRAWS_SYSTEM_BAR_BACKGROUNDS)//设置状态栏颜色必须添加
            UtilKWindow.applyNavigationBarColor(activity, Color.TRANSPARENT)//设置透明
        } else if (UtilKBuildVersion.isAfterV_19_44_K()) {//19
            UtilKWindow.addFlags(activity, CWinMgr.Lpf.TRANSLUCENT_NAVIGATION)
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun hide(activity: Activity) {
        UtilKDecorView.applySystemUiVisibilityOr(activity, CView.SystemUiFlag.HIDE_NAVIGATION /*or CView.SystemUi.FLAG_LIGHT_STATUS_BAR*/)
    }

    @JvmStatic
    fun overlay(activity: Activity) {
        UtilKDecorView.applySystemUiVisibilityOr(activity, CView.SystemUiFlag.LAYOUT_HIDE_NAVIGATION)
    }

    @JvmStatic
    fun isVisible(activity: Activity): Boolean {
        val windowSystemUiVisibility = UtilKDecorView.getWindowSystemUiVisibility(activity)
        return windowSystemUiVisibility and CView.SystemUiFlag.HIDE_NAVIGATION == 0 && windowSystemUiVisibility and CView.SystemUiFlag.LAYOUT_HIDE_NAVIGATION == 0
    }

//    @JvmStatic
//    fun appendID(id: String) {
//        _navigationBarNames[id] = null
//    }
}