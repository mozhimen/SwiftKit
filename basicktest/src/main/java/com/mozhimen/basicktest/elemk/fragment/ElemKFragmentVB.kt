package com.mozhimen.basicktest.elemk.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseFragmentVB
import com.mozhimen.basicktest.databinding.FragmentElemkFragmentVbBinding
import com.mozhimen.basicktest.elemk.viewmodel.ElemKViewModel

class ElemKFragmentVB : BaseFragmentVB<FragmentElemkFragmentVbBinding>() {
    private val vm: ElemKViewModel by viewModels(ownerProducer = { requireActivity() })

    override fun initView(savedInstanceState: Bundle?) {
        Log.d(TAG, "initData: 1")
        vb.elemkFragmentVbBtn.setOnClickListener {
            Log.d(TAG, "initData: 2")
            vm.genNum()
        }
    }
}