package com.mozhimen.componentktest.navigatek.fragments

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKFragment
import com.mozhimen.componentk.navigatek.NavigateK
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.FragmentFirstBinding
import com.mozhimen.componentktest.databinding.FragmentSecondBinding
import com.mozhimen.componentktest.navigatek.NavigateKViewModel

class SecondFragment : BaseKFragment<FragmentSecondBinding, NavigateKViewModel>(R.layout.fragment_second) {
    override fun initData(savedInstanceState: Bundle?) {
        vb.navigatekFragmentSecondTxt.setOnClickListener {
            vm.liveFragmentId.value = NavigateK.getId(FirstFragment::class.java)
        }
    }
}