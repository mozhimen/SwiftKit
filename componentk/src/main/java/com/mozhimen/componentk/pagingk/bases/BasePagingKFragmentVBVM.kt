package com.mozhimen.componentk.pagingk.bases

import android.util.Log
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.paging.PagedList
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
abstract class BasePagingKFragmentVBVM<DES, VB : ViewDataBinding, VM : BasePagingKViewModel<*, DES>> : BaseFragmentVB<VB>(), IPagingKActivity<DES, VM> {

//    protected val _vm_: VM by lazy { getViewModel() }
//    protected val _pagedListAdapter_: PagedListAdapter<DES, *> by lazy { getPagedListAdapter() }

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
        getSwipeRefreshLayout().apply {
            if (getSwipeRefreshLayoutColorScheme() != 0) {
                setColorSchemeResources(getSwipeRefreshLayoutColorScheme())
            }
            setOnRefreshListener { getViewModel().onInvalidate() }
        }
        getRecyclerView().apply {
            layoutManager = getRecyclerViewLayoutManager()
            adapter = getPagedListAdapter()
        }
        getViewModel().liveLoadState.observe(this) {
            if (it == CPagingKLoadingState.STATE_FIRST_LOAD_START) {
                getSwipeRefreshLayout().isRefreshing = true
                onLoadStart()
            } else
                getSwipeRefreshLayout().isRefreshing = false
            if (it == CPagingKLoadingState.STATE_FIRST_LOAD_EMPTY) {
                getRecyclerView().applyGone()
                onLoadEmpty()
            } else {
                getRecyclerView().applyVisible()
                onLoadComplete()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getViewModel().livePagedList.observe(this,_pagedListObserver) /*{ pagedList -> getPagedListAdapter().submitList(pagedList) }*/
    }
}