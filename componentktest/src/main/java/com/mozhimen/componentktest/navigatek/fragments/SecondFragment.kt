package com.mozhimen.componentktest.navigatek.fragments

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseFragmentVB
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseFragmentVBVM
import com.mozhimen.basick.elemk.mos.MKey
import com.mozhimen.componentktest.BR
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.FragmentSecondBinding
import com.mozhimen.componentktest.databinding.ItemNavigatekBinding
import com.mozhimen.componentktest.navigatek.NavigateKActivity
import com.mozhimen.uicorek.adapterk.AdapterKRecyclerVB

class SecondFragment : BaseFragmentVB<FragmentSecondBinding>() {
    private val _datas = listOf(MKey("01", "01"))
    private var _adapter: AdapterKRecyclerVB<MKey, ItemNavigatekBinding>? = null

    override fun initView(savedInstanceState: Bundle?) {
        vb.navigatekFragmentSecondTxt.setOnClickListener {
            (requireActivity() as NavigateKActivity).navigateKProxy.navController.popBackStack()
        }
        vb.navigatekFragmentSecondRecycler.layoutManager = LinearLayoutManager(requireActivity())
        _adapter = AdapterKRecyclerVB<MKey, ItemNavigatekBinding>(_datas, R.layout.item_navigatek, BR.item_navigatek)
        vb.navigatekFragmentSecondRecycler.adapter = _adapter
    }
}