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
        const val FLAG_ALLOW_LOCK_WHILE_SCREEN_ON = WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
        const val FLAG_DIM_BEHIND = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        const val FLAG_BLUR_BEHIND = WindowManager.LayoutParams.FLAG_BLUR_BEHIND
        const val FLAG_NOT_FOCUSABLE = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        const val FLAG_NOT_TOUCHABLE = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        const val FLAG_NOT_TOUCH_MODAL = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        const val FLAG_TOUCHABLE_WHEN_WAKING = WindowManager.LayoutParams.FLAG_TOUCHABLE_WHEN_WAKING
        const val FLAG_KEEP_SCREEN_ON = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        const val FLAG_LAYOUT_IN_SCREEN = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        const val FLAG_LAYOUT_NO_LIMITS = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        const val FLAG_FULLSCREEN = WindowManager.LayoutParams.FLAG_FULLSCREEN
        const val FLAG_FORCE_NOT_FULLSCREEN = WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
        const val FLAG_DITHER = WindowManager.LayoutParams.FLAG_DITHER
        const val FLAG_SECURE = WindowManager.LayoutParams.FLAG_SECURE
        const val FLAG_SCALED = WindowManager.LayoutParams.FLAG_SCALED
        const val FLAG_IGNORE_CHEEK_PRESSES = WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES
        const val FLAG_LAYOUT_INSET_DECOR = WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
        const val FLAG_ALT_FOCUSABLE_IM = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
        const val FLAG_WATCH_OUTSIDE_TOUCH = WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
        const val FLAG_SHOW_WHEN_LOCKED = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
        const val FLAG_SHOW_WALLPAPER = WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER
        const val FLAG_TURN_SCREEN_ON = WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        const val FLAG_DISMISS_KEYGUARD = WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        const val FLAG_SPLIT_TOUCH = WindowManager.LayoutParams.FLAG_SPLIT_TOUCH
        const val FLAG_HARDWARE_ACCELERATED = WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        const val FLAG_LAYOUT_IN_OVERSCAN = WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN
        const val FLAG_TRANSLUCENT_STATUS = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        const val FLAG_TRANSLUCENT_NAVIGATION = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        const val FLAG_LOCAL_FOCUS_MODE = WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE

        @RequiresApi(CVersionCode.V_22_51_L1)
        const val FLAG_LAYOUT_ATTACHED_IN_DECOR = WindowManager.LayoutParams.FLAG_LAYOUT_ATTACHED_IN_DECOR
        const val FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS = WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
    }

    object Lps {
        const val SOFT_INPUT_ADJUST_RESIZE = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        const val SOFT_INPUT_STATE_UNCHANGED = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED
        const val SOFT_INPUT_ADJUST_PAN = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
    }

    object Lpt {
        @RequiresApi(CVersionCode.V_26_8_O)
        const val TYPE_APPLICATION_OVERLAY = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        const val TYPE_SYSTEM_ALERT = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        const val TYPE_TOAST = WindowManager.LayoutParams.TYPE_TOAST
    }
}