package com.mozhimen.uicorek.layoutk.banner.helpers

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import com.mozhimen.basick.utilk.exts.et
import com.mozhimen.uicorek.pagerk.PagerKDisScroll
import java.lang.Exception

/**
 * @ClassName BannerViewPager
 * @Description 实现了自动翻页的ViewPager
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/19 17:40
 * @Version 1.0
 */
class BannerViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    PagerKDisScroll(context, attrs) {


    private var _intervalTime = 0
    private var _scrollDuration = 0
    private var _autoPlay = true//是否开启自动轮播
    private var _isAutoPlaying = false
    private var _isLayout = false
    private val _autoPlayHandler = Handler(Looper.getMainLooper())
    private val _setItemHandler = Handler(Looper.getMainLooper())

    private val _runnable: Runnable = object : Runnable {
        override fun run() {
            autoScrollToNextItem()
            _autoPlayHandler.postDelayed(this, _intervalTime.toLong()) //延时一定时间执行下一次
        }
    }

    fun setAutoPlay(autoPlay: Boolean) {
        _autoPlay = autoPlay
        if (!_autoPlay) {
            stop()
        }
    }

    fun setScrollDuration(duration: Int) {
        _scrollDuration = duration
        try {
            val scrollerField = ViewPager::class.java.getDeclaredField("mScroller")
            scrollerField.isAccessible = true
            scrollerField[this] = BannerScroller(context, duration)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
    }

    fun setIntervalTime(intervalTime: Int) {
        _intervalTime = intervalTime
    }

    fun setCurrentItemSelf(item: Int, smoothScroll: Boolean, isDelay: Boolean = false) {
        if (isDelay) {
            stop()
            setCurrentItem(item, smoothScroll)
            _setItemHandler.postDelayed({
                start()
            }, _intervalTime.toLong())
        } else {
            setCurrentItem(item, smoothScroll)
        }
    }

    fun start() {
        stop()
        if (_autoPlay) {
            _autoPlayHandler.postDelayed(_runnable, _intervalTime.toLong())
        }
        _isAutoPlaying = true
    }

    fun stop() {
        _autoPlayHandler.removeCallbacksAndMessages(null) //停止Timer
        _isAutoPlaying = false
    }

    fun scrollToPreItem(): Int {
        var preItem = currentItem - 1
        //下一个索引大于adapter的view的最大数量时重新开始
        if (preItem < 0) {
            preItem = (adapter as BannerAdapter).getFirstItem()
        }
        setCurrentItemSelf(preItem, true, true)
        return preItem
    }

    fun scrollToNextItem(): Int {
        var nextItem = currentItem + 1
        //下一个索引大于adapter的view的最大数量时重新开始
        if (nextItem >= adapter!!.count) {
            nextItem = (adapter as BannerAdapter).getFirstItem()
        }
        setCurrentItemSelf(nextItem, true, true)
        return nextItem
    }

    private fun autoScrollToNextItem(): Int {
        var nextPosition = -1
        if (adapter == null || adapter!!.count <= 1) {
            stop()
            return nextPosition
        }
        nextPosition = currentItem + 1
        //下一个索引大于adapter的view的最大数量时重新开始
        if (nextPosition >= adapter!!.count) {
            nextPosition = (adapter as BannerAdapter).getFirstItem()
        }
        setCurrentItemSelf(nextPosition, true)
        return nextPosition
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> start()
            else -> stop()
        }
        return super.onTouchEvent(ev)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        _isLayout = true
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (_isLayout && adapter != null && adapter!!.count > 0) {
            try {
                //fix 使用RecyclerView + ViewPager bug https://blog.csdn.net/u011002668/article/details/72884893
                val mScroller = ViewPager::class.java.getDeclaredField("mFirstLayout")
                mScroller.isAccessible = true
                mScroller[this] = false
            } catch (e: Exception) {
                e.printStackTrace()
                e.message?.et(TAG)
            }
        }
        start()
    }

    override fun onDetachedFromWindow() {
        //fix 使用RecyclerView + ViewPager bug
        if (context is Activity && (context as Activity).isFinishing) {
            super.onDetachedFromWindow()
        }
        stop()
        _setItemHandler.removeCallbacksAndMessages(null)
    }
}