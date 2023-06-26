package com.mozhimen.basick.utilk.android.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import androidx.annotation.ColorInt
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.elemk.cons.CView
import com.mozhimen.basick.elemk.cons.CWinMgrLP
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.app.UtilKActivity
import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.basick.elemk.view.bar.ColorfulStatusBar
import com.mozhimen.basick.utilk.android.content.UtilKResource

/**
 * @ClassName UtilKBar
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/7/17 17:29
 * @Version 1.0
 */
object UtilKStatusBar : BaseUtilK() {
    /**
     * 设置状态栏全屏
     * @param activity Activity
     */
    @JvmStatic
    fun setFullScreen(activity: Activity) {
        UtilKTitleBar.hideTitleBar(activity)
        hideStatusBar(activity)
        UtilKContentView.setFitsSystemWindows(activity)
    }

    /**
     * 设置状态栏沉浸式
     * @param activity Activity
     */
    @JvmStatic
    fun setImmersed(activity: Activity) {
        if (Build.VERSION.SDK_INT >= CVersionCode.V_21_5_L) {//21//5.0以上状态栏透明
            UtilKWindow.clearFlags(activity, CWinMgrLP.FLAG_TRANSLUCENT_STATUS)//清除透明状态栏
            UtilKDecorView.setSystemUiVisibility(activity, CView.FLAG_LAYOUT_FULLSCREEN or CView.FLAG_LAYOUT_STABLE)
            UtilKWindow.addFlags(activity, CWinMgrLP.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)//设置状态栏颜色必须添加
            UtilKWindow.setStatusBarColor(activity, Color.TRANSPARENT)//设置透明
        } else if (Build.VERSION.SDK_INT >= CVersionCode.V_19_44_K) {//19
            UtilKWindow.addFlags(activity, CWinMgrLP.FLAG_TRANSLUCENT_STATUS)
        }
    }

    /**
     * 修改状态栏颜色,支持4.4以上的版本
     */
    @JvmStatic
    fun setStatusBarColor(activity: Activity, @ColorInt colorInt: Int) {
        if (Build.VERSION.SDK_INT >= CVersionCode.V_21_5_L) {
            UtilKWindow.setStatusBarColor(activity, colorInt)
        } else if (Build.VERSION.SDK_INT >= CVersionCode.V_19_44_K) {
            //使用SystemBarTintManager,需要先将状态栏设置为透明
            setImmersed(activity)
            val colorfulStatusBar = ColorfulStatusBar(activity)
            colorfulStatusBar.setEnable(true)//显示状态栏
            colorfulStatusBar.setColor(colorInt)//显示状态栏颜色
        }
    }

    /**
     * 隐藏状态栏
     * @param activity Activity
     */
    @JvmStatic
    fun hideStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= CVersionCode.V_23_6_M) {
            UtilKDecorView.setSystemUiVisibility(activity, CView.FLAG_FULLSCREEN or CView.FLAG_LIGHT_STATUS_BAR)
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Return the status bar's height.
     * @return Int
     */
    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    @JvmStatic
    fun getStatusBarHeight(): Int {
        val dimensionId = UtilKResource.getIdentifier("status_bar_height", "dimen", "android")
        return if (dimensionId != 0) UtilKRes.getDimensionPixelSize(dimensionId) else 0
    }

    /**
     * 获取状态栏高度2
     * 优点: 依赖于WMS,是在界面构建后根据View获取的,所以高度是动态的
     * 缺点: 在Activity的回调方法onWindowFocusChanged()执行后,才能得到预期的结果
     * @param activity Activity
     * @return Int
     */
    @JvmStatic
    fun getStatusBarHeight(activity: Activity): Int {
        val rect = Rect()
        UtilKDecorView.getWindowVisibleDisplayFrame(activity, rect)
        return rect.top
    }

    /**
     * 获取状态栏高度1
     * 优点: 不需要在Activity的回调方法onWindowFocusChanged()执行后才能得到结果
     * 缺点: 不管你是否设置全屏模式,或是不是显示状态栏,高度是固定的;因为系统资源属性是固定的,真实的,不管你是否隐藏(隐藏或显示),他都在那里
     * @param isCheckFullScreen Boolean 是否把全屏纳入考虑范围, 置true, 全屏返回0
     * @return Int
     */
    @JvmStatic
    fun getStatusBarHeight(isCheckFullScreen: Boolean): Int {
        if (isCheckFullScreen && UtilKScreen.isFullScreen()) return 0
        return getStatusBarHeight()
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 状态栏是否可见
     * @param context Context
     * @return Boolean
     */
    @JvmStatic
    fun isStatusBarVisible(context: Context): Boolean {
        return isStatusBarVisible(UtilKActivity.getActivityByContext(context, true) ?: return true)
    }

    /**
     * 状态栏是否可见
     * @param context Context
     * @return Boolean
     */
    @JvmStatic
    fun isStatusBarVisible(activity: Activity): Boolean =
        !UtilKWindow.isFullScreenInFlag(activity)

    @JvmStatic
    fun isStatusBarTranslucent(activity: Activity): Boolean {
        var isStatusBarAvailable: Boolean
        //检查主题中是否有透明的状态栏
        val typedArray = activity.obtainStyledAttributes(intArrayOf(android.R.attr.windowTranslucentStatus))
        try {
            isStatusBarAvailable = typedArray.getBoolean(0, false)
        } finally {
            typedArray.recycle()
        }
        if (UtilKWindow.getFlags(activity) and CWinMgrLP.FLAG_TRANSLUCENT_STATUS != 0) {
            isStatusBarAvailable = true
        }
        return isStatusBarAvailable
    }
}