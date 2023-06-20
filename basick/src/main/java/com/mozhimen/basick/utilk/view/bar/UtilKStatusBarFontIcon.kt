package com.mozhimen.basick.utilk.view.bar

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.elemk.cons.CWinMgrLP
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.log.et
import com.mozhimen.basick.utilk.os.UtilKRom
import com.mozhimen.basick.utilk.os.UtilKRomVersion
import com.mozhimen.basick.utilk.view.window.UtilKDecorView
import com.mozhimen.basick.utilk.view.window.UtilKWindow

/**
 * @ClassName UtilKStatusBarIcon
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 15:06
 * @Version 1.0
 */
object UtilKStatusBarFontIcon : BaseUtilK() {
    /**
     * 状态栏字体和图标是否是深色
     */
    @JvmStatic
    fun setStatusBarFontIcon(activity: Activity, isDark: Boolean) {
        when {
            UtilKRom.isMiui() -> setStatusBarFontIcon_MiuiUI(activity, isDark)
            UtilKRom.isOppo() -> setStatusBarFontIcon_ColorOSUI(activity, isDark)
            UtilKRom.isFlyme() -> setStatusBarFontIcon_FlymeUI(activity, isDark)
            else -> setStatusBarFontIcon_CommonUI(activity, isDark)
        }
    }

    /**
     * 设置Miui状态栏字符
     * @param activity Activity
     * @param isDark Boolean
     */
    @JvmStatic
    fun setStatusBarFontIcon_MiuiUI(activity: Activity, isDark: Boolean) {
        if (Build.VERSION.SDK_INT > CVersionCode.V_23_6_M) {
            setStatusBarFontIcon_CommonUI(activity, isDark)
        } else {
            if (UtilKRomVersion.isMIUILarger6()) {
                setStatusBarFontIcon_MiuiUILarger6(activity, isDark)
            } else {
                "setStatusBarFontIcon_MiuiUI: dont support this miui version".et(TAG)
            }
        }
    }

    /**
     * 设置MIUI<6样式状态栏字符
     * @param activity Activity
     * @param isDark Boolean
     */
    @JvmStatic
    @SuppressLint("PrivateApi")
    fun setStatusBarFontIcon_MiuiUILarger6(activity: Activity, isDark: Boolean) {
        try {
            val window = UtilKWindow.get(activity)
            val layoutParams = Class.forName("android.view.MiuiWindowManager${'$'}LayoutParams")
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

    /**
     * 设置原生状态栏字符
     * @param activity Activity
     * @param isDark Boolean
     */
    @JvmStatic
    fun setStatusBarFontIcon_CommonUI(activity: Activity, isDark: Boolean) {
        if (Build.VERSION.SDK_INT >= CVersionCode.V_23_6_M) {
            val window: Window = UtilKWindow.get(activity)
            window.addFlags(CWinMgrLP.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(CWinMgrLP.FLAG_TRANSLUCENT_STATUS)
            val flag: Int = if (isDark)
                UtilKDecorView.getSystemUiVisibility(window) or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            else
                UtilKDecorView.getSystemUiVisibility(window) and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            UtilKDecorView.setSystemUiVisibility(window, flag)
        }
    }

    /**
     * 设置Flyme样式状态栏字符
     * @param activity Activity
     * @param isDark Boolean
     */
    @JvmStatic
    fun setStatusBarFontIcon_FlymeUI(activity: Activity, isDark: Boolean) {
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
            UtilKWindow.setAttributes(window,layoutParams)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 设置ColorOS的状态栏字符
     * @param activity Activity
     * @param isDark Boolean
     */
    @JvmStatic
    fun setStatusBarFontIcon_ColorOSUI(activity: Activity, isDark: Boolean) {
        //控制字体颜色，只有黑白两色
        UtilKDecorView.setSystemUiVisibility(activity, 0 or if (isDark) 0x00000010 else 0x00190000)
    }
}