package com.mozhimen.app.componentmk.basemk

import com.mozhimen.app.R
import com.mozhimen.app.databinding.FragmentBasemkFragmentBinding
import com.mozhimen.componentmk.basemk.BaseMKFragment

class BaseMKDemoFragment :
    BaseMKFragment<FragmentBasemkFragmentBinding, BaseMKDemoViewModel>(R.layout.fragment_basemk_fragment) {

    override fun assignVM() {
        vb.vm = vm
    }

    override fun initView() {

    }
}