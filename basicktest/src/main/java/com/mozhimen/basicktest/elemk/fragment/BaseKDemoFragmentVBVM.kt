package com.mozhimen.basicktest.elemk.fragment

import android.os.Bundle
import com.mozhimen.basick.elemk.fragment.commons.BaseFragmentVBVM
import com.mozhimen.basicktest.databinding.FragmentElemkFragmentVbvmBinding
import com.mozhimen.basicktest.elemk.viewmodel.ElemKDemoViewModel

class BaseKDemoFragmentVBVM : BaseFragmentVBVM<FragmentElemkFragmentVbvmBinding, ElemKDemoViewModel>() {

    override fun bindViewVM(vb: FragmentElemkFragmentVbvmBinding) {
        vb.vm = vm
    }

    override fun initData(savedInstanceState: Bundle?) {
        vb.elemkFragmentVbvmBtn.setOnClickListener {
            vm.genNum()
        }
    }
}