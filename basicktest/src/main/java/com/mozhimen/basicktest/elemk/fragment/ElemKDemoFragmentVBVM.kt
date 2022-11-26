package com.mozhimen.basicktest.elemk.fragment

import android.os.Bundle
import com.mozhimen.basick.elemk.fragment.bases.BaseFragmentVBVM
import com.mozhimen.basicktest.databinding.FragmentElemkFragmentVbvmBinding
import com.mozhimen.basicktest.elemk.viewmodel.ElemKDemoViewModel

class ElemKDemoFragmentVBVM : BaseFragmentVBVM<FragmentElemkFragmentVbvmBinding, ElemKDemoViewModel>() {

    override fun bindViewVM(vb: FragmentElemkFragmentVbvmBinding) {
        vb.vm = vm
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        vb.elemkFragmentVbvmBtn.setOnClickListener {
            vm.genNum()
        }
    }
}