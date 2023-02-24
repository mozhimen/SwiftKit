package com.mozhimen.basick.statusbark.helpers

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.bar.UtilKStatusBar
import com.mozhimen.basick.utilk.os.UtilKOS
import com.mozhimen.basick.utilk.bar.UtilKTitleBar

/**
 * @ClassName StatusBarKHelper
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:14
 * @Version 1.0
 */
object StatusBarKHelper {
    private const val TAG = "StatusBarKHelper>>>>>"

    /**
     * 设置状态栏沉浸式
     * @param activity Activity
     */
    @JvmStatic
    fun setStatusBarImmersed(activity: Activity) {
        val window = activity.window
        val decorView = window.decorView
        //5.0以上状态栏透明
        if (Build.VERSION.SDK_INT >= CVersionCode.V_21_5_L) {//21
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)//清除透明状态栏
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)//设置状态栏颜色必须添加
            window.statusBarColor = Color.TRANSPARENT//设置透明
        } else if (Build.VERSION.SDK_INT >= CVersionCode.V_19_44_K) {//19
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    /**
     * 设置状态栏全屏
     * @param activity Activity
     */
    @JvmStatic
    fun setStatusBarFullScreen(activity: Activity) {
        UtilKTitleBar.hideTitleBar(activity)
        UtilKStatusBar.hideStatusBar(activity)
        setDecorViewFitSys(activity)
    }

    /**
     * 采用谷歌原生状态栏文字颜色的方法进行设置,携带SYSTEM_UI_LAYOUT_FULLSCREEN这个flag那么默认界面会变成全屏模式,
     * 需要在根布局中设置FitSystemWindows属性为true, 所以添加Process方法中加入如下的代码
     * 或者在xml中添加android:fitSystemWindows="true"
     */
    @JvmStatic
    fun setDecorViewFitSys(activity: Activity) {
        //华为,OPPO机型在StatusUtil.setLightStatusBar后布局被顶到状态栏上去了
        if (Build.VERSION.SDK_INT >= CVersionCode.V_23_6_M) {
            val content = (activity.findViewById(android.R.id.content) as ViewGroup).getChildAt(0)
            if (content != null) {
                content.fitsSystemWindows = true
            }
        }
    }

    /**
     * 修改状态栏颜色,支持4.4以上的版本
     */
    @JvmStatic
    fun setStatusBarColor(activity: Activity, colorId: Int) {
        if (Build.VERSION.SDK_INT >= CVersionCode.V_21_5_L) {
            val window = activity.window
            window.statusBarColor = colorId
        } else if (Build.VERSION.SDK_INT >= CVersionCode.V_19_44_K) {
            //使用SystemBarTintManager,需要先将状态栏设置为透明
            setStatusBarImmersed(activity)
            val barTintMgr = BarTintMgr(activity)
            barTintMgr.setBarTintEnabled(true)//显示状态栏
            barTintMgr.setBarTintColor(colorId)//显示状态栏颜色
        }
    }

    /**
     * 状态栏字体和图标是否是深色
     */
    @JvmStatic
    fun setStatusBarFontIcon(activity: Activity, isDark: Boolean) {
        if (isDark) {
            when {
                UtilKOS.isMiui() -> {
                    UtilKStatusBar.setStatusBarFontIcon_MiuiUI(activity, isDark)
                }
                UtilKOS.isOppo() -> {
                    UtilKStatusBar.setStatusBarFontIcon_ColorOSUI(activity, isDark)
                }
                UtilKOS.isFlyme() -> {
                    UtilKStatusBar.setStatusBarFontIcon_FlymeUI(activity, isDark)
                }
                else -> {
                    UtilKStatusBar.setStatusBarFontIcon_CommonUI(activity, isDark)
                }
            }
        }
    }
}