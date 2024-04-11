package com.mozhimen.basick.elemk.androidx.fragment.bases.databinding

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.mozhimen.basick.elemk.androidx.appcompat.commons.IActivity
import com.mozhimen.basick.elemk.androidx.databinding.commons.IViewDataBinding
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseViewModel
import com.mozhimen.basick.utilk.androidx.lifecycle.UtilKViewModel


/**
 * @ClassName BaseDialogFragmentVBVM
 * @Description 这里的VM1是和Activity共享的VM,私有VM2
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
abstract class BaseDialogFragmentVDBVMVM<VB : ViewDataBinding, VM1 : BaseViewModel, VM2 : BaseViewModel> : BaseDialogFragmentVDB<VB>, IActivity, IViewDataBinding<VB> {

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
        vmSelf = UtilKViewModel.get(this, _factorySelf/*, 1*/)
        bindViewVM(vdb)
    }
}