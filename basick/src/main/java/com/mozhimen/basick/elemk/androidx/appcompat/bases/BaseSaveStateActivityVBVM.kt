package com.mozhimen.basick.elemk.androidx.appcompat.bases

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mozhimen.basick.elemk.androidx.databinding.commons.IViewDataBinding
import com.mozhimen.basick.utilk.androidx.lifecycle.UtilKViewModel

/**
 * @ClassName BaseSaveStateActivityVBVM
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/26 15:18
 * @Version 1.0
 */
abstract class BaseSaveStateActivityVBVM<VB : ViewDataBinding, VM : ViewModel> : BaseSaveStateActivityVB<VB>, IViewDataBinding<VB> {
    /**
     * 针对Hilt(@JvmOverloads kotlin默认参数值无效)
     * @constructor
     */
    constructor() : this(null)

    constructor(factory: ViewModelProvider.Factory?) : super(factory)

    //////////////////////////////////////////////////////////////////////////////

    protected lateinit var vm: VM

    //////////////////////////////////////////////////////////////////////////////

    @CallSuper
    override fun initLayout() {
        super.initLayout()
        vm = UtilKViewModel.get(this, _factory, 1)
        bindViewVM(vb)
    }
}