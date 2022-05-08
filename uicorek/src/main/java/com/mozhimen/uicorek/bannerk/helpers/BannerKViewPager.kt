package com.mozhimen.uicorek.bannerk.helpers

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import java.lang.Exception

/**
 * @ClassName BannerKViewPager
 * @Description 实现了自动翻页的ViewPager
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/19 17:40
 * @Version 1.0
 */
class BannerKViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ViewPager(context, attrs) {

    private var _intervalTime = 0
    private var _autoPlay = true//是否开启自动轮播
    private var _isLayout = false
    private val _handler = Handler(Looper.getMainLooper())
    private val _runnable: Runnable = object : Runnable {
        override fun run() {
            nextItem()
            _handler.postDelayed(this, _intervalTime.toLong()) //延时一定时间执行下一次
        }
    }

    /**
     * 是否自动轮播
     * @param autoPlay Boolean
     */
    fun setAutoPlay(autoPlay: Boolean) {
        this._autoPlay = autoPlay
        if (!_autoPlay) {
            _handler.removeCallbacks(_runnable)
        }
    }

    /**
     * 设置ViewPager的滚动速度
     * @param duration Int page切换的时间长度
     */
    fun setScrollDuration(duration: Int) {
        try {
            val scrollerField = ViewPager::class.java.getDeclaredField("mScroller")
            scrollerField.isAccessible = true
            scrollerField[this] = BannerKScroller(context, duration)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 设置页面停留时间
     * @param intervalTime Int 停留时间单位毫秒
     */
    fun setIntervalTime(intervalTime: Int) {
        this._intervalTime = intervalTime
    }

    /**
     * 开始
     */
    fun start() {
        _handler.removeCallbacksAndMessages(null)
        if (_autoPlay) {
            _handler.postDelayed(_runnable, _intervalTime.toLong())
        }
    }

    /**
     * 结束
     */
    fun stop() {
        _handler.removeCallbacksAndMessages(null) //停止Timer
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
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
            }
        }
        start()
    }

    override fun onDetachedFromWindow() {
        //fix 使用RecyclerView + ViewPager bug
        if ((context as Activity).isFinishing) {
            super.onDetachedFromWindow()
        }
        stop()
    }

    private fun nextItem(): Int {
        var nextPosition = -1
        if (adapter == null || adapter!!.count <= 1) {
            stop()
            return nextPosition
        }
        nextPosition = currentItem + 1
        //下一个索引大于adapter的view的最大数量时重新开始
        if (nextPosition >= adapter!!.count) {
            nextPosition = (adapter as BannerKAdapter).getFirstItem()
        }
        setCurrentItem(nextPosition, true)
        return nextPosition
    }
}