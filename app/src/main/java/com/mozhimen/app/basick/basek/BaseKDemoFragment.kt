package com.mozhimen.app.basick.basek

import android.os.Bundle
import com.mozhimen.app.R
import com.mozhimen.app.databinding.FragmentBasekFragmentBinding
import com.mozhimen.basick.basek.BaseKFragment

class BaseKDemoFragment :
    BaseKFragment<FragmentBasekFragmentBinding, BaseKDemoViewModel>(R.layout.fragment_basek_fragment) {

    override fun injectVM() {
        vb.vm = vm
    }

    override fun initView(savedInstanceState: Bundle?) {

    }
}