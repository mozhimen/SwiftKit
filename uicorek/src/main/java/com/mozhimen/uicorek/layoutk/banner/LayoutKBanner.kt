package com.mozhimen.uicorek.layoutk.banner

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKFrame
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.commons.IUicoreK
import com.mozhimen.uicorek.layoutk.banner.commons.IBanner
import com.mozhimen.uicorek.layoutk.banner.commons.IBannerBindListener
import com.mozhimen.uicorek.layoutk.banner.commons.IBannerIndicator
import com.mozhimen.uicorek.layoutk.banner.commons.IBannerItemClickListener
import com.mozhimen.uicorek.layoutk.banner.helpers.LayoutKBannerProxy
import com.mozhimen.uicorek.layoutk.banner.mos.MBannerItem

/**
 * @ClassName LayoutKBanner
 * @Description 核心问题:
 * 1. 如何实现UI的高度定制
 * 2. 作为有限的item如何实现无限轮播?
 * 3. Banner需要展示网络图片.如何将网络图片库和Banner组件进行解耦?
 * 4. 指示器样式各异, 如何实现指示器的高度定制?
 * 5. 如何设置Viewpager的滚动速度
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/19 23:26
 * @Version 1.0
 */
class LayoutKBanner @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    BaseLayoutKFrame(context, attrs, defStyleAttr), IBanner {
    private val _layoutKBannerProxy: LayoutKBannerProxy by lazy { LayoutKBannerProxy(context, this) }

    private var _autoPlay = true
    private var _loop = true
    private var _enableScroll = true
    private var _intervalTime = -1
    private var _scrollDuration = -1
    init {
        initAttrs(attrs, defStyleAttr)
    }

    override fun setBannerData(mos: List<MBannerItem>) {
        _layoutKBannerProxy.setBannerData(mos)
    }

    override fun setBannerData(layoutResId: Int, mos: List<MBannerItem>) {
        _layoutKBannerProxy.setBannerData(layoutResId, mos)
    }

    override fun setBannerIndicator(indicator: IBannerIndicator<*>) {
        _layoutKBannerProxy.setBannerIndicator(indicator)
    }

    override fun setEnableScroll(enable: Boolean) {
        _layoutKBannerProxy.setEnableScroll(enable)
    }

    override fun setAutoPlay(autoPlay: Boolean) {
        _layoutKBannerProxy.setAutoPlay(autoPlay)
    }

    override fun setLoop(loop: Boolean) {
        _layoutKBannerProxy.setLoop(loop)
    }

    override fun setScrollDuration(duration: Int) {
        _layoutKBannerProxy.setScrollDuration(duration)
    }

    override fun setIntervalTime(intervalTime: Int) {
        _layoutKBannerProxy.setIntervalTime(intervalTime)
    }

    override fun setCurrentPosition(position: Int, smoothScroll: Boolean) {
        _layoutKBannerProxy.setCurrentPosition(position, smoothScroll)
    }

    override fun scrollToNextItem() {
        _layoutKBannerProxy.scrollToNextItem()
    }

    override fun scrollToPreviousItem() {
        _layoutKBannerProxy.scrollToPreviousItem()
    }

    override fun getCurrentPosition(): Int {
        return _layoutKBannerProxy.getCurrentPosition()
    }

    override fun setBannerBindListener(listener: IBannerBindListener) {
        _layoutKBannerProxy.setBannerBindListener(listener)
    }

    override fun setPagerChangeListener(listener: ViewPager.OnPageChangeListener) {
        _layoutKBannerProxy.setPagerChangeListener(listener)
    }

    override fun setBannerClickListener(listener: IBannerItemClickListener) {
        _layoutKBannerProxy.setBannerClickListener(listener)
    }
    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutKBanner)
            _enableScroll = typedArray.getBoolean(R.styleable.LayoutKBanner_layoutKBanner_enableScroll, _enableScroll)
            _autoPlay = typedArray.getBoolean(R.styleable.LayoutKBanner_layoutKBanner_autoPlay, _autoPlay)
            _loop = typedArray.getBoolean(R.styleable.LayoutKBanner_layoutKBanner_loop, _loop)
            _intervalTime = typedArray.getInteger(R.styleable.LayoutKBanner_layoutKBanner_intervalTime, _intervalTime)
            _scrollDuration = typedArray.getInteger(R.styleable.LayoutKBanner_layoutKBanner_scrollDuration, _scrollDuration)
            typedArray.recycle()
        }

        setEnableScroll(_enableScroll)
        setAutoPlay(_autoPlay)
        setLoop(_loop)
        setIntervalTime(_intervalTime)
        setScrollDuration(_scrollDuration)
    }
}