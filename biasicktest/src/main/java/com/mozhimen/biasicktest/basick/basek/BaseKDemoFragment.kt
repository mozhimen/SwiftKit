package com.mozhimen.biasicktest.basick.basek

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKFragment
import com.mozhimen.biasicktest.R
import com.mozhimen.biasicktest.databinding.FragmentBasekFragmentBinding

class BaseKDemoFragment :
    BaseKFragment<FragmentBasekFragmentBinding, BaseKDemoViewModel>(R.layout.fragment_basek_fragment) {

    override fun injectVM() {
        vb.vm = vm
    }

    override fun initView(savedInstanceState: Bundle?) {

    }
}