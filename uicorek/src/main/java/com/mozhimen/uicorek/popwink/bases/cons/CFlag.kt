package com.mozhimen.uicorek.popwink.bases.cons


/**
 * @ClassName CFlag
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/17 13:10
 * @Version 1.0
 */
object CFlag {
    private const val KEYBOARD_CONTROL_SHIFT = 16
    const val KEYBOARD_ALIGN_TO_VIEW = 0x1 shl KEYBOARD_CONTROL_SHIFT
    const val KEYBOARD_ALIGN_TO_ROOT = 0x2 shl KEYBOARD_CONTROL_SHIFT
    const val KEYBOARD_IGNORE_OVER_KEYBOARD = 0x4 shl KEYBOARD_CONTROL_SHIFT
    const val KEYBOARD_ANIMATE_ALIGN = 0x8 shl KEYBOARD_CONTROL_SHIFT
    const val KEYBOARD_FORCE_ADJUST = 0x10 shl KEYBOARD_CONTROL_SHIFT
}