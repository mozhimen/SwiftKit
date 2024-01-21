package com.mozhimen.componentk.pagingk.bases

import android.util.Log
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.utilk.android.view.applyGone
import com.mozhimen.basick.utilk.android.view.applyVisible
import com.mozhimen.componentk.pagingk.commons.IPagingKActivity
import com.mozhimen.componentk.pagingk.cons.CPagingKLoadingState

/**
 * @ClassName BasePagingKActivityVBVM
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/26 22:51
 * @Version 1.0
 */
abstract class BasePagingKActivityVBVM<DES : Any, VB : ViewDataBinding, VM : BasePagingKViewModel<*, DES>> : BaseActivityVB<VB>(), IPagingKActivity<DES, VM> {

    private val _pagedListObserver: Observer<PagedList<DES>> by lazy {
        Observer<PagedList<DES>> { pagedList ->
            Log.d(TAG, "_pagedListObserver_: pagedList $pagedList")
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
            adapter = getPagedListAdapter()
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
        getViewModel().livePagedList.observe(this, _pagedListObserver)
    }
}