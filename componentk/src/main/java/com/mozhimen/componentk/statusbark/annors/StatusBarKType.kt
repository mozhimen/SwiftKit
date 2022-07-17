package com.mozhimen.componentk.statusbark.annors

/**
 * @ClassName StatusBarKType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/7/17 17:41
 * @Version 1.0
 */
annotation class StatusBarKType {
    companion object {
        const val FULL_SCREEN = 0//是否全屏状态栏
        const val IMMERSED = 1//是否沉浸式状态栏(即状态栏透明, 页面延展至状态栏下)
        const val CUSTOM = 2
    }
}