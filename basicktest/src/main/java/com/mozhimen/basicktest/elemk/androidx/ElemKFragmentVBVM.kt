package com.mozhimen.basicktest.elemk.androidx

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.fragment.bases.databinding.BaseFragmentVDBVM
import com.mozhimen.basicktest.databinding.FragmentElemkFragmentVbvmBinding

class ElemKFragmentVBVM : BaseFragmentVDBVM<FragmentElemkFragmentVbvmBinding, ElemKViewModel>() {

    override fun bindViewVM(vb: FragmentElemkFragmentVbvmBinding) {
        vdb.vm = vm
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        vdb.elemkFragmentVbvmBtn.setOnClickListener {
            vm.addNum()
        }
    }
}