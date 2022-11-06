package com.mozhimen.uicorek.layoutk.banner.commons

import com.mozhimen.uicorek.layoutk.banner.helpers.BannerViewHolder
import com.mozhimen.uicorek.layoutk.banner.mos.MBannerItem

/**
 * @ClassName IBannerBindListener
 * @Description IBannerBindListener,基于该接口可以实现数据的绑定和框架层解耦
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/19 14:40
 * @Version 1.0
 */
interface IBannerBindListener {
    /**
     * 当绑定的时候
     * @param viewHolder BannerViewHolder
     * @param mo MBannerItem
     * @param position Int
     */
    fun onBind(viewHolder: BannerViewHolder, item: MBannerItem, position: Int)
}