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
import com.mozhimen.componentk.navigatek.bases.BaseNavigateKViewModel
import com.mozhimen.uicorek.adapterk.AdapterKRecyclerVB

class SecondFragment : BaseFragmentVBVM<FragmentSecondBinding, BaseNavigateKViewModel>() {
    private val _datas = listOf(MKey("01", "01"))
    private var _adapter: AdapterKRecyclerVB<MKey, ItemNavigatekBinding>? = null
    override fun initView(savedInstanceState: Bundle?) {
        VB.navigatekFragmentSecondTxt.setOnClickListener {
            (requireActivity() as NavigateKActivity).navController.popBackStack()
            //vm.liveFragmentId.value = NavigateK.getId(FirstFragment::class.java)
        }
        VB.navigatekFragmentSecondRecycler.layoutManager = LinearLayoutManager(requireActivity())
        _adapter = AdapterKRecyclerVB<MKey, ItemNavigatekBinding>(_datas, R.layout.item_navigatek, BR.item_navigatek)
        VB.navigatekFragmentSecondRecycler.adapter = _adapter
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
        VB.navigatekFragmentSecondRecycler.adapter = null
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