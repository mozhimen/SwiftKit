package com.mozhimen.basicktest.elemk.androidx

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseFragmentVBVM
import com.mozhimen.basicktest.databinding.FragmentElemkFragmentVbvmBinding

class ElemKFragmentVBVM : BaseFragmentVBVM<FragmentElemkFragmentVbvmBinding, ElemKViewModel>() {

    override fun bindViewVM(vb: FragmentElemkFragmentVbvmBinding) {
        vb.vm = vm
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        vb.elemkFragmentVbvmBtn.setOnClickListener {
            vm.addNum()
        }
    }
}