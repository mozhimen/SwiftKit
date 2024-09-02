package com.mozhimen.basicktest.elemk.androidx

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.mozhimen.mvvmk.bases.fragment.databinding.BaseDialogFragmentVDB
import com.mozhimen.basicktest.databinding.FragmentElemkDialogFragmentVbvmBinding

class ElemKDialogFragmentVB : com.mozhimen.mvvmk.bases.fragment.databinding.BaseDialogFragmentVDB<FragmentElemkDialogFragmentVbvmBinding>() {
    private val vm: ElemKViewModel by viewModels(ownerProducer = { requireActivity() })

    override fun initView(savedInstanceState: Bundle?) {
        vdb.elemkFragmentVbvmBtn.setOnClickListener {
            vm.addNum()
        }
    }
}