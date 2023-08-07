package com.mozhimen.basick.elemk.android.view.cons

import android.view.MotionEvent


/**
 * @ClassName CMotionEvent
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/7 12:57
 * @Version 1.0
 */
object CMotionEvent {
    object Str {
        const val ACTION_DOWN = "ACTION_DOWN"
        const val ACTION_UP = "ACTION_UP"
        const val ACTION_CANCEL = "ACTION_CANCEL"
        const val ACTION_OUTSIDE = "ACTION_OUTSIDE"
        const val ACTION_MOVE = "ACTION_MOVE"
        const val ACTION_HOVER_MOVE = "ACTION_HOVER_MOVE"
        const val ACTION_SCROLL = "ACTION_SCROLL"
        const val ACTION_HOVER_ENTER = "ACTION_HOVER_ENTER"
        const val ACTION_HOVER_EXIT = "ACTION_HOVER_EXIT"
        const val ACTION_BUTTON_PRESS = "ACTION_BUTTON_PRESS"
        const val ACTION_BUTTON_RELEASE = "ACTION_BUTTON_RELEASE"
        const val ACTION_POINTER_DOWN = "ACTION_POINTER_DOWN"
        const val ACTION_POINTER_UP = "ACTION_POINTER_UP"
    }

    const val ACTION_DOWN = MotionEvent.ACTION_DOWN
    const val ACTION_UP = MotionEvent.ACTION_UP
    const val ACTION_CANCEL = MotionEvent.ACTION_CANCEL
    const val ACTION_OUTSIDE = MotionEvent.ACTION_OUTSIDE
    const val ACTION_MOVE = MotionEvent.ACTION_MOVE
    const val ACTION_HOVER_MOVE = MotionEvent.ACTION_HOVER_MOVE
    const val ACTION_SCROLL = MotionEvent.ACTION_SCROLL
    const val ACTION_HOVER_ENTER = MotionEvent.ACTION_HOVER_ENTER
    const val ACTION_HOVER_EXIT = MotionEvent.ACTION_HOVER_EXIT
    const val ACTION_BUTTON_PRESS = MotionEvent.ACTION_BUTTON_PRESS
    const val ACTION_BUTTON_RELEASE = MotionEvent.ACTION_BUTTON_RELEASE
    const val ACTION_POINTER_INDEX_MASK = MotionEvent.ACTION_POINTER_INDEX_MASK
    const val ACTION_POINTER_INDEX_SHIFT = MotionEvent.ACTION_POINTER_INDEX_SHIFT
    const val ACTION_MASK = MotionEvent.ACTION_MASK
    const val ACTION_POINTER_DOWN = MotionEvent.ACTION_POINTER_DOWN
    const val ACTION_POINTER_UP = MotionEvent.ACTION_POINTER_UP
}