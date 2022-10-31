package com.mozhimen.uicorek.bannerk.commons

import androidx.annotation.LayoutRes
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.mozhimen.uicorek.bannerk.helpers.BannerKAdapter.BannerKViewHolder
import com.mozhimen.uicorek.bannerk.mos.MBannerKItem

/**
 * @ClassName IBannerK
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/19 14:33
 * @Version 1.0
 */
interface IBannerK {
    /**
     * 设置banner数据
     * @param mos List<BannerKMo>
     */
    fun setBannerData(mos: List<MBannerKItem>)

    /**
     * 设置banner数据
     * @param layoutResId Int
     * @param mos List<BannerKMo>
     */
    fun setBannerData(@LayoutRes layoutResId: Int, mos: List<MBannerKItem>)

    /**
     * 设置banner指示器
     * @param indicator IBannerKIndicator<*>
     */
    fun setBannerIndicator(indicator: IBannerKIndicator<*>)

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
     * @param bindAdapter IBannerKBindAdapter
     */
    fun setBindAdapter(bindAdapter: IBannerKBinder)

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

    interface IOnBannerClickListener {
        fun onBannerClick(viewHolder: BannerKViewHolder, bannerKMo: MBannerKItem, position: Int)
    }
}