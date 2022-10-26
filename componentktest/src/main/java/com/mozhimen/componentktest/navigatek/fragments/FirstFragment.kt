package com.mozhimen.componentktest.navigatek.fragments

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKFragmentVBVM
import com.mozhimen.componentk.navigatek.NavigateK
import com.mozhimen.componentktest.databinding.FragmentFirstBinding
import com.mozhimen.componentktest.navigatek.NavigateKViewModel

class FirstFragment : BaseKFragmentVBVM<FragmentFirstBinding, NavigateKViewModel>() {

    override fun initData(savedInstanceState: Bundle?) {
        vb.navigatekFragmentFirstTxt.setOnClickListener {
            vm.liveFragmentId.value = NavigateK.getId(SecondFragment::class.java)
        }
    }

    override fun bindViewVM(vb: FragmentFirstBinding) {

    }
}