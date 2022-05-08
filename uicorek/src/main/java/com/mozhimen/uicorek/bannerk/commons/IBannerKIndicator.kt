package com.mozhimen.uicorek.bannerk.commons

import android.view.View

/**
 * @ClassName IBannerKIndicator
 * @Description 指示器统一接口
 * 实现该接口来定义你需要央视的指示器
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/19 14:41
 * @Version 1.0
 */
interface IBannerKIndicator<T : View> {
    /**
     * 获取指示器
     * @return T
     */
    fun get(): T

    /**
     * 初始化Indicator
     * @param count Int 幻灯片数量
     */
    fun inflate(count: Int)

    /**
     * 幻灯片切换回调
     * @param current Int 切换到的幻灯片位置
     * @param count Int 幻灯片数量
     */
    fun onItemChange(current: Int, count: Int)
}