package com.mozhimen.uicorek.layoutk.banner.helpers

import android.content.Context
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.layoutk.banner.LayoutKBanner
import com.mozhimen.uicorek.layoutk.banner.commons.IBanner
import com.mozhimen.uicorek.layoutk.banner.commons.IBannerBindListener
import com.mozhimen.uicorek.layoutk.banner.commons.IBannerIndicator
import com.mozhimen.uicorek.layoutk.banner.commons.IOnBannerClickListener
import com.mozhimen.uicorek.layoutk.banner.mos.MBannerItem
import com.mozhimen.uicorek.layoutk.banner.temps.PointIndicator

/**
 * @ClassName LayoutKBannerProxy
 * @Description Banner的控制器
 * 辅助Banner完成各种功能的控制
 * 将Banner的一些逻辑内聚在这,保证暴露给使用者的Banner干净整洁
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/20 11:45
 * @Version 1.0
 */
class LayoutKBannerProxy(
    private val _context: Context,
    private val _layoutKBanner: LayoutKBanner
) : IBanner, OnPageChangeListener {

    private var _adapter: BannerAdapter? = null
    private var _indicator: IBannerIndicator<*>? = null
    private var _bannerItems: List<MBannerItem>? = null
    private var _onPageChangeListener: OnPageChangeListener? = null
    private var _iOnBannerClickListener: IOnBannerClickListener? = null
    private var _viewPager: BannerViewPager? = null

    private var _currentItemIndex = 0

    private var _autoPlay = false//后期更新也能生效
    private var _scrollDuration = -1

    private var _loop = false
    private var _enableScroll = true
    private var _intervalTime = 5000

    override fun setBannerData(mos: List<MBannerItem>) {
        setBannerData(R.layout.layoutk_banner, mos)
    }

    override fun setBannerData(layoutResId: Int, mos: List<MBannerItem>) {
        _bannerItems = mos
        init(layoutResId)
    }

    override fun setBannerIndicator(indicator: IBannerIndicator<*>) {
        this._indicator = indicator
    }

    override fun setEnableScroll(enable: Boolean) {
        this._enableScroll = enable
    }

    override fun setAutoPlay(autoPlay: Boolean) {
        this._autoPlay = autoPlay
        _adapter?.setAutoPlay(autoPlay)
        _viewPager?.setAutoPlay(autoPlay)
    }

    override fun setLoop(loop: Boolean) {
        this._loop = loop
    }

    override fun setScrollDuration(duration: Int) {
        this._scrollDuration = duration
        if (_viewPager != null && duration > 0) {
            _viewPager!!.setScrollDuration(duration)
        }
    }

    override fun setIntervalTime(intervalTime: Int) {
        if (intervalTime > 0) {
            this._intervalTime = intervalTime
        }
    }

    override fun setCurrentItem(position: Int) {
        if (_currentItemIndex >= 0 && _currentItemIndex < _bannerItems!!.size) {
            _currentItemIndex = position
            _viewPager?.setCurrentItem(_currentItemIndex, false)
        }
    }

    override fun setBindAdapter(bindAdapter: IBannerBindListener) {
        _adapter?.setBindAdapter(bindAdapter)
    }

    override fun setOnPageChangeListener(onPageChangeListener: OnPageChangeListener) {
        this._onPageChangeListener = onPageChangeListener
    }

    override fun setOnBannerClickListener(IOnBannerClickListener: IOnBannerClickListener) {
        this._iOnBannerClickListener = IOnBannerClickListener
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (_onPageChangeListener != null && _adapter?.getRealCount() != 0) {
            _onPageChangeListener!!.onPageScrolled(
                position % _adapter!!.getRealCount(),
                positionOffset,
                positionOffsetPixels
            )
        }
    }

    override fun onPageSelected(position: Int) {
        requireNotNull(_adapter) { "adapter must not be null!" }
        if (_adapter!!.getRealCount() == 0) {
            return
        }
        val pos = position % _adapter!!.getRealCount()
        _currentItemIndex = pos
        _onPageChangeListener?.onPageSelected(pos)
        _indicator?.onItemChange(pos, _adapter!!.getRealCount())
    }

    override fun onPageScrollStateChanged(state: Int) {
        _onPageChangeListener?.onPageScrollStateChanged(state)
    }

    private fun init(layoutResId: Int) {
        requireNotNull(_bannerItems) { "bannerMos must not be null!" }
        if (_adapter == null) {
            _adapter = BannerAdapter(_context)
        }
        if (_indicator == null) {
            _indicator = PointIndicator(_context)
        }
        _indicator!!.inflate(_bannerItems!!.size)
        _adapter!!.apply {
            setLayoutResId(layoutResId)
            setBannerData(_bannerItems!!)
            setAutoPlay(_autoPlay)
            setLoop(_loop)
            _iOnBannerClickListener?.let {
                setOnBannerClickListener(it)
            }
        }

        _viewPager = BannerViewPager(_context)
        _viewPager!!.apply {
            setEnableScroll(_enableScroll)
            setIntervalTime(_intervalTime)
            addOnPageChangeListener(this@LayoutKBannerProxy)
            setAutoPlay(_autoPlay)
            if (_scrollDuration > 0) setScrollDuration(_scrollDuration)
            adapter = _adapter
            if ((_loop || _autoPlay) && _adapter!!.getRealCount() != 0) {
                //无限轮播关键点: 使第一张能反向滑动到最后一张, 已经达到无限滚动的效果
                val firstItem: Int = if (_currentItemIndex != 0) _currentItemIndex else _adapter!!.getFirstItem()
                _viewPager!!.setCurrentItem(firstItem, false)
            }
        }

        val layoutParams =
            FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        //清除缓存view
        _layoutKBanner.removeAllViews()
        _layoutKBanner.addView(_viewPager, layoutParams)
        _layoutKBanner.addView(_indicator!!.get(), layoutParams)
    }
}