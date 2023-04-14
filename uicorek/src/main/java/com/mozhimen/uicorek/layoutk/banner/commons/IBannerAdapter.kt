package com.mozhimen.uicorek.layoutk.banner.commons

import androidx.annotation.LayoutRes
import com.mozhimen.uicorek.layoutk.banner.helpers.BannerViewHolder
import com.mozhimen.uicorek.layoutk.banner.mos.MBannerItem

/**
 * @ClassName IBannerAdapter
 * @Description TODO
 * @Author Mozhimen & Kolin
 * @Date 2023/4/14 18:34
 * @Version 1.0
 */
interface IBannerAdapter {
    /**
     * 设置BannerData
     * @param mos List<MBannerItem>
     */
    fun setBannerData(mos: List<MBannerItem>)

    /**
     * 设置adapter
     * @param listener IBannerBindListener
     */
    fun setBannerBindListener(listener: IBannerBindListener)

    /**
     * 设置监听器
     * @param listener OnBannerClickListener
     */
    fun setBannerClickListener(listener: IBannerItemClickListener)

    /**
     * 设置资源id
     * @param layoutId Int
     */
    fun setLayoutResId(@LayoutRes layoutId: Int)

    /**
     * 设置自动播放
     * @param autoPlay Boolean
     */
    fun setAutoPlay(autoPlay: Boolean)

    /**
     * 设置循环播放
     * @param loop Boolean
     */
    fun setLoop(loop: Boolean)

    /**
     * 获取真实的item数
     * @return Int
     */
    fun getRealCount(): Int

    /**
     * 获取初次展示的item位置
     * @return Int
     */
    fun getFirstItem(): Int

}