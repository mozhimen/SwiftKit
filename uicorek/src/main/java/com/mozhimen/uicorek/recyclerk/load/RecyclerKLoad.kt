package com.mozhimen.uicorek.recyclerk.load

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.utilk.androidx.recyclerview.getRVLastVisibleItemPosition
import com.mozhimen.basick.utilk.androidx.recyclerview.isRVScroll2end
import com.mozhimen.basick.utilk.androidx.recyclerview.isRVScroll2top
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.adapterk.AdapterKRecyclerStuffed
import com.mozhimen.uicorek.layoutk.refresh.helpers.RefreshGestureDetector
import com.mozhimen.uicorek.recyclerk.load.commons.IRecyclerKLoad
import com.mozhimen.uicorek.recyclerk.load.commons.IRecyclerKLoadListener

/**
 * @ClassName DataKRecyclerView
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/22 17:30
 * @Version 1.0
 */
class RecyclerKLoad @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr), IRecyclerKLoad {

    private var _onScrollListener: OnScrollListener? = null
    private var _footerView: View? = null
    private var _isLoading: Boolean = false
    private var _isFooterShowing: Boolean = false
    private var _recyclerKLoadListener: IRecyclerKLoadListener? = null
    private var _prefetchSize: Int = 5
    private val _gestureDetector: GestureDetector by lazy { GestureDetector(context, RecyclerKLoadGestureDetector(_prefetchSize, _recyclerKLoadListener)) }

    override fun setFooterView(layoutId: Int) {
        _footerView = LayoutInflater.from(context).inflate(layoutId, this, false)
    }

    @Throws(Exception::class)
    override fun enableLoad(prefetchSize: Int, listener: IRecyclerKLoadListener?) {
        require(adapter is AdapterKRecyclerStuffed) { "$TAG enableLoad adapter must use dataKAdapter" }

        _prefetchSize = prefetchSize
        _recyclerKLoadListener = listener
        _onScrollListener = RecyclerKLoadScrollCallback()
        addOnScrollListener(_onScrollListener!!)
    }

    @Throws(Exception::class)
    override fun disableLoad() {
        require(adapter is AdapterKRecyclerStuffed) { "$TAG disableLoad adapter must use dataKAdapter" }
        _footerView?.let {
            if (_footerView!!.parent != null) {
                (adapter as AdapterKRecyclerStuffed).removeFooterView(_footerView!!)
            }
        }

        _onScrollListener?.let {
            removeOnScrollListener(_onScrollListener!!)
            _recyclerKLoadListener = null
            _onScrollListener = null
            _footerView = null
            _isFooterShowing = false
            _isLoading = false
        }
    }

    override fun isLoading(): Boolean = _isLoading

    override fun finishLoad() {
        require(adapter is AdapterKRecyclerStuffed) { "$TAG loadFinished must use dataKAdapter" }

        _footerView?.let {
            (adapter as AdapterKRecyclerStuffed).removeFooterView(_footerView!!)
        }
        _isFooterShowing = false
        _isLoading = false
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        //这里放弃了监听onScroll事件而改用监听手势, 因为fix 原有的滚动, 滚动到边缘触发两次的bug
        return if (_gestureDetector.onTouchEvent(ev)) true else super.dispatchTouchEvent(ev)
    }

    inner class RecyclerKLoadGestureDetector(private val _prefetchSize: Int, private val _listener: IRecyclerKLoadListener?) : RefreshGestureDetector() {
        //咱们这里的强转, 因为前面会有前置检查
        private val _adapterKRecyclerStuffed by lazy { adapter as AdapterKRecyclerStuffed }

        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            if (distanceY < 0 && this@RecyclerKLoad.isRVScroll2top()) {
                return true
            } else {
                if (distanceY > 0) {
                    if (_isLoading) return false

                    val totalItemCount = _adapterKRecyclerStuffed.itemCount
                    if (totalItemCount <= 0) return false

                    val lastVisibleItem = this@RecyclerKLoad.getRVLastVisibleItemPosition()

                    //预加载, 就是不需要等待滑动到最后一个item的时候, 就出发下一页的加载动作
                    val arrivePrefetchPosition = totalItemCount - lastVisibleItem <= _prefetchSize
                    if (!arrivePrefetchPosition) return false//不在预加载范围内则返回

                    _listener?.onLoading()
                    _isLoading = true
                }
                return false
            }
        }
    }

    inner class RecyclerKLoadScrollCallback : OnScrollListener() {
        //咱们这里的强转, 因为前面会有前置检查
        private val _adapterKRecyclerStuffed by lazy { adapter as AdapterKRecyclerStuffed }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            //需要根据当前的滑动状态, 已决定要不要添加footerView, 要不要执行上拉加载分页的动作
            if (_isFooterShowing) return

            //咱们需要判断当前类表上已经显示的item的个数, 如果列表上已显示的item的数量小于0, 退出
            val totalItemCount = _adapterKRecyclerStuffed.itemCount
            if (totalItemCount <= 0) return

            //还有一种情况,canScrollVertical 咱们是检查他能不能继续向下滑动,
            //特殊情况, 咱们的列表已经滑动到底部了, 但是分页失败了。
            val lastVisibleItem = recyclerView.getRVLastVisibleItemPosition()
            if (lastVisibleItem <= 0) return

            if (newState == SCROLL_STATE_DRAGGING) {
                //这边分三个情况: 列表没有铺满屏幕, 铺满但firstVisibleItem还是停留在0, firstVisibleItem>0
                //列表不可滑动,但列表没有撑满屏幕,此时lastVisibleItem就等于最后一条item,为了避免这种能情况，还需要加firstVisibleItem!=0
                //可以向下滑动, 或者当前已经滑动到最底下了, 此时在拖动列表, 那也是允许分页的
                if (this@RecyclerKLoad.isRVScroll2end()) {
                    _isFooterShowing = true
                    addFooterView()
                }
            }
        }

        private fun addFooterView() {
            val footerView = getFooterView()
            //但是, 这里有个坑...在一些边界场景下, 会出现多次添加的情况, 添加之前先remove -> dataKAdapter.removeFooterView()
            //主要是为了避免removeFooterView不及时, 在边界场景下可能会出现, footerView还没从recyclerView上移除掉, 但我们又调用了addFooterView
            //造成的重复添加的情况, 此时会抛出 add view must call removeView form it parent first exception
            if (footerView.parent != null) {
                footerView.post {
                    addFooterView()
                }
            } else {
                _adapterKRecyclerStuffed.addFooterView(footerView)
            }
        }

        private fun getFooterView(): View {
            if (_footerView == null) {
                _footerView =
                    LayoutInflater.from(context).inflate(R.layout.recyclerk_load_footer_loading, this@RecyclerKLoad, false)
            }
            return _footerView!!
        }
    }
}