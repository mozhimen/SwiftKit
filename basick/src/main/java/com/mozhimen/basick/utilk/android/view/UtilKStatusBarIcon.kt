package com.mozhimen.basick.utilk.android.view

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.view.Window
import android.view.WindowManager
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.elemk.cons.CView
import com.mozhimen.basick.elemk.cons.CWinMgr
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.android.os.UtilKRom
import com.mozhimen.basick.utilk.android.os.UtilKRomVersion

/**
 * @ClassName UtilKStatusBarIcon
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 15:06
 * @Version 1.0
 */
object UtilKStatusBarIcon : BaseUtilK() {
    /**
     * 状态栏字体和图标是否是深色
     */
    @JvmStatic
    fun setIcon(activity: Activity, isDark: Boolean) {
        when {
            UtilKRom.isMiui() -> setIcon_MiuiUi(activity, isDark)
            UtilKRom.isOppo() -> setIcon_ColorOsUi(activity, isDark)
            UtilKRom.isFlyme() -> setIcon_FlymeUi(activity, isDark)
            else -> setIcon_CommonUi(activity, isDark)
        }
    }

    @JvmStatic
    fun setIcon_MiuiUi(activity: Activity, isDark: Boolean) {
        if (Build.VERSION.SDK_INT > CVersionCode.V_23_6_M) {
            setIcon_CommonUi(activity, isDark)
        } else if (UtilKRomVersion.isMIUILarger6()) {
            setIcon_MiuiUi_After6(activity, isDark)
        } else "setStatusBarFontIcon_MiuiUI: dont support this miui version".et(TAG)
    }

    @JvmStatic
    @SuppressLint("PrivateApi")
    fun setIcon_MiuiUi_After6(activity: Activity, isDark: Boolean) {
        try {
            val window = UtilKWindow.get(activity)
            val layoutParams: Class<*> = Class.forName("android.view.MiuiWindowManager${'$'}LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            val darkModeFlag = field.getInt(layoutParams)
            val extraFlagMethod = window.javaClass.getMethod("setExtraFlags", Int::class.java, Int::class.java)
            //状态栏亮色且黑色字体
            extraFlagMethod.invoke(window, if (isDark) darkModeFlag else 0, darkModeFlag)
        } catch (e: Exception) {
            "setStatusBarFontIcon_MiuiUILarger6: ${e.message}".et(TAG)
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun setIcon_CommonUi(activity: Activity, isDark: Boolean) {
        if (Build.VERSION.SDK_INT >= CVersionCode.V_23_6_M) {
            val window: Window = UtilKWindow.get(activity)
            window.addFlags(CWinMgr.Lpf.DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(CWinMgr.Lpf.TRANSLUCENT_STATUS)
            val flag: Int = if (isDark)
                UtilKDecorView.getSystemUiVisibility(window) or CView.SystemUi.FLAG_LIGHT_STATUS_BAR
            else
                UtilKDecorView.getSystemUiVisibility(window) and CView.SystemUi.FLAG_LIGHT_STATUS_BAR.inv()
            UtilKDecorView.setSystemUiVisibility(window, flag)
        }
    }

    @JvmStatic
    fun setIcon_FlymeUi(activity: Activity, isDark: Boolean) {
        try {
            val window = UtilKWindow.get(activity)
            val layoutParams = window.attributes
            val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true

            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(layoutParams)
            value = if (isDark) value or bit else value and bit.inv()
            meizuFlags.setInt(layoutParams, value)
            UtilKWindow.setAttributes(window, layoutParams)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun setIcon_ColorOsUi(activity: Activity, isDark: Boolean) {
        //控制字体颜色，只有黑白两色
        UtilKDecorView.setSystemUiVisibility(activity, 0 or if (isDark) 0x00000010 else 0x00190000)
    }
}