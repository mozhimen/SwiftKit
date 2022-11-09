package com.mozhimen.componentktest.navigatek.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
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

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
}