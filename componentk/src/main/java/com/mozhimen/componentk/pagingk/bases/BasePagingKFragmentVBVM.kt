package com.mozhimen.componentk.pagingk.bases

import android.content.Context
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import com.mozhimen.basick.elemk.androidx.appcompat.commons.IActivity
import com.mozhimen.basick.elemk.androidx.databinding.commons.IViewDataBinding
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseFragmentVB
import com.mozhimen.basick.utilk.androidx.lifecycle.UtilKViewModel

/**
 * @ClassName BasePagingKFragmentVBVM
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/16 15:05
 * @Version 1.0
 */
abstract class BasePagingKFragmentVBVM<DES, VB : ViewDataBinding, VM : BasePagingKViewModel<*, DES>> : BaseFragmentVB<VB>, IActivity, IViewDataBinding<VB> {
    protected var _factory: ViewModelProvider.Factory?

    /**
     * 针对Hilt(@JvmOverloads kotlin默认参数值无效)
     * @constructor
     */
    constructor() : this(null)

    constructor(factory: ViewModelProvider.Factory?) : super() {
        _factory = factory
    }

    //////////////////////////////////////////////////////////////////////////////

    protected lateinit var vm: VM

    private val _pagedListAdapter: PagedListAdapter<DES, *> by lazy { getPagedListAdapter(requireContext()) }

    private val _observer: Observer<PagedList<DES>> by lazy { Observer<PagedList<DES>> { pagedList -> _pagedListAdapter.submitList(pagedList) } }

    //////////////////////////////////////////////////////////////////////////////

    @CallSuper
    override fun initLayout() {
        super.initLayout()
        vm = UtilKViewModel.get(this.requireActivity(), _factory, 1)
        bindViewVM(vb)
    }

    override fun onResume() {
        super.onResume()
        vm.livePagedList.observe(this, _observer)
    }

    //////////////////////////////////////////////////////////////////////////////

    abstract fun getPagedListAdapter(context: Context): PagedListAdapter<DES, *>
}