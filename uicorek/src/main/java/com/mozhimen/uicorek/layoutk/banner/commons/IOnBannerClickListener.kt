package com.mozhimen.uicorek.layoutk.banner.commons

import com.mozhimen.uicorek.layoutk.banner.helpers.BannerViewHolder
import com.mozhimen.uicorek.layoutk.banner.mos.MBannerItem

/**
 * @ClassName IOnBannerClickListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/7 2:32
 * @Version 1.0
 */
interface IOnBannerClickListener {
    fun onBannerClick(viewHolder: BannerViewHolder, item: MBannerItem, position: Int)
}