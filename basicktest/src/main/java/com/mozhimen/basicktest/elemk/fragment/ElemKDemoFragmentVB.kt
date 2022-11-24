package com.mozhimen.basicktest.elemk.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import com.mozhimen.basick.elemk.fragment.bases.BaseFragmentVB
import com.mozhimen.basicktest.databinding.FragmentElemkFragmentVbBinding
import com.mozhimen.basicktest.elemk.viewmodel.ElemKDemoViewModel

class ElemKDemoFragmentVB : BaseFragmentVB<FragmentElemkFragmentVbBinding>() {
    private val vm: ElemKDemoViewModel by viewModels(ownerProducer = { requireActivity() })

    override fun initData(savedInstanceState: Bundle?) {
        Log.d(TAG, "initData: 1")
        vb.elemkFragmentVbBtn.setOnClickListener {
            Log.d(TAG, "initData: 2")
            vm.genNum()
        }
    }
}