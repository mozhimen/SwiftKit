package com.mozhimen.basicktest.elemk.fragment

import android.os.Bundle
import com.mozhimen.basick.elemk.fragment.bases.BaseFragmentVBVM
import com.mozhimen.basicktest.databinding.FragmentElemkFragmentVbvmBinding
import com.mozhimen.basicktest.elemk.viewmodel.ElemKViewModel

class ElemKFragmentVBVM : BaseFragmentVBVM<FragmentElemkFragmentVbvmBinding, ElemKViewModel>() {

    override fun bindViewVM(vb: FragmentElemkFragmentVbvmBinding) {
        vb.vm = vm
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        VB.elemkFragmentVbvmBtn.setOnClickListener {
            vm.genNum()
        }
    }
}