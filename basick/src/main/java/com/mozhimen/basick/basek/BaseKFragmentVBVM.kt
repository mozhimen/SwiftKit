package com.mozhimen.basick.basek

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mozhimen.basick.basek.commons.IBaseKActivity
import com.mozhimen.basick.basek.commons.IBaseKViewDataBinding
import com.mozhimen.basick.utilk.UtilKViewDataBinding
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
abstract class BaseKFragmentVBVM<VB : ViewDataBinding, VM : BaseKViewModel>(
    private val _factory: ViewModelProvider.Factory? = null
) : BaseKFragmentVB<VB>(_factory), IBaseKActivity, IBaseKViewDataBinding<VB> {

    protected lateinit var vm: VM

    override fun initLayout() {
        super.initLayout()
        vm = UtilKViewModel.get(this.requireActivity(), _factory, 1)
        vb.bindViewVM()
    }
}