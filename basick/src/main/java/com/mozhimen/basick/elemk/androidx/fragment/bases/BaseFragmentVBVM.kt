package com.mozhimen.basick.elemk.androidx.fragment.bases

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.mozhimen.basick.elemk.androidx.appcompat.commons.IActivity
import com.mozhimen.basick.elemk.androidx.databinding.commons.IViewDataBinding
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseViewModel
import com.mozhimen.basick.utilk.androidx.lifecycle.UtilKViewModel

/**
 * @ClassName BaseFragmentVBVM
 * @Description class BaseDemoFragment : BaseFragment<FragmentBasekFragmentBinding, BaseDemoViewModel>() {
 * override fun assignVM() {vb.vm = vm}
 * override fun initView() {}}
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 13:02
 * @Version 1.0
 */
abstract class BaseFragmentVBVM<VB : ViewDataBinding, VM : BaseViewModel> : BaseFragmentVB<VB>, IActivity, IViewDataBinding<VB> {

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
        vm = UtilKViewModel.get(this.requireActivity(), _factory, 1)
        bindViewVM(vb)
    }
}