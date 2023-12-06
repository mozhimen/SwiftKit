package com.mozhimen.uicorek.layoutk.banner.helpers

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.PagerAdapter
import com.mozhimen.uicorek.layoutk.banner.commons.IBannerAdapter
import com.mozhimen.uicorek.layoutk.banner.commons.IBannerBindListener
import com.mozhimen.uicorek.layoutk.banner.commons.IBannerItemClickListener
import com.mozhimen.uicorek.layoutk.banner.bases.BaseBannerItem
import java.util.*

/**
 * @ClassName BannerAdapter
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/19 14:45
 * @Version 1.0
 */
class BannerAdapter(private var _context: Context) : PagerAdapter(), IBannerAdapter {
    private val TAG = "BannerAdapter>>>>>"
    private var _cachedViews: SparseArray<BannerViewHolder> = SparseArray<BannerViewHolder>()
    private var _bannerClickListener: IBannerItemClickListener? = null
    private var _bannerBindListener: IBannerBindListener? = null
    private var _bannerItems: LinkedList<BaseBannerItem>? = null
    private var _autoPlay = true //是否开启自动轮播
    private var _loop = false //非自动轮播状态下是否可以循环切换
    private var _layoutId = -1

    override fun setBannerData(mos: List<BaseBannerItem>) {
        _bannerItems = LinkedList(mos)
        initCachedView()//初始化数据
        notifyDataSetChanged()
    }

    override fun setBannerBindListener(listener: IBannerBindListener) {
        _bannerBindListener = listener
    }

    override fun setBannerClickListener(listener: IBannerItemClickListener) {
        _bannerClickListener = listener
    }

    override fun setLayoutResId(@LayoutRes layoutId: Int) {
        _layoutId = layoutId
    }

    override fun setAutoPlay(autoPlay: Boolean) {
        _autoPlay = autoPlay
    }

    override fun setLoop(loop: Boolean) {
        _loop = loop
    }

    override fun getRealCount(): Int =
        _bannerItems?.size ?: 0

    override fun getFirstItem(): Int {
        //这里是为了配合instantiateItem方法中realPosition = position % getRealCount();
        // - (Integer.MAX_VALUE / 2) % getRealCount()的主要目的是用于获取realPosition=0的位置
        return Int.MAX_VALUE / 2 - Int.MAX_VALUE / 2 % getRealCount()
    }

    /**
     * 无限轮播关键点
     * @return Int
     */
    override fun getCount(): Int =
        if (_autoPlay) Int.MAX_VALUE else (if (_loop) Int.MAX_VALUE else getRealCount())

    /**
     * 类型匹配
     * @param view View
     * @param obj Any
     * @return Boolean
     */
    override fun isViewFromObject(view: View, obj: Any): Boolean =
        view == obj

    /**
     * 初始化布局
     * @param container ViewGroup
     * @param position Int
     * @return Any
     */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        requireNotNull(_bannerItems) { "$TAG _bannerItems must not be null!" }
        var realPosition = position
        if (getRealCount() > 0) {
            realPosition = position % getRealCount()
        }
        val viewHolder: BannerViewHolder = _cachedViews.get(realPosition)
        if (container == viewHolder.rootView.parent) {
            container.removeView(viewHolder.rootView)
        }
        //数据绑定
        onBind(viewHolder, _bannerItems!![realPosition], realPosition)
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

    private fun onBind(viewHolder: BannerViewHolder, item: BaseBannerItem, position: Int) {
        viewHolder.rootView.setOnClickListener {
            _bannerClickListener?.onBannerClick(viewHolder, item, position)
        }
        _bannerBindListener?.onBannerBind(viewHolder, item, position)
    }

    @Throws(Exception::class)
    private fun initCachedView() {
        requireNotNull(_bannerItems) { "$TAG _bannerItems must not be null!" }
        _cachedViews = SparseArray<BannerViewHolder>()
        for (i in _bannerItems!!.indices) {
            val viewHolder = BannerViewHolder(createView(LayoutInflater.from(_context), null))
            _cachedViews.put(i, viewHolder)
        }
    }

    @Throws(Exception::class)
    private fun createView(layoutInflater: LayoutInflater, parent: ViewGroup?): View {
        require(_layoutId != -1) { "$TAG you must be set setLayoutResId first" }
        return layoutInflater.inflate(_layoutId, parent, false)
    }
}