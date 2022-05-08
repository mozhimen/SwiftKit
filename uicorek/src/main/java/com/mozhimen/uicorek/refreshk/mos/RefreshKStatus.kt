package com.mozhimen.uicorek.refreshk.mos

/**
 * @ClassName RefreshKStatus
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/17 23:37
 * @Version 1.0
 */
enum class RefreshKStatus {
    /**
     * 初始态
     */
    INIT,

    /**
     * Header展示的状态
     */
    VISIBLE,

    /**
     * 刷新中的状态
     */
    REFRESHING,

    /**
     * 头部超出可刷新距离的状态
     */
    OVERFLOW,

    /**
     * 超出刷新位置松开手后的状态
     */
    OVERFLOW_RELEASE
}