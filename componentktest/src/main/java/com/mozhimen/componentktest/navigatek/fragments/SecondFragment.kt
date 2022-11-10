package com.mozhimen.componentktest.navigatek.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.basek.BaseKFragmentVBVM
import com.mozhimen.componentk.navigatek.NavigateK
import com.mozhimen.componentktest.databinding.FragmentSecondBinding
import com.mozhimen.componentktest.navigatek.NavigateKViewModel

class SecondFragment : BaseKFragmentVBVM<FragmentSecondBinding, NavigateKViewModel>() {
    override fun initData(savedInstanceState: Bundle?) {
        vb.navigatekFragmentSecondTxt.setOnClickListener {
            vm.liveFragmentId.value = NavigateK.getId(FirstFragment::class.java)
        }
    }

    override fun bindViewVM(vb: FragmentSecondBinding) {

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