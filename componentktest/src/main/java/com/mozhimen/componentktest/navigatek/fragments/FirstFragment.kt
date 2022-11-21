package com.mozhimen.componentktest.navigatek.fragments

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.fragment.commons.BaseFragmentVBVM
import com.mozhimen.componentk.navigatek.exts.getNavigateKId
import com.mozhimen.componentktest.databinding.FragmentFirstBinding
import com.mozhimen.componentktest.navigatek.NavigateKViewModel

class FirstFragment : BaseFragmentVBVM<FragmentFirstBinding, NavigateKViewModel>() {

    override fun initData(savedInstanceState: Bundle?) {
        vb.navigatekFragmentFirstTxt.setOnClickListener {
            vm.liveFragmentId.value = SecondFragment::class.java.getNavigateKId()
        }
    }

    override fun bindViewVM(vb: FragmentFirstBinding) {

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
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