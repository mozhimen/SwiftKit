package com.mozhimen.basick.elemk.fragment.bases

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.mozhimen.basick.elemk.activity.commons.IActivity
import com.mozhimen.basick.elemk.viewdatabinding.commons.IViewDataBinding
import com.mozhimen.basick.elemk.viewmodel.bases.BaseViewModel
import com.mozhimen.basick.utilk.jetpack.lifecycle.UtilKViewModel


/**
 * @ClassName BaseDialogFragmentVBVM
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/17 13:06
 * @Version 1.0
 */
abstract class BaseDialogFragmentVBVM<VB : ViewDataBinding, VM : BaseViewModel>(
    override val _factory: ViewModelProvider.Factory? = null
) : BaseDialogFragmentVB<VB>(_factory), IActivity, IViewDataBinding<VB> {

    protected lateinit var vm: VM

    @CallSuper
    override fun initLayout() {
        super.initLayout()
        vm = UtilKViewModel.get(this.requireActivity(), _factory, 1)
        bindViewVM(VB)
    }
}