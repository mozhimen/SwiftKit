package com.mozhimen.basicktest.elemk.androidx

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseDialogFragmentVB
import com.mozhimen.basicktest.databinding.FragmentElemkDialogFragmentVbvmBinding

class ElemKDialogFragmentVB : BaseDialogFragmentVB<FragmentElemkDialogFragmentVbvmBinding>() {
    private val vm: ElemKViewModel by viewModels(ownerProducer = { requireActivity() })

    override fun initView(savedInstanceState: Bundle?) {
        vb.elemkFragmentVbvmBtn.setOnClickListener {
            vm.addNum()
        }
    }
}