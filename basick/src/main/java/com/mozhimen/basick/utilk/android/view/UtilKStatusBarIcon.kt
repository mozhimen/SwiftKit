package com.mozhimen.basick.utilk.android.view

import android.annotation.SuppressLint
import android.app.Activity
import android.view.Window
import android.view.WindowManager
import com.mozhimen.basick.elemk.android.view.cons.CView
import com.mozhimen.basick.elemk.android.view.cons.CWinMgr
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.android.os.UtilKRom
import com.mozhimen.basick.utilk.java.lang.UtilKReflect
import com.mozhimen.basick.utilk.android.os.UtilKSystemProperties
import com.mozhimen.basick.utilk.android.os.UtilKSystemPropertiesWrapper
import com.mozhimen.basick.utilk.kotlin.strPackage2clazz

/**
 * @ClassName UtilKStatusBarIcon
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 15:06
 * @Version 1.0
 */
object UtilKStatusBarIcon : BaseUtilK() {
    @JvmStatic
    fun applyIconLowProfile(activity: Activity) {
        UtilKDecorView.applySystemUiVisibilityOr(activity, CView.SystemUiFlag.LOW_PROFILE)
    }

    /**
     * 状态栏字体和图标是否是深色
     * @param activity Activity
     * @param isThemeDark Boolean
     */
    @JvmStatic
    fun applyIcon(activity: Activity, isThemeDark: Boolean) {
        when {
            UtilKRom.isMiui() -> applyIcon_MiuiUi(activity, isThemeDark)
            UtilKRom.isOppo() -> applyIcon_ColorOsUi(activity, isThemeDark)
            UtilKRom.isFlyme() -> applyIcon_FlymeUi(activity, isThemeDark)
            else -> applyIcon_CommonUi(activity, isThemeDark)
        }
    }

    @JvmStatic
    fun applyIcon_MiuiUi(activity: Activity, isThemeDark: Boolean) {
        if (UtilKBuildVersion.isAfterV_23_6_M()) {
            applyIcon_CommonUi(activity, isThemeDark)
        } else if (UtilKSystemPropertiesWrapper.isMIUIAfter6()) {
            applyIcon_MiuiUi_After6(activity, isThemeDark)
        } else "setIcon_MiuiUi: don't support this miui version".et(TAG)
    }

    @JvmStatic
    @SuppressLint("PrivateApi")
    fun applyIcon_MiuiUi_After6(activity: Activity, isDark: Boolean) {
        try {
            val window = UtilKWindow.get(activity)
            val EXTRA_FLAG_STATUS_BAR_DARK_MODE_OBJ = UtilKReflect.getFieldInt("android.view.MiuiWindowManager${'$'}LayoutParams".strPackage2clazz(), "EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            val extraFlagMethod = window.javaClass.getMethod("setExtraFlags", Int::class.java, Int::class.java)
            //状态栏亮色且黑色字体
            extraFlagMethod.invoke(window, if (isDark) EXTRA_FLAG_STATUS_BAR_DARK_MODE_OBJ else 0, EXTRA_FLAG_STATUS_BAR_DARK_MODE_OBJ)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
    }

    @JvmStatic
    fun applyIcon_CommonUi(activity: Activity, isDarkMode: Boolean) {
        if (UtilKBuildVersion.isAfterV_23_6_M()) {
            val window: Window = UtilKWindow.get(activity)
            window.addFlags(CWinMgr.Lpf.DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(CWinMgr.Lpf.TRANSLUCENT_STATUS)
            if (isDarkMode) UtilKDecorView.applySystemUiVisibilityOr(window, CView.SystemUiFlag.LIGHT_STATUS_BAR)
            else UtilKDecorView.applySystemUiVisibilityAnd(window, CView.SystemUiFlag.LIGHT_STATUS_BAR.inv())
//            val flag: Int =
//                if (isDark)
//                    UtilKDecorView.getSystemUiVisibility(window) or CView.SystemUiFlag.LIGHT_STATUS_BAR
//                else
//                    UtilKDecorView.getSystemUiVisibility(window) and CView.SystemUiFlag.LIGHT_STATUS_BAR.inv()
//            UtilKDecorView.setSystemUiVisibility(window, flag)
        }
    }

    @JvmStatic
    fun applyIcon_FlymeUi(activity: Activity, isDarkMode: Boolean) {
        try {
            val window = UtilKWindow.get(activity)
            val layoutParams = window.attributes
            val field_MEIZU_FLAG_DARK_STATUS_BAR_ICON = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val fieldMeizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
            field_MEIZU_FLAG_DARK_STATUS_BAR_ICON.isAccessible = true
            fieldMeizuFlags.isAccessible = true

            val value_MEIZU_FLAG_DARK_STATUS_BAR_ICON = field_MEIZU_FLAG_DARK_STATUS_BAR_ICON.getInt(null)
            var meizuFlags = fieldMeizuFlags.getInt(layoutParams)
            meizuFlags = if (isDarkMode)
                meizuFlags or value_MEIZU_FLAG_DARK_STATUS_BAR_ICON
            else meizuFlags and value_MEIZU_FLAG_DARK_STATUS_BAR_ICON.inv()
            fieldMeizuFlags.setInt(layoutParams, meizuFlags)
            UtilKWindow.applyAttributes(window, layoutParams)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
    }

    @JvmStatic
    fun applyIcon_ColorOsUi(activity: Activity, isDarkMode: Boolean) {//控制字体颜色，只有黑白两色
        //UtilKDecorView.setSystemUiVisibility(activity, 0 or if (isDarkMode) 0x00000010 else 0x00190000)
        if (UtilKBuildVersion.isAfterV_23_6_M()) {
            applyIcon_CommonUi(activity, isDarkMode)
        } else
            UtilKDecorView.applySystemUiVisibilityOr(
                activity, 0 or
                        if (isDarkMode)
                            0x00000010
                        else
                            0x00190000
            )
    }
}