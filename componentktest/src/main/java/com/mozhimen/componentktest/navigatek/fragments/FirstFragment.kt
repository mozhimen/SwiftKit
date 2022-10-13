package com.mozhimen.componentktest.navigatek.fragments

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKFragment
import com.mozhimen.componentk.navigatek.NavigateK
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.FragmentFirstBinding
import com.mozhimen.componentktest.navigatek.NavigateKViewModel

class FirstFragment : BaseKFragment<FragmentFirstBinding, NavigateKViewModel>(R.layout.fragment_first) {

    override fun initData(savedInstanceState: Bundle?) {
        vb.navigatekFragmentFirstTxt.setOnClickListener {
            vm.liveFragmentId.value = NavigateK.getId(SecondFragment::class.java)
        }
    }
}