package com.mozhimen.mvvmk.bases.activity.databinding

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.mozhimen.mvvmk.commons.IViewDataBinding
import com.mozhimen.kotlin.utilk.androidx.lifecycle.UtilKViewModel

/**
 * @ClassName BaseSaveStateActivityVBVM
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
abstract class BaseSaveStateActivityVDBVM<VB : ViewDataBinding, VM : ViewModel> : BaseSaveStateActivityVDB<VB>, IViewDataBinding<VB> {

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