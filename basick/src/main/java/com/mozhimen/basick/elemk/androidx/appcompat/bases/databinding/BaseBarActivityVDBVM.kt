package com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mozhimen.basick.elemk.androidx.databinding.commons.IViewDataBinding
import com.mozhimen.basick.utilk.androidx.lifecycle.UtilKViewModel

/**
 * @ClassName BaseBarActivityVBVM
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/24 15:40
 * @Version 1.0
 */
abstract class BaseBarActivityVDBVM<VDB : ViewDataBinding, VM : ViewModel> : BaseBarActivityVDB<VDB>, IViewDataBinding<VDB> {

    protected var _factory: ViewModelProvider.Factory?

    /**
     * 针对Hilt(@JvmOverloads kotlin默认参数值无效)
     * @constructor
     */
    constructor() : this(null)

    constructor(factory: ViewModelProvider.Factory?) : super(){
        _factory = factory
    }

    //////////////////////////////////////////////////////////////////////////////

    protected lateinit var vm: VM

    //////////////////////////////////////////////////////////////////////////////

    @CallSuper
    override fun initLayout() {
        super.initLayout()
        vm = UtilKViewModel.get(this, _factory/*, 1*/)
        bindViewVM(vdb)
    }
}