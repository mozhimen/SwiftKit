package com.mozhimen.basick.elemk.cons

import android.view.WindowManager
import androidx.annotation.RequiresApi

/**
 * @ClassName CWinMgrLP
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 22:40
 * @Version 1.0
 */
object CWinMgr {
    object Lp {
        const val WRAP_CONTENT = WindowManager.LayoutParams.WRAP_CONTENT
        const val MATCH_PARENT = WindowManager.LayoutParams.MATCH_PARENT
        const val FILL_PARENT = WindowManager.LayoutParams.FILL_PARENT
    }

    object Lpf {
        const val ALLOW_LOCK_WHILE_SCREEN_ON = WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
        const val DIM_BEHIND = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        const val BLUR_BEHIND = WindowManager.LayoutParams.FLAG_BLUR_BEHIND
        const val NOT_FOCUSABLE = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        const val NOT_TOUCHABLE = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        const val NOT_TOUCH_MODAL = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        const val TOUCHABLE_WHEN_WAKING = WindowManager.LayoutParams.FLAG_TOUCHABLE_WHEN_WAKING
        const val KEEP_SCREEN_ON = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        const val LAYOUT_IN_SCREEN = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        const val LAYOUT_NO_LIMITS = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS

        /**
         * 用于隐藏StatusBar
         * 使用此flag,系统会自动忽略输入法的
         * @see Lpsi.ADJUST_RESIZE 的特性
         */
        const val FULLSCREEN = WindowManager.LayoutParams.FLAG_FULLSCREEN
        const val FORCE_NOT_FULLSCREEN = WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
        const val DITHER = WindowManager.LayoutParams.FLAG_DITHER
        const val SECURE = WindowManager.LayoutParams.FLAG_SECURE
        const val SCALED = WindowManager.LayoutParams.FLAG_SCALED
        const val IGNORE_CHEEK_PRESSES = WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES
        const val LAYOUT_INSET_DECOR = WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
        const val ALT_FOCUSABLE_IM = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
        const val WATCH_OUTSIDE_TOUCH = WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
        const val SHOW_WHEN_LOCKED = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
        const val SHOW_WALLPAPER = WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER
        const val TURN_SCREEN_ON = WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        const val DISMISS_KEYGUARD = WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        const val SPLIT_TOUCH = WindowManager.LayoutParams.FLAG_SPLIT_TOUCH
        const val HARDWARE_ACCELERATED = WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        const val LAYOUT_IN_OVERSCAN = WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN

        /**
         * 半透明StatusBar,并且不会因用户交互而被清除。设置了此flag,系统会自动设置
         * @see CView.SystemUi.FLAG_LAYOUT_STABLE 和
         * @see CView.SystemUi.FLAG_LAYOUT_FULLSCREEN
         */
        const val TRANSLUCENT_STATUS = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS

        /**
         * 半透明NavigationBar,并且不会因用户交互而被清除。
         * 设置了此flag,系统会自动设置
         * @see CView.SystemUi.FLAG_LAYOUT_STABLE 和
         * @see CView.SystemUi.FLAG_LAYOUT_HIDE_NAVIGATION
         */
        const val TRANSLUCENT_NAVIGATION = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        const val LOCAL_FOCUS_MODE = WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE

        @RequiresApi(CVersionCode.V_22_51_L1)
        const val LAYOUT_ATTACHED_IN_DECOR = WindowManager.LayoutParams.FLAG_LAYOUT_ATTACHED_IN_DECOR

        /**
         * 用于未StatusBar和NavigationBar设置背景颜色。
         * 原理：将StatusBar和NavigationBar设置为透明背景，并且将StatusBar和NavigationBar所在空间设置为
         * Window.getStatusBarColor() 和Window.getNavigationBarColor()方法获得的颜色。
         */
        const val DRAWS_SYSTEM_BAR_BACKGROUNDS = WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
    }

    object Lpsi {
        /**
         * 在使用同时
         * @see CView.SystemUi.FLAG_LAYOUT_FULLSCREEN 或
         * @see CView.SystemUi.FLAG_LAYOUT_HIDE_NAVIGATION 时，
         * 当键盘弹出时，只会fitSystemWindow=true的view所占区域会被resize，其他view将会被软键盘覆盖
         */
        const val ADJUST_RESIZE = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        const val STATE_UNCHANGED = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED
        const val ADJUST_PAN = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
    }

    object Lpt {
        @RequiresApi(CVersionCode.V_26_8_O)
        const val APPLICATION_OVERLAY = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        const val SYSTEM_ALERT = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        const val TOAST = WindowManager.LayoutParams.TYPE_TOAST
    }
}