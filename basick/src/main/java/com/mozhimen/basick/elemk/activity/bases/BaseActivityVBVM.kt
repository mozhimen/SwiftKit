package com.mozhimen.basick.elemk.activity.bases

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mozhimen.basick.elemk.viewdatabinding.commons.IViewDataBinding
import com.mozhimen.basick.utilk.jetpack.lifecycle.UtilKViewModel

/**
 * @ClassName BaseActivity
 * @Description
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/1/25 18:47
 * @Version 1.0
 */
abstract class BaseActivityVBVM<VB : ViewDataBinding, VM : ViewModel>(
    private val _factory: ViewModelProvider.Factory? = null
) : BaseActivityVB<VB>(_factory), IViewDataBinding<VB> {

    protected lateinit var vm: VM

    @CallSuper
    override fun initLayout() {
        super.initLayout()
        vm = UtilKViewModel.get(this, _factory, 1)
        bindViewVM(vb)
    }
}