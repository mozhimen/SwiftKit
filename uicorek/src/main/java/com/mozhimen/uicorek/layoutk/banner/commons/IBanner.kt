package com.mozhimen.uicorek.layoutk.banner.commons

import androidx.annotation.LayoutRes
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.mozhimen.uicorek.layoutk.banner.mos.MBannerItem

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
    fun setBannerData(mos: List<MBannerItem>)

    /**
     * 设置banner数据
     * @param layoutResId Int
     * @param mos List<MBannerItem>
     */
    fun setBannerData(@LayoutRes layoutResId: Int, mos: List<MBannerItem>)

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
     * 设置停留时长
     * @param duration Int
     */
    fun setScrollDuration(duration: Int)

    /**
     * 设置滚动间隔时间
     * @param intervalTime Int
     */
    fun setIntervalTime(intervalTime: Int)

    /**
     * 设置当前item
     * @param position Int
     */
    fun setCurrentItem(position: Int)

    /**
     * 绑定adapter
     * @param bindAdapter IBannerBindListener
     */
    fun setBindAdapter(bindAdapter: IBannerBindListener)

    /**
     * 页面变换监听器
     * @param onPageChangeListener OnPageChangeListener
     */
    fun setOnPageChangeListener(onPageChangeListener: OnPageChangeListener)

    /**
     * banner的item点击监听器
     * @param IOnBannerClickListener OnBannerClickListener
     */
    fun setOnBannerClickListener(IOnBannerClickListener: IOnBannerClickListener)
}