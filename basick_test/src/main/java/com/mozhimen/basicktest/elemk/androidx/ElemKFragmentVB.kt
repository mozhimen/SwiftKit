package com.mozhimen.basicktest.elemk.androidx

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.mozhimen.mvvmk.bases.fragment.databinding.BaseFragmentVDB
import com.mozhimen.basicktest.databinding.FragmentElemkFragmentVbBinding

class ElemKFragmentVB : com.mozhimen.mvvmk.bases.fragment.databinding.BaseFragmentVDB<FragmentElemkFragmentVbBinding>() {
    private val vm: ElemKViewModel by viewModels(ownerProducer = { requireActivity() })

    override fun initView(savedInstanceState: Bundle?) {
        vdb.elemkFragmentVbBtn.setOnClickListener {
            vm.addNum()
        }
    }
}