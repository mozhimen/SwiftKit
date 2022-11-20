package com.mozhimen.basick.elemk.fragment.commons

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.mozhimen.basick.elemk.activity.commons.IActivity
import com.mozhimen.basick.elemk.viewdatabinding.commons.IViewDataBinding
import com.mozhimen.basick.elemk.viewmodel.commons.BaseViewModel
import com.mozhimen.basick.utilk.UtilKViewModel

/**
 * @ClassName BaseKFragment
 * @Description class BaseKDemoFragment : BaseKFragment<FragmentBasekFragmentBinding, BaseKDemoViewModel>() {
 * override fun assignVM() {vb.vm = vm}
 * override fun initView() {}}
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 13:02
 * @Version 1.0
 */
abstract class BaseFragmentVBVM<VB : ViewDataBinding, VM : BaseViewModel>(
    private val _factory: ViewModelProvider.Factory? = null
) : BaseFragmentVB<VB>(_factory), IActivity, IViewDataBinding<VB> {

    protected lateinit var vm: VM

    @CallSuper
    override fun initLayout() {
        super.initLayout()
        vm = UtilKViewModel.get(this.requireActivity(), _factory, 1)
        bindViewVM(vb)
    }
}