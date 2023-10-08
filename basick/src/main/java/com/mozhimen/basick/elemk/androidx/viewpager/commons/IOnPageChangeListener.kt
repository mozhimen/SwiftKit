package com.mozhimen.basick.elemk.androidx.viewpager.commons

import androidx.viewpager.widget.ViewPager

/**
 * @ClassName IOnPageChangeListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/7 11:17
 * @Version 1.0
 */
interface IOnPageChangeListener : ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    override fun onPageSelected(position: Int) {}
}