package com.mozhimen.uicorek.refreshk

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Scroller
import com.mozhimen.basicsk.utilk.UtilKScroll.childScrolled
import com.mozhimen.basicsk.utilk.UtilKScroll.findScrollableChild
import com.mozhimen.basicsk.basek.BaseKLayoutFrame
import com.mozhimen.uicorek.datak.DataKRecyclerView
import com.mozhimen.uicorek.refreshk.commons.IRefreshK
import com.mozhimen.uicorek.refreshk.commons.RefreshKOverView
import com.mozhimen.uicorek.refreshk.helpers.RefreshKGestureDetector
import com.mozhimen.uicorek.refreshk.mos.RefreshKStatus
import kotlin.math.abs

/**
 * @ClassName RefreshKLayout
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/18 10:57
 * @Version 1.0
 */
open class RefreshKLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    BaseKLayoutFrame(context, attrs, defStyleAttr), IRefreshK {
    private lateinit var _gestureDetector: GestureDetector
    private lateinit var _autoScroller: AutoScroller

    private var _refreshKListener: IRefreshK.IRefreshKListener? = null
    private var _disableRefreshScroll = false//刷新时是否禁止滚动
    private var _refreshKStatus: RefreshKStatus? = null
    private var _lastY = 0
    protected var refreshKOverView: RefreshKOverView? = null

    init {
        initView()
    }

    final override fun initView() {
        _gestureDetector = GestureDetector(context, object : RefreshKGestureDetector() {
            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
                if (abs(distanceX) > abs(distanceY) || _refreshKListener != null && !_refreshKListener!!.enableRefresh()) {
                    //横向滑动，或刷新被禁止则不处理
                    return false
                }

                if (_disableRefreshScroll && _refreshKStatus == RefreshKStatus.REFRESHING) {
                    //刷新时是否禁止滑动
                    return true
                }

                val head = getChildAt(0)
                val child: View = findScrollableChild(this@RefreshKLayout)
                if (childScrolled(child) || (child is DataKRecyclerView && child.isLoading())) {
                    //如果列表发生了滚动或正在加载则不处理
                    return false
                }

                //没有刷新或没有达到可以刷新的距离，且头部已经划出或下拉
                return if ((_refreshKStatus != RefreshKStatus.REFRESHING || head.bottom <= refreshKOverView!!.minPullRefreshHeight) && (head.bottom > 0 || distanceY <= 0.0f)) {
                    //还在滑动中
                    if (_refreshKStatus != RefreshKStatus.OVERFLOW_RELEASE) {
                        //阻尼计算
                        val speed: Int = if (child.top < refreshKOverView!!.minPullRefreshHeight) {
                            (_lastY / refreshKOverView!!.minDamp).toInt()
                        } else {
                            (_lastY / refreshKOverView!!.maxDamp).toInt()
                        }
                        //如果是正在刷新状态，则不允许在滑动的时候改变状态
                        val bool = moveDown(speed, true)
                        _lastY = (-distanceY).toInt()
                        bool
                    } else {
                        false
                    }
                } else {
                    false
                }
            }
        })
        _autoScroller = AutoScroller()
    }

    /**
     * 设置overview
     * @param overView RefreshKOverView
     */
    override fun setRefreshOverView(overView: RefreshKOverView) {
        if (refreshKOverView != null) {
            removeView(refreshKOverView)
        }
        this.refreshKOverView = overView
    }

    /**
     * 设置下拉参数, 触发才生效
     * @param minPullRefreshHeight Int? 触发下拉刷新需要的最小高度
     * @param minDamp Float? 最小阻尼
     * @param maxDamp Float? 最大阻尼
     */
    override fun setRefreshParams(
        minPullRefreshHeight: Int?,
        minDamp: Float?, maxDamp: Float?
    ) {
        requireNotNull(refreshKOverView) { "refreshKOverView must not be null, set it first!" }
        minPullRefreshHeight?.let { refreshKOverView!!.minPullRefreshHeight = it }
        minDamp?.let { refreshKOverView!!.minDamp = it }
        maxDamp?.let { refreshKOverView!!.maxDamp = it }
        val params = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        addView(refreshKOverView, 0, params)
    }

    /**
     * 是否禁用滚动
     * @param disableRefreshScroll Boolean
     */
    override fun setDisableRefreshScroll(disableRefreshScroll: Boolean) {
        this._disableRefreshScroll = disableRefreshScroll
    }

    /**
     * 设置监听器
     * @param listener RefreshKListener?
     */
    override fun setRefreshListener(listener: IRefreshK.IRefreshKListener) {
        this._refreshKListener = listener
    }

    /**
     * 刷新停止
     */
    override fun refreshFinished() {
        val head = getChildAt(0)
        refreshKOverView?.apply {
            onFinish()
            setStatus(RefreshKStatus.INIT)
        }
        val bottom = head.bottom
        if (bottom > 0) {
            recoverHead(bottom)
        }
        _refreshKStatus = RefreshKStatus.INIT
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        //事件分发处理
        if (!_autoScroller.isFinished()) {
            return false
        }

        val head = getChildAt(0)
        if (ev!!.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_CANCEL || ev.action == MotionEvent.ACTION_POINTER_INDEX_MASK) { //松开手
            if (head.bottom > 0) {
                if (_refreshKStatus != RefreshKStatus.REFRESHING) { //非正在刷新
                    recoverHead(head.bottom)
                    return false
                }
            }
            _lastY = 0
        }
        val consumed: Boolean = _gestureDetector.onTouchEvent(ev)
        if ((consumed || _refreshKStatus != RefreshKStatus.INIT && _refreshKStatus != RefreshKStatus.REFRESHING) && head.bottom != 0) {
            ev.action = MotionEvent.ACTION_CANCEL //让父类接受不到真实的事件
            return super.dispatchTouchEvent(ev)
        }

        return if (consumed) {
            true
        } else {
            super.dispatchTouchEvent(ev)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        //定义head和child的排列位置
        val header = getChildAt(0)
        val child = getChildAt(1)
        if (header != null && child != null) {
            val childTop = child.top
            if (_refreshKStatus == RefreshKStatus.REFRESHING) {
                header.layout(
                    0, refreshKOverView!!.minPullRefreshHeight - header.measuredHeight,
                    right, refreshKOverView!!.minPullRefreshHeight
                )
                child.layout(
                    0, refreshKOverView!!.minPullRefreshHeight,
                    right, refreshKOverView!!.minPullRefreshHeight + child.measuredHeight
                )
            } else {
                //left,top,right,bottom
                header.layout(0, childTop - header.measuredHeight, right, childTop)
                child.layout(0, childTop, right, childTop + child.measuredHeight)
            }
            var other: View
            //让HiRefreshLayout节点下两个以上的child能够不跟随手势移动以实现一些特殊效果，如悬浮的效果
            for (i in 2 until childCount) {
                other = getChildAt(i)
                other.layout(0, top, right, bottom)
            }
        }
    }

    /**
     * 恢复头部
     * @param distance Int 滚动的距离
     */
    private fun recoverHead(distance: Int) {
        require(refreshKOverView != null) { "refreshKOverview must not be null!" }
        if (_refreshKListener != null && distance > refreshKOverView!!.minPullRefreshHeight) {
            //滚动到指定位置distance-kOverView.mPullRefreshHeight
            _autoScroller.recover(distance - refreshKOverView!!.minPullRefreshHeight)
            _refreshKStatus = RefreshKStatus.OVERFLOW_RELEASE
        } else {
            _autoScroller.recover(distance)
        }
    }

    private inner class AutoScroller : Runnable {
        private val _scroller: Scroller = Scroller(context, LinearInterpolator())
        private var _lastY = 0
        private var _isFinished = true

        override fun run() {
            if (_scroller.computeScrollOffset()) {
                //还未滚动完成
                moveDown(_lastY - _scroller.currY, false)
                _lastY = _scroller.currY
                post(this)
            } else {
                removeCallbacks(this)
                _isFinished = true
            }
        }

        fun recover(distance: Int) {
            if (distance <= 0) {
                return
            }
            removeCallbacks(this)
            _lastY = 0
            _isFinished = false
            _scroller.startScroll(0, 0, 0, distance, 500)
            post(this)
        }

        fun isFinished(): Boolean = _isFinished
    }

    /**
     * 根据偏移量移动header与child
     * @param offY Int 偏移量
     * @param isEnableAutoScroll Boolean 是否非自动滚动触发
     * @return Boolean
     */
    private fun moveDown(offY: Int, isEnableAutoScroll: Boolean): Boolean {
        requireNotNull(refreshKOverView) { "refreshKOverView must not be null!" }
        var offsetY = offY
        val head = getChildAt(0)
        val child = getChildAt(1)
        val childTop = child.top + offsetY
        if (childTop <= 0) { //异常情况的补充
            offsetY = -child.top
            //移动head与child的位置到原始位置
            head.offsetTopAndBottom(offsetY)
            child.offsetTopAndBottom(offsetY)
            if (_refreshKStatus != RefreshKStatus.REFRESHING) {
                _refreshKStatus = RefreshKStatus.INIT
            }
        } else if (_refreshKStatus == RefreshKStatus.REFRESHING && childTop > refreshKOverView!!.minPullRefreshHeight) {
            //如果正在下拉刷新中,禁止继续下拉
            return false
        } else if (childTop <= refreshKOverView!!.minPullRefreshHeight) { //还没有超出设定的刷新距离
            if (refreshKOverView!!.getStatus() != RefreshKStatus.VISIBLE && isEnableAutoScroll) { //头部开始显示
                refreshKOverView!!.onVisible()
                refreshKOverView!!.setStatus(RefreshKStatus.VISIBLE)
                _refreshKStatus = RefreshKStatus.VISIBLE
            }
            head.offsetTopAndBottom(offsetY)
            child.offsetTopAndBottom(offsetY)
            if (childTop == refreshKOverView!!.minPullRefreshHeight && _refreshKStatus == RefreshKStatus.OVERFLOW_RELEASE) {
                //开始下拉刷新
                startRefresh()
            }
        } else {
            if (refreshKOverView!!.getStatus() != RefreshKStatus.OVERFLOW && isEnableAutoScroll) {
                //超出刷新位置
                refreshKOverView!!.onOverflow()
                refreshKOverView!!.setStatus(RefreshKStatus.OVERFLOW)
            }
            head.offsetTopAndBottom(offsetY)
            child.offsetTopAndBottom(offsetY)
        }
        if (refreshKOverView != null) {
            refreshKOverView!!.onScroll(head.bottom, refreshKOverView!!.minPullRefreshHeight)
        }
        return true
    }

    /**
     * 开始刷新
     */
    private fun startRefresh() {
        requireNotNull(refreshKOverView) { "refreshKOverView must not be null!" }
        if (_refreshKListener != null) {
            _refreshKStatus = RefreshKStatus.REFRESHING
            refreshKOverView!!.onStartRefresh()
            refreshKOverView!!.setStatus(RefreshKStatus.REFRESHING)
            _refreshKListener!!.onRefresh()
        }
    }
}