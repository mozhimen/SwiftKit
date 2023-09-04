package com.mozhimen.basicktest.elemk.androidx

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseFragmentVB
import com.mozhimen.basicktest.databinding.FragmentElemkFragmentVbBinding

class ElemKFragmentVB : BaseFragmentVB<FragmentElemkFragmentVbBinding>() {
    private val vm: ElemKViewModel by viewModels(ownerProducer = { requireActivity() })

    override fun initView(savedInstanceState: Bundle?) {
        vb.elemkFragmentVbBtn.setOnClickListener {
            vm.addNum()
        }
    }
}