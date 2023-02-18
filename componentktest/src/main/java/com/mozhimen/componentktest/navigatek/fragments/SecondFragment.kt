package com.mozhimen.componentktest.navigatek.fragments

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.elemk.fragment.bases.BaseFragmentVBVM
import com.mozhimen.basick.elemk.mos.MKey
import com.mozhimen.componentktest.BR
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.FragmentSecondBinding
import com.mozhimen.componentktest.databinding.ItemNavigatekBinding
import com.mozhimen.componentktest.navigatek.NavigateKActivity
import com.mozhimen.componentk.navigatek.bases.NavigateKViewModel
import com.mozhimen.uicorek.recyclerk.RecyclerKVBAdapter

class SecondFragment : BaseFragmentVBVM<FragmentSecondBinding, NavigateKViewModel>() {
    private val _datas = listOf<MKey>(MKey("01", "01"))
    private var _adapter: RecyclerKVBAdapter<MKey, ItemNavigatekBinding>? = null
    override fun initData(savedInstanceState: Bundle?) {
        vb.navigatekFragmentSecondTxt.setOnClickListener {
            (requireActivity() as NavigateKActivity).navController.popBackStack()
            //vm.liveFragmentId.value = NavigateK.getId(FirstFragment::class.java)
        }
        vb.navigatekFragmentSecondRecycler.layoutManager = LinearLayoutManager(requireActivity())
        _adapter = RecyclerKVBAdapter(_datas, R.layout.item_navigatek, BR.item_navigatek)
        vb.navigatekFragmentSecondRecycler.adapter = _adapter
    }

    override fun bindViewVM(vb: FragmentSecondBinding) {

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        vb.navigatekFragmentSecondRecycler.adapter = null
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
}