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
 * @Description 这里的VM是和Activity共享的VM,私有可以通过代理的方式引入
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
abstract class BaseDialogFragmentVDBVMSelf<VB : ViewDataBinding, VM : BaseViewModel> : BaseDialogFragmentVDB<VB>, IActivity, IViewDataBinding<VB> {
    /**
     * 针对Hilt(@JvmOverloads kotlin默认参数值无效)
     * @constructor
     */
    constructor() : super()

    //////////////////////////////////////////////////////////////////////////////

    protected lateinit var vm: VM

    //////////////////////////////////////////////////////////////////////////////

    @CallSuper
    override fun initLayout() {
        super.initLayout()
        vm = UtilKViewModel.get(this, getViewModelProviderFactory()/*, 1*/)
        bindViewVM(vdb)
    }
}