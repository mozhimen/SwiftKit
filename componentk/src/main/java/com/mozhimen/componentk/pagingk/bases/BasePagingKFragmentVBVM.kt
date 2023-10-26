package com.mozhimen.componentk.pagingk.bases

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseFragmentVB
import com.mozhimen.basick.utilk.android.view.applyGone
import com.mozhimen.basick.utilk.android.view.applyInVisible
import com.mozhimen.componentk.pagingk.commons.IPagingKFragment
import com.mozhimen.componentk.pagingk.cons.CPagingKLoadingState

/**
 * @ClassName BasePagingKFragmentVBVM
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/16 15:05
 * @Version 1.0
 */
abstract class BasePagingKFragmentVBVM<DES, VB : ViewDataBinding, VM : BasePagingKViewModel<*, DES>> : BaseFragmentVB<VB>(), IPagingKFragment<DES, VM> {

//    protected val _vm_: VM by lazy { getViewModel() }
//
//    protected val _pagedListAdapter_: PagedListAdapter<DES, *> by lazy { getPagedListAdapter() }

    protected val _pagedListObserver_: Observer<PagedList<DES>> by lazy { Observer<PagedList<DES>> { pagedList -> getPagedListAdapter().submitList(pagedList) } }

    //////////////////////////////////////////////////////////////////////////////

    @CallSuper
    override fun initLayout() {
        super.initLayout()
        getSwipeRefreshLayout().apply {
            setColorSchemeResources(getSwipeRefreshLayoutColorScheme())
            setOnRefreshListener { getViewModel().onInvalidate() }
        }
        getRecyclerView().apply {
            layoutManager = getRecyclerViewLayoutManager()
            adapter = getPagedListAdapter()
        }
        getViewModel().liveLoadState.observe(this) {
            if (it != CPagingKLoadingState.STATE_FIRST_LOAD_START) {
                getSwipeRefreshLayout().isRefreshing = false
                onLoadStart()
            } else
                getSwipeRefreshLayout().isRefreshing = true
            if (it == CPagingKLoadingState.STATE_FIRST_LOAD_EMPTY) {
                getRecyclerView().applyGone()
                onLoadEmpty()
            } else {
                getRecyclerView().applyInVisible()
                onLoadComplete()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getViewModel().livePagedList.observe(this, _pagedListObserver_)
    }
}