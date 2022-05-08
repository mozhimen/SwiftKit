package com.mozhimen.uicorek.bannerk.helpers

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.PagerAdapter
import com.mozhimen.uicorek.bannerk.commons.IBannerK
import com.mozhimen.uicorek.bannerk.commons.IBannerKBindAdapter
import com.mozhimen.uicorek.bannerk.mos.BannerKMo
import java.util.*

/**
 * @ClassName BannerKAdapter
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/19 14:45
 * @Version 1.0
 */
class BannerKAdapter(private var _context: Context) : PagerAdapter() {
    private var _cachedViews: SparseArray<BannerKViewHolder> = SparseArray<BannerKViewHolder>()
    private var _bannerClickListenerI: IBannerK.IOnBannerClickListener? = null
    private var _bindAdapter: IBannerKBindAdapter? = null
    private var _bannerKMos: LinkedList<BannerKMo>? = null
    private var _autoPlay = true //是否开启自动轮播
    private var _loop = false //非自动轮播状态下是否可以循环切换
    private var _layoutResId = -1

    /**
     * 设置BannerData
     * @param mos List<BannerKMo>
     */
    fun setBannerData(mos: List<BannerKMo>) {
        _bannerKMos = LinkedList(mos)
        initCachedView()//初始化数据
        notifyDataSetChanged()
    }

    /**
     * 设置adapter
     * @param bindAdapter IBannerKBindAdapter
     */
    fun setBindAdapter(bindAdapter: IBannerKBindAdapter) {
        this._bindAdapter = bindAdapter
    }

    /**
     * 设置监听器
     * @param IOnBannerClickListener OnBannerClickListener
     */
    fun setOnBannerClickListener(IOnBannerClickListener: IBannerK.IOnBannerClickListener) {
        this._bannerClickListenerI = IOnBannerClickListener
    }

    /**
     * 设置资源id
     * @param layoutResId Int
     */
    fun setLayoutResId(@LayoutRes layoutResId: Int) {
        this._layoutResId = layoutResId
    }

    /**
     * 设置自动播放
     * @param autoPlay Boolean
     */
    fun setAutoPlay(autoPlay: Boolean) {
        this._autoPlay = autoPlay
    }

    /**
     * 设置循环播放
     * @param loop Boolean
     */
    fun setLoop(loop: Boolean) {
        this._loop = loop
    }

    /**
     * 获取真实的item数
     * @return Int
     */
    fun getRealCount(): Int = _bannerKMos?.size ?: 0

    /**
     * 获取初次展示的item位置
     * @return Int
     */
    fun getFirstItem(): Int {
        //这里是为了配合instantiateItem方法中realPosition = position % getRealCount();
        // - (Integer.MAX_VALUE / 2) % getRealCount()的主要目的是用于获取realPosition=0的位置
        return Int.MAX_VALUE / 2 - Int.MAX_VALUE / 2 % getRealCount()
    }

    /**
     * 无限轮播关键点
     * @return Int
     */
    override fun getCount(): Int = if (_autoPlay) Int.MAX_VALUE else (if (_loop) Int.MAX_VALUE else getRealCount())

    /**
     * 类型匹配
     * @param view View
     * @param obj Any
     * @return Boolean
     */
    override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj

    /**
     * 初始化布局
     * @param container ViewGroup
     * @param position Int
     * @return Any
     */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        requireNotNull(_bannerKMos) { "_bannerKMos must not be null!" }
        var realPosition = position
        if (getRealCount() > 0) {
            realPosition = position % getRealCount()
        }
        val viewHolder: BannerKViewHolder = _cachedViews.get(realPosition)
        if (container == viewHolder.rootView.parent) {
            container.removeView(viewHolder.rootView)
        }
        //数据绑定
        onBind(viewHolder, _bannerKMos!![realPosition], realPosition)
        if (viewHolder.rootView.parent != null) {
            (viewHolder.rootView.parent as ViewGroup).removeView(viewHolder.rootView)
        }
        container.addView(viewHolder.rootView)
        return viewHolder.rootView
    }

    /**
     * 删除item
     * @param container ViewGroup
     * @param position Int
     * @param `object` Any
     */
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {}

    protected fun onBind(viewHolder: BannerKViewHolder, bannerKMo: BannerKMo, position: Int) {
        viewHolder.rootView.setOnClickListener {
            _bannerClickListenerI?.onBannerClick(viewHolder, bannerKMo, position)
        }
        _bindAdapter?.onBind(viewHolder, bannerKMo, position)
    }

    class BannerKViewHolder(var rootView: View) {
        private var _viewSparseArray: SparseArray<View>? = null

        fun <V : View> findViewById(id: Int): V {
            if (rootView !is ViewGroup) {
                return rootView as V
            }
            if (_viewSparseArray == null) {
                _viewSparseArray = SparseArray(1)
            }
            var childView = _viewSparseArray!![id] as V?
            if (childView == null) {
                childView = rootView.findViewById(id)
                _viewSparseArray!!.put(id, childView)
            }
            return childView!!
        }
    }

    private fun initCachedView() {
        requireNotNull(_bannerKMos) { "_bannerKMos must not be null!" }
        _cachedViews = SparseArray<BannerKViewHolder>()
        for (i in _bannerKMos!!.indices) {
            val viewHolder = BannerKViewHolder(createView(LayoutInflater.from(_context), null))
            _cachedViews.put(i, viewHolder)
        }
    }

    private fun createView(layoutInflater: LayoutInflater, parent: ViewGroup?): View {
        require(_layoutResId != -1) { "you must be set setLayoutResId first" }
        return layoutInflater.inflate(_layoutResId, parent, false)
    }
}