package com.mozhimen.basick.utilk.android.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.util.Log
import androidx.annotation.ColorInt
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.elemk.android.view.cons.CView
import com.mozhimen.basick.elemk.android.view.cons.CWinMgr
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.app.UtilKActivity
import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.basick.elemk.android.view.ColorfulStatusBar
import com.mozhimen.basick.utilk.android.content.UtilKResource
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion

/**
 * @ClassName UtilKBar
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/7/17 17:29
 * @Version 1.0
 */
object UtilKStatusBar : BaseUtilK() {
    /**
     * 设置状态栏沉浸式
     * @param activity Activity
     */
    @JvmStatic
    @ADescription("需要${CView.SystemUiFlag.LAYOUT_FULLSCREEN or CView.SystemUiFlag.LAYOUT_STABLE}")
    fun applyTranslucent(activity: Activity) {
        if (UtilKBuildVersion.isAfterV_21_5_L()) {//21//5.0以上状态栏透明
            UtilKWindow.clearFlags(activity, CWinMgr.Lpf.TRANSLUCENT_STATUS)//清除透明状态栏
            //UtilKDecorView.setSystemUiVisibility(activity, CView.SystemUiFlag.LAYOUT_FULLSCREEN or CView.SystemUiFlag.LAYOUT_STABLE)
            Log.d(TAG, "setTranslucent: ${(UtilKDecorView.getWindowSystemUiVisibility(activity) or CView.SystemUiFlag.LAYOUT_FULLSCREEN)== CView.SystemUiFlag.LAYOUT_FULLSCREEN}")
            UtilKWindow.addFlags(activity, CWinMgr.Lpf.DRAWS_SYSTEM_BAR_BACKGROUNDS)//设置状态栏颜色必须添加
            UtilKWindow.applyStatusBarColor(activity, Color.TRANSPARENT)//设置透明
        } else if (UtilKBuildVersion.isAfterV_19_44_K()) {//19
            UtilKWindow.addFlags(activity, CWinMgr.Lpf.TRANSLUCENT_STATUS)
        }
    }

    /**
     * 修改状态栏颜色,支持4.4以上的版本
     */
    @JvmStatic
    fun applyColor(activity: Activity, @ColorInt colorInt: Int) {
        if (UtilKBuildVersion.isAfterV_21_5_L()) {
            UtilKWindow.applyStatusBarColor(activity, colorInt)
        } else if (UtilKBuildVersion.isAfterV_19_44_K()) {
            //使用SystemBarTintManager,需要先将状态栏设置为透明
            applyTranslucent(activity)
            val colorfulStatusBar = ColorfulStatusBar(activity)
            colorfulStatusBar.setEnable(true)//显示状态栏
            colorfulStatusBar.setColor(colorInt)//显示状态栏颜色
        }
    }

    @JvmStatic
    fun hide(activity: Activity) {
        UtilKDecorView.applySystemUiVisibilityOr(activity, CView.SystemUiFlag.FULLSCREEN /*or CView.SystemUi.FLAG_LIGHT_STATUS_BAR*/)
    }

    @JvmStatic
    fun overlay(activity: Activity) {
        UtilKDecorView.applySystemUiVisibilityOr(activity, CView.SystemUiFlag.LAYOUT_FULLSCREEN)
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Return the status bar's height.
     * @return Int
     */
    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    @JvmStatic
    fun getHeight(): Int {
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
    fun getHeight(activity: Activity): Int {
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
    fun getHeight(isCheckFullScreen: Boolean): Int {
        if (isCheckFullScreen && UtilKScreen.isFullScreen()) return 0
        return getHeight()
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 状态栏是否可见
     * @param context Context
     * @return Boolean
     */
    @JvmStatic
    fun isVisible(context: Context): Boolean {
        return isVisible(UtilKActivity.getByContext(context, true) ?: return true)
    }

    /**
     * 状态栏是否可见
     * @param context Context
     * @return Boolean
     */
    @JvmStatic
    fun isVisible(activity: Activity): Boolean =
        !UtilKWindow.isFlagFullScreen(activity)

    @JvmStatic
    fun isTranslucent(activity: Activity): Boolean {
        var isStatusBarAvailable: Boolean
        val typedArray = activity.obtainStyledAttributes(intArrayOf(android.R.attr.windowTranslucentStatus))//检查主题中是否有透明的状态栏
        isStatusBarAvailable = typedArray.getBoolean(0, false)
        typedArray.recycle()
        if (UtilKWindow.isFlagStatusBarTranslucent(activity)) isStatusBarAvailable = true
        return isStatusBarAvailable
    }
}