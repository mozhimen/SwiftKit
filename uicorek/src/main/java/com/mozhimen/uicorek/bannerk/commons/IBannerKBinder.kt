package com.mozhimen.uicorek.bannerk.commons

import com.mozhimen.uicorek.bannerk.helpers.BannerKAdapter.BannerKViewHolder
import com.mozhimen.uicorek.bannerk.mos.MBannerKItem

/**
 * @ClassName IBannerKBindAdapter
 * @Description IBannerKBindAdapter的数据绑定接口,基于该接口可以实现数据的绑定和框架层解耦
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/19 14:40
 * @Version 1.0
 */
interface IBannerKBinder {
    /**
     * 当绑定的时候
     * @param viewHolder BannerKViewHolder
     * @param mo BannerKMo
     * @param position Int
     */
    fun onBind(viewHolder: BannerKViewHolder, mo: MBannerKItem, position: Int)
}