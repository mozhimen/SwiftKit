package com.mozhimen.basicktest.basick.basek

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKFragment
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.FragmentBasekFragmentBinding

class BaseKDemoFragment :
    BaseKFragment<FragmentBasekFragmentBinding, BaseKDemoViewModel>(R.layout.fragment_basek_fragment) {

    override fun injectVM() {
        vb.vm = vm
    }

    override fun initView(savedInstanceState: Bundle?) {

    }
}