package com.mozhimen.swiftmk.utils.statusbar

import android.annotation.TargetApi
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.mozhimen.swiftmk.utils.os.OSUtil

/**
 * @ClassName StatusBarUtil
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:14
 * @Version 1.0
 */
object StatusBarUtil {
    private const val TYPE_MIUI = 0
    private const val TYPE_FLYME = 1
    private const val TYPE_M = 3

    internal annotation class ViewType

    /**
     * 作用: 设置状态栏透明
     */
    @TargetApi(19)
    fun setTranslucentStatus(activity: Activity) {
        //5.0以上状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            //清除透明状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            //设置状态栏颜色必须添加
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT//设置透明
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //19
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    /**
     * 修改状态栏颜色,支持4.4以上的版本
     */
    fun setStatusBarColor(activity: Activity, colorId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            window.statusBarColor = colorId
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //使用SystemBarTintManager,需要先将状态栏设置为透明
            setTranslucentStatus(activity)
            val barTintManager = BarTintManager(activity)
            barTintManager.setBarTintEnabled(true)//显示状态栏
            barTintManager.setBarTintColor(colorId)//显示状态栏颜色
        }
    }

    /**
     * 状态栏字体和图标是否是深色
     */
    fun setImmersiveStatusBar(activity: Activity, fontIconDark: Boolean, isFullScreen: Boolean) {
        if (fontIconDark) {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                    process(activity, isFullScreen)
                    setStatusBarFontIconDark(activity, TYPE_M)
                }
                OSUtil.isMiui() -> {
                    setStatusBarFontIconDark(activity, TYPE_MIUI)
                }
                OSUtil.isFlyme() -> {
                    setStatusBarFontIconDark(activity, TYPE_FLYME)
                }
                else -> {
                    //其他情况下我们将状态栏设置为灰色.就不会看不见字体
                    setStatusBarColor(activity, Color.LTGRAY)//灰色
                }
            }
        }
    }

    /**
     * 采用谷歌原生状态栏文字颜色的方法进行设置,携带SYSTEM_UI_LAYOUT_FULLSCREEN这个flag那么默认界面会变成全屏模式,
     * 需要在根布局中设置FitSystemWindows属性为true, 所以添加Process方法中加入如下的代码
     * 或者在xml中添加android:fitSystemWindows="true"
     */
    private fun process(activity: Activity, isFullScreen: Boolean) {
        //华为,OPPO机型在StatusUtil.setLightStatusBar后布局被顶到状态栏上去了
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val content = (activity.findViewById(android.R.id.content) as ViewGroup).getChildAt(0)
            if (content != null && !isFullScreen) {
                content.fitsSystemWindows = true
            }
        }
    }

    /**
     * 设置文字颜色
     */
    private fun setStatusBarFontIconDark(activity: Activity, @ViewType type: Int) {
        when (type) {
            TYPE_MIUI -> setMiuiUI(activity, true)
            TYPE_M -> setCommonUI(activity)
            TYPE_FLYME -> setFlymeUI(activity, true)
        }
    }

    /**
     * 设置MIUI样式字体
     */
    private fun setMiuiUI(activity: Activity, dark: Boolean) {
        try {
            val window = activity.window
            val cls = activity.window.javaClass
            val layoutParams = Class.forName("android.view.MiuiWindowManager${'$'}LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            val darkModeFlag = field.getInt(layoutParams)
            val extraFlagMethod = cls.getMethod("setExtraFlags", Int::class.java, Int::class.java)
            if (dark) {
                //状态栏亮色且黑色字体
                extraFlagMethod.invoke(window, darkModeFlag, darkModeFlag)
            } else {
                extraFlagMethod.invoke(window, 0, darkModeFlag)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 设置Flyme样式字体
     */
    private fun setFlymeUI(activity: Activity, dark: Boolean) {
        try {
            val window = activity.window
            val layoutParams = window.attributes
            val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true

            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(layoutParams)
            value = if (dark) value or bit else value and bit.inv()
            meizuFlags.setInt(layoutParams, value)
            window.attributes = layoutParams
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 设置6.0字体
     */
    private fun setCommonUI(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}