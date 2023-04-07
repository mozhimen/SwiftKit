package com.mozhimen.basicktest.elemk.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import com.mozhimen.basick.elemk.fragment.bases.BaseDialogFragmentVB
import com.mozhimen.basicktest.databinding.FragmentElemkDialogFragmentVbvmBinding
import com.mozhimen.basicktest.elemk.viewmodel.ElemKViewModel

class ElemKDialogFragmentVB : BaseDialogFragmentVB<FragmentElemkDialogFragmentVbvmBinding>() {
    private val vm: ElemKViewModel by viewModels(ownerProducer = { requireActivity() })

    override fun initView(savedInstanceState: Bundle?) {
        Log.d(TAG, "initData: 1")
        VB.elemkFragmentVbvmBtn.setOnClickListener {
            Log.d(TAG, "initData: 2")
            vm.genNum()
        }
    }
}