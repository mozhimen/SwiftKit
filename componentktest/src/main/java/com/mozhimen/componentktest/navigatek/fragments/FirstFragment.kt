package com.mozhimen.componentktest.navigatek.fragments

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.elemk.fragment.bases.BaseFragmentVBVM
import com.mozhimen.basick.elemk.mos.MKey
import com.mozhimen.componentk.navigatek.bases.BaseNavigateKViewModel
import com.mozhimen.componentk.navigatek.exts.getNavigateKId
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.BR
import com.mozhimen.componentktest.databinding.FragmentFirstBinding
import com.mozhimen.componentktest.databinding.ItemNavigatekBinding
import com.mozhimen.uicorek.recyclerk.RecyclerKVBAdapter

class FirstFragment : BaseFragmentVBVM<FragmentFirstBinding, BaseNavigateKViewModel>() {
    private val _datas = listOf(MKey("01", "01"))
    private var _adapter: RecyclerKVBAdapter<MKey, ItemNavigatekBinding>? = null
    override fun initData(savedInstanceState: Bundle?) {
        vb.navigatekFragmentFirstTxt.setOnClickListener {
            vm.liveFragmentId.value = SecondFragment::class.java.getNavigateKId()
        }
        vb.navigatekFragmentFirstRecycler.layoutManager = LinearLayoutManager(requireActivity())
        _adapter = RecyclerKVBAdapter(_datas, R.layout.item_navigatek, BR.item_navigatek)
        vb.navigatekFragmentFirstRecycler.adapter = _adapter
    }

    override fun bindViewVM(vb: FragmentFirstBinding) {

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
        vb.navigatekFragmentFirstRecycler.adapter = null
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