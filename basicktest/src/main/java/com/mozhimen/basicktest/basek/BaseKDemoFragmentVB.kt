package com.mozhimen.basicktest.basek

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import com.mozhimen.basick.basek.BaseKFragmentVB
import com.mozhimen.basicktest.databinding.FragmentBasekFragmentVbBinding

class BaseKDemoFragmentVB : BaseKFragmentVB<FragmentBasekFragmentVbBinding>() {
    private val vm: BaseKDemoViewModel by viewModels(ownerProducer = { requireActivity() })

    override fun initData(savedInstanceState: Bundle?) {
        Log.d(TAG, "initData: 1")
        vb.basekDemoFragmentVbBtn.setOnClickListener {
            Log.d(TAG, "initData: 2")
            vm.genNum()
        }
    }
}