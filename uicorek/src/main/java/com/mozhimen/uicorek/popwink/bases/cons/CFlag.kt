package com.mozhimen.uicorek.popwink.bases.cons

/**
 * @ClassName CFlag
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/17 13:10
 * @Version 1.0
 */
object CFlag {
    const val MODE_ADD = -1
    const val MODE_REMOVE = -2

    //事件控制 3 bit
    const val EVENT_SHIFT = 0
    const val OUT_SIDE_DISMISS = 0x1 shl EVENT_SHIFT //点击外部消失
    const val OUT_SIDE_TOUCHABLE = 0x2 shl EVENT_SHIFT //外部可以响应事件
    const val BACKPRESS_ENABLE = 0x4 shl EVENT_SHIFT //backpress消失

    //显示控制 3 bit
    const val DISPLAY_SHIFT = 3
    const val OVERLAY_STATUS_BAR = 0x1 shl DISPLAY_SHIFT //允许覆盖状态栏
    const val CLIP_CHILDREN = 0x2 shl DISPLAY_SHIFT //裁剪子控件
    const val OVERLAY_NAVIGATION_BAR = 0x4 shl DISPLAY_SHIFT //允许覆盖导航栏

    //popup控制 6 bit
    const val CONTROL_SHIFT = 7
    const val FADE_ENABLE = 0X1 shl CONTROL_SHIFT // 淡入淡出
    const val AUTO_MIRROR = 0x2 shl CONTROL_SHIFT //自动定位
    const val WITH_ANCHOR = 0x4 shl CONTROL_SHIFT //关联Anchor
    const val AUTO_INPUT_METHOD = 0x8 shl CONTROL_SHIFT //自动弹出输入法
    const val ALIGN_BACKGROUND = 0x10 shl CONTROL_SHIFT //对齐蒙层
    const val FITSIZE = 0x20 shl CONTROL_SHIFT //允许popup重设大小

    //quick popup config
    const val QUICK_POPUP_CONFIG_SHIFT = 14
    const val BLUR_BACKGROUND = 0x1 shl QUICK_POPUP_CONFIG_SHIFT //blur background

    //keyboard
    const val KEYBOARD_CONTROL_SHIFT = 16
    const val KEYBOARD_ALIGN_TO_VIEW = 0x1 shl KEYBOARD_CONTROL_SHIFT
    const val KEYBOARD_ALIGN_TO_ROOT = 0x2 shl KEYBOARD_CONTROL_SHIFT
    const val KEYBOARD_IGNORE_OVER_KEYBOARD = 0x4 shl KEYBOARD_CONTROL_SHIFT
    const val KEYBOARD_ANIMATE_ALIGN = 0x8 shl KEYBOARD_CONTROL_SHIFT
    const val KEYBOARD_FORCE_ADJUST = 0x10 shl KEYBOARD_CONTROL_SHIFT

    //其他用
    const val OTHER_SHIFT = 22
    const val CUSTOM_ON_UPDATE = 0x1 shl OTHER_SHIFT
    const val CUSTOM_ON_ANIMATE_DISMISS = 0x2 shl OTHER_SHIFT
    const val SYNC_MASK_ANIMATION_DURATION = 0x4 shl OTHER_SHIFT //同步蒙层和用户动画的时间
    const val AS_WIDTH_AS_ANCHOR = 0x8 shl OTHER_SHIFT //宽度与anchor一致
    const val AS_HEIGHT_AS_ANCHOR = 0x10 shl OTHER_SHIFT //高度与anchor一致
    const val TOUCHABLE = 0x20 shl OTHER_SHIFT
    const val OVERLAY_MASK = 0x40 shl OTHER_SHIFT //用于overlay status/navigation 覆盖mask层
    const val OVERLAY_CONTENT = 0x80 shl OTHER_SHIFT //用于overlay status/navigation覆盖content层

    const val IDLE = (OUT_SIDE_DISMISS
            or BACKPRESS_ENABLE
            or OVERLAY_STATUS_BAR
            or OVERLAY_NAVIGATION_BAR
            or CLIP_CHILDREN
            or FADE_ENABLE
            or KEYBOARD_ALIGN_TO_ROOT
            or KEYBOARD_IGNORE_OVER_KEYBOARD
            or KEYBOARD_ANIMATE_ALIGN
            or SYNC_MASK_ANIMATION_DURATION
            or TOUCHABLE
            or FITSIZE)
}