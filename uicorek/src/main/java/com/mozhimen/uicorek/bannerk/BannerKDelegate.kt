package com.mozhimen.uicorek.bannerk

import android.content.Context
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.bannerk.commons.IBannerK
import com.mozhimen.uicorek.bannerk.commons.IBannerKBindAdapter
import com.mozhimen.uicorek.bannerk.commons.IBannerKIndicator
import com.mozhimen.uicorek.bannerk.helpers.BannerKAdapter
import com.mozhimen.uicorek.bannerk.helpers.BannerKViewPager
import com.mozhimen.uicorek.bannerk.mos.BannerKMo
import com.mozhimen.uicorek.bannerk.temps.PointIndicator

/**
 * @ClassName BannerKDelegate
 * @Description BannerK的控制器
 * 辅助BannerK完成各种功能的控制
 * 将BannerK的一些逻辑内聚在这,保证暴露给使用者的BannerK干净整洁
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/20 11:45
 * @Version 1.0
 */
class BannerKDelegate(
    private val _context: Context,
    private val _bannerK: BannerK
) : IBannerK, OnPageChangeListener {

    private var _adapter: BannerKAdapter? = null
    private var _indicator: IBannerKIndicator<*>? = null
    private var _bannerKMos: List<BannerKMo>? = null
    private var _onPageChangeListener: OnPageChangeListener? = null
    private var _iOnBannerClickListener: IBannerK.IOnBannerClickListener? = null
    private var _viewPager: BannerKViewPager? = null

    private var _autoPlay = false
    private var _loop = false
    private var _scrollDuration = -1
    private var _intervalTime = 5000
    private var _currentItem = 0

    /**
     * 设置Banner数据
     * @param mos List<BannerKMo>
     */
    override fun setBannerData(mos: List<BannerKMo>) {
        setBannerData(R.layout.bannerk, mos)
    }

    /**
     * 设置Banner数据
     * @param layoutResId Int
     * @param mos List<BannerKMo>
     */
    override fun setBannerData(layoutResId: Int, mos: List<BannerKMo>) {
        _bannerKMos = mos
        init(layoutResId)
    }

    /**
     * 设置指示器
     * @param indicator IBannerKIndicator<*>
     */
    override fun setBannerIndicator(indicator: IBannerKIndicator<*>) {
        this._indicator = indicator
    }

    /**
     * 设置自动轮播
     * @param autoPlay Boolean
     */
    override fun setAutoPlay(autoPlay: Boolean) {
        this._autoPlay = autoPlay
        _adapter?.setAutoPlay(autoPlay)
        _viewPager?.setAutoPlay(autoPlay)
    }

    /**
     * 设置循环播放
     * @param loop Boolean
     */
    override fun setLoop(loop: Boolean) {
        this._loop = loop
    }

    /**
     * 设置滚动时长
     * @param duration Int
     */
    override fun setScrollDuration(duration: Int) {
        this._scrollDuration = duration
        if (_viewPager != null && duration > 0) {
            _viewPager!!.setScrollDuration(duration)
        }
    }

    /**
     * 设置单页驻留时长
     * @param intervalTime Int
     */
    override fun setIntervalTime(intervalTime: Int) {
        if (intervalTime > 0) {
            this._intervalTime = intervalTime
        }
    }

    /**
     * 设置当前item
     * @param position Int
     */
    override fun setCurrentItem(position: Int) {
        if (_currentItem >= 0 && _currentItem < _bannerKMos!!.size) {
            _currentItem = position
            _viewPager?.setCurrentItem(_currentItem, false)
        }
    }

    /**
     * 设置绑定adapter
     * @param bindAdapter IBannerKBindAdapter
     */
    override fun setBindAdapter(bindAdapter: IBannerKBindAdapter) {
        _adapter?.setBindAdapter(bindAdapter)
    }

    /**
     * 设置页面改换监听器
     * @param onPageChangeListener OnPageChangeListener
     */
    override fun setOnPageChangeListener(onPageChangeListener: OnPageChangeListener) {
        this._onPageChangeListener = onPageChangeListener
    }

    /**
     * 设置页面点击监听器
     * @param IOnBannerClickListener OnBannerClickListener
     */
    override fun setOnBannerClickListener(IOnBannerClickListener: IBannerK.IOnBannerClickListener) {
        this._iOnBannerClickListener = IOnBannerClickListener
    }

    /**
     * 页面滑动回调
     * @param position Int
     * @param positionOffset Float
     * @param positionOffsetPixels Int
     */
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (_onPageChangeListener != null && _adapter?.getRealCount() != 0) {
            _onPageChangeListener!!.onPageScrolled(
                position % _adapter!!.getRealCount(),
                positionOffset,
                positionOffsetPixels
            )
        }
    }

    /**
     * 页面点击
     * @param position Int
     */
    override fun onPageSelected(position: Int) {
        requireNotNull(_adapter) { "adapter must not be null!" }
        if (_adapter!!.getRealCount() == 0) {
            return
        }
        val pos = position % _adapter!!.getRealCount()
        _onPageChangeListener?.onPageSelected(pos)
        _indicator?.onItemChange(pos, _adapter!!.getRealCount())
    }

    /**
     * 滑动状态改变回调
     * @param state Int
     */
    override fun onPageScrollStateChanged(state: Int) {
        _onPageChangeListener?.onPageScrollStateChanged(state)
    }

    private fun init(layoutResId: Int) {
        requireNotNull(_bannerKMos) { "bannerMos must not be null!" }
        if (_adapter == null) {
            _adapter = BannerKAdapter(_context)
        }
        if (_indicator == null) {
            _indicator = PointIndicator(_context)
        }
        _indicator!!.inflate(_bannerKMos!!.size)
        _adapter!!.apply {
            setLayoutResId(layoutResId)
            setBannerData(_bannerKMos!!)
            setAutoPlay(_autoPlay)
            setLoop(_loop)
            _iOnBannerClickListener?.let {
                setOnBannerClickListener(it)
            }
        }

        _viewPager = BannerKViewPager(_context)
        _viewPager!!.apply {
            setIntervalTime(_intervalTime)
            addOnPageChangeListener(this@BannerKDelegate)
            setAutoPlay(_autoPlay)
            if (_scrollDuration > 0) setScrollDuration(_scrollDuration)
            adapter = _adapter
            if ((_loop || _autoPlay) && _adapter!!.getRealCount() != 0) {
                //无限轮播关键点: 使第一张能反向滑动到最后一张, 已经达到无限滚动的效果
                val firstItem: Int = if (_currentItem != 0) _currentItem else _adapter!!.getFirstItem()
                _viewPager!!.setCurrentItem(firstItem, false)
            }
        }

        val layoutParams =
            FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        //清除缓存view
        _bannerK.removeAllViews()
        _bannerK.addView(_viewPager, layoutParams)
        _bannerK.addView(_indicator!!.get(), layoutParams)
    }
}