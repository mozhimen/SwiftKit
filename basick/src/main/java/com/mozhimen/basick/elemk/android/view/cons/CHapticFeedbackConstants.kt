package com.mozhimen.basick.elemk.android.view.cons

import android.view.HapticFeedbackConstants
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName CHapticFeedbackConstants
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/18
 * @Version 1.0
 */
object CHapticFeedbackConstants {
    //用户对导致正在执行的动作的对象执行了长时间的按压
    const val LONG_PRESS = HapticFeedbackConstants.LONG_PRESS
    //用户按下了虚拟屏幕上的按键
    const val VIRTUAL_KEY = HapticFeedbackConstants.VIRTUAL_KEY
    //用户按下了软键盘按键
    const val KEYBOARD_TAP = HapticFeedbackConstants.KEYBOARD_TAP

    //用户按下时钟的小时或分钟刻度
    @RequiresApi(CVersCode.V_21_5_L)
    const val CLOCK_TICK = HapticFeedbackConstants.CLOCK_TICK
//    const val CALENDAR_DATE = HapticFeedbackConstants.CALENDAR_DATE
    //用户已经在对象上执行了上下文点击
    @RequiresApi(CVersCode.V_23_6_M)
    const val CONTEXT_CLICK = HapticFeedbackConstants.CONTEXT_CLICK
    @RequiresApi(CVersCode.V_27_81_O1)
    const val KEYBOARD_PRESS = HapticFeedbackConstants.KEYBOARD_PRESS
    @RequiresApi(CVersCode.V_27_81_O1)
    const val KEYBOARD_RELEASE = HapticFeedbackConstants.KEYBOARD_RELEASE
    @RequiresApi(CVersCode.V_27_81_O1)
    const val VIRTUAL_KEY_RELEASE = HapticFeedbackConstants.VIRTUAL_KEY_RELEASE
    @RequiresApi(CVersCode.V_27_81_O1)
    const val TEXT_HANDLE_MOVE = HapticFeedbackConstants.TEXT_HANDLE_MOVE
//    const val ENTRY_BUMP = HapticFeedbackConstants.ENTRY_BUMP
//    const val DRAG_CROSSING = HapticFeedbackConstants.DRAG_CROSSING
    @RequiresApi(CVersCode.V_30_11_R)
    const val GESTURE_START = HapticFeedbackConstants.GESTURE_START
    @RequiresApi(CVersCode.V_30_11_R)
    const val GESTURE_END = HapticFeedbackConstants.GESTURE_END
//    const val EDGE_SQUEEZE = HapticFeedbackConstants.EDGE_SQUEEZE
//    const val EDGE_RELEASE = HapticFeedbackConstants.EDGE_RELEASE
    @RequiresApi(CVersCode.V_30_11_R)
    const val CONFIRM = HapticFeedbackConstants.CONFIRM
    @RequiresApi(CVersCode.V_30_11_R)
    const val REJECT = HapticFeedbackConstants.REJECT
//    const val ROTARY_SCROLL_TICK = HapticFeedbackConstants.ROTARY_SCROLL_TICK
//    const val ROTARY_SCROLL_ITEM_FOCUS = HapticFeedbackConstants.ROTARY_SCROLL_ITEM_FOCUS
//    const val ROTARY_SCROLL_LIMIT = HapticFeedbackConstants.ROTARY_SCROLL_LIMIT
//    const val SAFE_MODE_ENABLED = HapticFeedbackConstants.SAFE_MODE_ENABLED
//    const val ASSISTANT_BUTTON = HapticFeedbackConstants.ASSISTANT_BUTTON
//    const val LONG_PRESS_POWER_BUTTON = HapticFeedbackConstants.LONG_PRESS_POWER_BUTTON
    //忽略视图中的设置是否执行触觉反馈，请始终执行
    const val FLAG_IGNORE_VIEW_SETTING = HapticFeedbackConstants.FLAG_IGNORE_VIEW_SETTING
    //忽略是否执行触觉反馈的全局设置，请始终执行
    const val FLAG_IGNORE_GLOBAL_SETTING = HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
}