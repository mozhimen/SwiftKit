package com.mozhimen.uicorek.bannerk

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import com.mozhimen.basick.basek.BaseKLayoutFrame
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.bannerk.commons.IBannerK
import com.mozhimen.uicorek.bannerk.commons.IBannerKBindAdapter
import com.mozhimen.uicorek.bannerk.commons.IBannerKIndicator
import com.mozhimen.uicorek.bannerk.mos.BannerKMo

/**
 * @ClassName BannerK
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
class BannerK @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    BaseKLayoutFrame(context, attrs, defStyleAttr), IBannerK {
    private var delegate: BannerKDelegate = BannerKDelegate(context, this)

    private var _autoPlay = true
    private var _loop = true
    private var _intervalTime = -1
    private var _scrollDuration = -1

    init {
        initAttrs(attrs, defStyleAttr)
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerK)
        _autoPlay = typedArray.getBoolean(R.styleable.BannerK_bannerK_autoPlay, true)
        _loop = typedArray.getBoolean(R.styleable.BannerK_bannerK_loop, true)
        _intervalTime = typedArray.getInteger(R.styleable.BannerK_bannerK_intervalTime, -1)
        _scrollDuration = typedArray.getInteger(R.styleable.BannerK_bannerK_scrollDuration, -1)
        typedArray.recycle()

        setAutoPlay(_autoPlay)
        setLoop(_loop)
        setScrollDuration(_scrollDuration)
        setIntervalTime(_intervalTime)
    }

    /**
     * 设置Banner数据
     * @param mos List<BannerKMo>
     */
    override fun setBannerData(mos: List<BannerKMo>) {
        delegate.setBannerData(mos)
    }

    /**
     * 设置Banner数据
     * @param layoutResId Int
     * @param mos List<BannerKMo>
     */
    override fun setBannerData(layoutResId: Int, mos: List<BannerKMo>) {
        delegate.setBannerData(layoutResId, mos)
    }

    /**
     * 设置指示器
     * @param indicator IBannerKIndicator<*>
     */
    override fun setBannerIndicator(indicator: IBannerKIndicator<*>) {
        delegate.setBannerIndicator(indicator)
    }

    /**
     * 设置自动轮播
     * @param autoPlay Boolean
     */
    override fun setAutoPlay(autoPlay: Boolean) {
        delegate.setAutoPlay(autoPlay)
    }

    /**
     * 设置循环播放
     * @param loop Boolean
     */
    override fun setLoop(loop: Boolean) {
        delegate.setLoop(loop)
    }

    /**
     * 设置滚动时长
     * @param duration Int page切换的时间长度 ,默认1000ms
     */
    override fun setScrollDuration(duration: Int) {
        delegate.setScrollDuration(duration)
    }

    /**
     * 设置单页驻留时长
     * @param intervalTime Int 默认5000
     */
    override fun setIntervalTime(intervalTime: Int) {
        delegate.setIntervalTime(intervalTime)
    }

    /**
     * 设置当前item
     * @param position Int
     */
    override fun setCurrentItem(position: Int) {
        delegate.setCurrentItem(position)
    }

    /**
     * 设置绑定adapter
     * @param bindAdapter IBannerKBindAdapter
     */
    override fun setBindAdapter(bindAdapter: IBannerKBindAdapter) {
        delegate.setBindAdapter(bindAdapter)
    }

    /**
     * 设置页面改换监听器
     * @param onPageChangeListener OnPageChangeListener
     */
    override fun setOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener) {
        delegate.setOnPageChangeListener(onPageChangeListener)
    }

    /**
     * 设置页面点击监听器
     * @param IOnBannerClickListener OnBannerClickListener
     */
    override fun setOnBannerClickListener(IOnBannerClickListener: IBannerK.IOnBannerClickListener) {
        delegate.setOnBannerClickListener(IOnBannerClickListener)
    }
}