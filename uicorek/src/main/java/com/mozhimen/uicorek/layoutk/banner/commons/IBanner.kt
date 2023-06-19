package com.mozhimen.uicorek.layoutk.banner.commons

import androidx.annotation.LayoutRes
import com.mozhimen.uicorek.layoutk.banner.IBannerItemChangeListener
import com.mozhimen.uicorek.layoutk.banner.bases.BaseBannerItem

/**
 * @ClassName IBanner
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/19 14:33
 * @Version 1.0
 */
interface IBanner {
    /**
     * 设置banner数据
     * @param mos List<MBannerItem>
     */
    fun setBannerData(mos: List<BaseBannerItem>)

    /**
     * 设置banner数据
     * @param layoutResId Int
     * @param mos List<MBannerItem>
     */
    fun setBannerData(@LayoutRes layoutResId: Int, mos: List<BaseBannerItem>)

    /**
     * 设置banner指示器
     * @param indicator IBannerIndicator<*>
     */
    fun setBannerIndicator(indicator: IBannerIndicator<*>)

    /**
     * 设置Banner是否支持左右滑动
     * @param enable Boolean
     */
    fun setEnableScroll(enable: Boolean)

    /**
     * 设置自动播放
     * @param autoPlay Boolean
     */
    fun setAutoPlay(autoPlay: Boolean)

    /**
     * 设置循环
     * @param loop Boolean
     */
    fun setLoop(loop: Boolean)

    /**
     * 设置滚动时长
     * @param duration Int
     */
    fun setScrollDuration(duration: Int)

    /**
     * 设置滚动间隔时间，设置单页驻留时长
     * @param intervalTime Int 默认5000
     */
    fun setIntervalTime(intervalTime: Int)

    /**
     * 设置当前item的Position
     * @param position Int
     */
    fun setCurrentPosition(position: Int, smoothScroll: Boolean)

    /**
     * 滑动到下一个Item
     */
    fun scrollToNextItem()

    /**
     * 滑动到上一个Item
     */
    fun scrollToPreviousItem()

    /**
     * 获取当前Item的Position
     * @return Int
     */
    fun getCurrentPosition(): Int

    /**
     * 绑定listener
     * @param listener IBannerBindListener
     */
    fun setBannerBindListener(listener: IBannerBindListener)

    /**
     * 页面变换监听器
     * @param listener OnPageChangeListener
     */
    fun setPagerChangeListener(listener: IBannerItemChangeListener)

    /**
     * banner的item点击监听器
     * @param listener OnBannerClickListener
     */
    fun setBannerClickListener(listener: IBannerItemClickListener)
}