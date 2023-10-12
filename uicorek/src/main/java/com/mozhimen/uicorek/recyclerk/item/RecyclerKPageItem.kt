package com.mozhimen.uicorek.recyclerk.item

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.mozhimen.basick.utilk.android.view.findViewByInflate
import com.mozhimen.uicorek.adapterk.page.AdapterKPageRecyclerMulti
import com.mozhimen.uicorek.vhk.VHKRecycler
import java.lang.ref.WeakReference

/**
 * @ClassName RecyckerKItemPage
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 11:52
 * @Version 1.0
 */
abstract class RecyclerKPageItem<DATA> {

    lateinit var context: Context

    private var _adapterKPageRecyclerMultiRef: WeakReference<AdapterKPageRecyclerMulti<DATA>>? = null
    private val _viewIdsClick by lazy(LazyThreadSafetyMode.NONE) { ArrayList<Int>() }
    private val _viewIdsLongClick by lazy(LazyThreadSafetyMode.NONE) { ArrayList<Int>() }

    ///////////////////////////////////////////////////////////////////////

    abstract val itemViewType: Int

    abstract val layoutId: Int
        @LayoutRes
        get

    abstract fun convert(holder: VHKRecycler, item: DATA?)

    ///////////////////////////////////////////////////////////////////////

    fun getAdapter(): AdapterKPageRecyclerMulti<DATA>? {
        return _adapterKPageRecyclerMultiRef?.get()
    }

    fun getChildClickViewIds() = this._viewIdsClick

    fun getChildLongClickViewIds() = this._viewIdsLongClick

    ///////////////////////////////////////////////////////////////////////

    internal fun setAdapter(adapter: AdapterKPageRecyclerMulti<DATA>) {
        _adapterKPageRecyclerMultiRef = WeakReference(adapter)
    }

    fun addChildClickViewIds(@IdRes vararg viewId: Int) {
        viewId.forEach {
            _viewIdsClick.add(it)
        }
    }

    fun addChildLongClickViewIds(@IdRes vararg viewId: Int) {
        viewId.forEach {
            _viewIdsLongClick.add(it)
        }
    }

    open fun convert(helper: VHKRecycler, item: DATA, payloads: List<Any>) {}

    /**
     * （可选重写）创建 ViewHolder。
     * 默认实现返回[BaseViewHolder]，可重写返回自定义 ViewHolder
     */
    open fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHKRecycler {
        return VHKRecycler(parent.findViewByInflate(layoutId))
    }

    /**
     * （可选重写）ViewHolder创建完毕以后的回掉方法。
     */
    open fun onViewHolderCreated(holder: VHKRecycler, viewType: Int) {}

    /**
     * Called when a view created by this [BaseItemProvider] has been attached to a window.
     * 当此[BaseItemProvider]出现在屏幕上的时候，会调用此方法
     *
     * This can be used as a reasonable signal that the view is about to be seen
     * by the user. If the [BaseItemProvider] previously freed any resources in
     * [onViewDetachedFromWindow][.onViewDetachedFromWindow]
     * those resources should be restored here.
     *
     * @param holder Holder of the view being attached
     */
    open fun onViewAttachedToWindow(holder: VHKRecycler) {}

    /**
     * Called when a view created by this [BaseItemProvider] has been detached from its
     * window.
     * 当此[BaseItemProvider]从屏幕上移除的时候，会调用此方法
     *
     * Becoming detached from the window is not necessarily a permanent condition;
     * the consumer of an Adapter's views may choose to cache views offscreen while they
     * are not visible, attaching and detaching them as appropriate.
     *
     * @param holder Holder of the view being detached
     */
    open fun onViewDetachedFromWindow(holder: VHKRecycler) {}

    /**
     * item 若想实现条目点击事件则重写该方法
     * @param helper VH
     * @param data T
     * @param position Int
     */
    open fun onClick(holder: VHKRecycler, view: View, data: DATA?, position: Int) {}

    /**
     * item 若想实现条目长按事件则重写该方法
     * @param helper VH
     * @param data T
     * @param position Int
     * @return Boolean
     */
    open fun onLongClick(holder: VHKRecycler, view: View, data: DATA, position: Int): Boolean {
        return false
    }

    open fun onChildClick(holder: VHKRecycler, view: View, data: DATA?, position: Int) {}

    open fun onChildLongClick(holder: VHKRecycler, view: View, data: DATA, position: Int): Boolean {
        return false
    }
}