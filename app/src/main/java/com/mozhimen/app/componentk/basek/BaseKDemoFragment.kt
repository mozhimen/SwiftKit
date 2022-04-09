package com.mozhimen.app.componentk.basek

import com.mozhimen.app.R
import com.mozhimen.app.databinding.FragmentBasekFragmentBinding
import com.mozhimen.componentk.basek.BaseKFragment

class BaseKDemoFragment :
    BaseKFragment<FragmentBasekFragmentBinding, BaseKDemoViewModel>(R.layout.fragment_basek_fragment) {

    override fun assignVM() {
        vb.vm = vm
    }

    override fun initView() {

    }
}