package com.mozhimen.uicorek.layoutk.banner

import androidx.viewpager.widget.ViewPager.OnPageChangeListener

/**
 * @ClassName IBannerItemChangeListener
 * @Description TODO
 * @Author Mozhimen & Kolin
 * @Date 2023/4/14 18:26
 * @Version 1.0
 */
interface IBannerItemChangeListener : OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    override fun onPageSelected(position: Int) {}
}