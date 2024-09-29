package com.mozhimen.bindk.bases.activity.viewbinding

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.mozhimen.bindk.commons.IViewBinding
import com.mozhimen.kotlin.utilk.androidx.lifecycle.UtilKViewModel

/**
 * @ClassName BaseActivity
 * @Description
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/1/25 18:47
 * @Version 1.0
 */
abstract class BaseActivityVBVM<VB : ViewBinding, VM : ViewModel> : BaseActivityVB<VB>, IViewBinding<VB> {

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
    }
}