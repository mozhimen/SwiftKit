package com.mozhimen.componentk.pagingk.bases

import android.util.Log
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseFragmentVB
import com.mozhimen.basick.utilk.android.view.applyGone
import com.mozhimen.basick.utilk.android.view.applyInVisible
import com.mozhimen.basick.utilk.android.view.applyVisible
import com.mozhimen.componentk.pagingk.commons.IPagingKActivity
import com.mozhimen.componentk.pagingk.cons.CPagingKLoadingState

/**
 * @ClassName BasePagingKFragmentVBVM
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/16 15:05
 * @Version 1.0
 */
abstract class BasePagingKFragmentVBVM<DES : Any, VB : ViewDataBinding, VM : BasePagingKViewModel<*, DES>> : BaseFragmentVB<VB>(), IPagingKActivity<DES, VM> {

    private val _pagedListObserver: Observer<PagedList<DES>> by lazy {
        Observer<PagedList<DES>> { pagedList ->
            Log.d(TAG, "_pagedListObserver: $pagedList")
            getPagedListAdapter().submitList(pagedList)
        }
    }

    //////////////////////////////////////////////////////////////////////////////

    @CallSuper
    override fun initLayout() {
        super.initLayout()
        getSwipeRefreshLayout()?.apply {
            if (getSwipeRefreshLayoutColorScheme() != 0)
                setColorSchemeResources(getSwipeRefreshLayoutColorScheme())
            setOnRefreshListener { getViewModel().onInvalidate() }
        }
        getRecyclerView().apply {
            layoutManager = getRecyclerViewLayoutManager()
            getRecyclerViewItemDecoration()?.let { addItemDecoration(it) }
            adapter = if (getLoadStateAdapter() != null)
                getPagedListAdapter()/*.withLoadStateFooter(getLoadStateAdapter()!!)*/
            else
                getPagedListAdapter()
        }
        getViewModel().liveLoadState.observe(this) {
            if (it == CPagingKLoadingState.STATE_FIRST_LOAD_START) {
                getSwipeRefreshLayout()?.isRefreshing = true
                onLoadStart()
            } else
                getSwipeRefreshLayout()?.isRefreshing = false
            if (it == CPagingKLoadingState.STATE_FIRST_LOAD_EMPTY) {
                onLoadEmpty()
            } else {
                onLoadComplete()
            }
        }
    }

    @CallSuper
    override fun onLoadEmpty() {
        getRecyclerView().applyGone()
    }

    @CallSuper
    override fun onLoadComplete() {
        getRecyclerView().applyVisible()
    }

    override fun onResume() {
        super.onResume()
        //由于注册观察者时会立即触发，所以在onResume中,防止在ViewPager2中切换时立刻触发
        getViewModel().livePagedList.observe(this, _pagedListObserver) /*{ pagedList -> getPagedListAdapter().submitList(pagedList) }*/
    }
}