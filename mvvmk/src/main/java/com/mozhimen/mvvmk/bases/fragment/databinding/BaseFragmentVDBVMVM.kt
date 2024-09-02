package com.mozhimen.mvvmk.bases.fragment.databinding

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.mozhimen.kotlin.elemk.androidx.appcompat.commons.IActivity
import com.mozhimen.mvvmk.commons.IViewDataBinding
import com.mozhimen.kotlin.elemk.androidx.lifecycle.bases.BaseViewModel
import com.mozhimen.kotlin.utilk.androidx.lifecycle.UtilKViewModel

/**
 * @ClassName BaseFragmentVBVM
 * @Description class BaseDemoFragment : BaseFragment<FragmentBasekFragmentBinding, BaseDemoViewModel>() {
 * override fun assignVM() {vdb.vm = vm}
 * override fun initView() {}}
 *
 * 这里的VM1是和Activity共享的VM,私有VM2
 *
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
abstract class BaseFragmentVDBVMVM<VB : ViewDataBinding, VM1 : BaseViewModel, VM2 : BaseViewModel> : BaseFragmentVDB<VB>, IActivity, IViewDataBinding<VB> {

    protected var _factoryShare: ViewModelProvider.Factory?
    protected var _factorySelf: ViewModelProvider.Factory?

    /**
     * 针对Hilt(@JvmOverloads kotlin默认参数值无效)
     * @constructor
     */
    constructor() : this(null)

    constructor(factoryShare: ViewModelProvider.Factory?) : this(factoryShare, null)

    constructor(factoryShare: ViewModelProvider.Factory?, factorySelf: ViewModelProvider.Factory?) : super() {
        _factoryShare = factoryShare
        _factorySelf = factorySelf
    }

    //////////////////////////////////////////////////////////////////////////////

    protected lateinit var vmShare: VM1
    protected lateinit var vmSelf: VM2

    //////////////////////////////////////////////////////////////////////////////

    @CallSuper
    override fun initLayout() {
        super.initLayout()
        vmShare = UtilKViewModel.get(this.requireActivity(), _factoryShare/*, 1*/)
        vmSelf = UtilKViewModel.get(this.requireActivity(), _factorySelf/*, 1*/)
        bindViewVM(vdb)
    }
}