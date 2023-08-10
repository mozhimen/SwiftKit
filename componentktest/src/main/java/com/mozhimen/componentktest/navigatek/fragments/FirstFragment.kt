package com.mozhimen.componentktest.navigatek.fragments

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseFragmentVB
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseFragmentVBVM
import com.mozhimen.basick.elemk.mos.MKey
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.componentk.navigatek.helpers.getDestinationId
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.BR
import com.mozhimen.componentktest.databinding.FragmentFirstBinding
import com.mozhimen.componentktest.databinding.ItemNavigatekBinding
import com.mozhimen.componentktest.navigatek.NavigateKActivity
import com.mozhimen.uicorek.adapterk.AdapterKRecyclerVB

class FirstFragment : BaseFragmentVB<FragmentFirstBinding>() {
    private val _datas = listOf(MKey("01", "01"))
    private var _adapter: AdapterKRecyclerVB<MKey, ItemNavigatekBinding>? = null

    @OptIn(OptInApiCall_BindLifecycle::class, OptInApiInit_ByLazy::class)
    override fun initView(savedInstanceState: Bundle?) {
        vb.navigatekFragmentFirstTxt.setOnClickListener {
            (requireActivity() as NavigateKActivity).navigateKProxy.startDestinationId(SecondFragment::class.java.getDestinationId())
        }
        vb.navigatekFragmentFirstRecycler.layoutManager = LinearLayoutManager(requireActivity())
        _adapter = AdapterKRecyclerVB<MKey, ItemNavigatekBinding>(_datas, R.layout.item_navigatek, BR.item_navigatek)
        vb.navigatekFragmentFirstRecycler.adapter = _adapter
    }
}