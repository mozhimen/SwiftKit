package com.mozhimen.componentktest.navigatek.fragments

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseFragmentVB
import com.mozhimen.basick.elemk.mos.MKey
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.componentk.navigatek.helpers.getDestinationId
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.BR
import com.mozhimen.componentktest.databinding.FragmentFirstBinding
import com.mozhimen.componentktest.databinding.ItemNavigatekBinding
import com.mozhimen.uicorek.adapterk.quick.AdapterKQuickRecyclerVB

class FirstFragment : BaseFragmentVB<FragmentFirstBinding>() {
    companion object {
        const val KEY_FIRST = "Title"
    }

    private val _datas = mutableListOf(MKey("01", "01"))
    private var _adapter: AdapterKQuickRecyclerVB<MKey, ItemNavigatekBinding>? = null

    @OptIn(OptInApiCall_BindLifecycle::class, OptInApiInit_ByLazy::class)
    override fun initView(savedInstanceState: Bundle?) {
        //用法一
//        vb.navigatekFragmentFirstTxt.setOnClickListener {
//            (requireActivity() as NavigateKActivity).navigateKProxy.startDestinationId(SecondFragment::class.java.getDestinationId())
//        }
        //用法二
        vb.navigatekFragmentFirstTxt.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                SecondFragment::class.java.getDestinationId(),
                Bundle().apply { putString(KEY_FIRST, "这是Args数据") })
        )

        vb.navigatekFragmentFirstRecycler.layoutManager = LinearLayoutManager(requireActivity())
        _adapter = AdapterKQuickRecyclerVB<MKey, ItemNavigatekBinding>(_datas, R.layout.item_navigatek, BR.item_navigatek)
        vb.navigatekFragmentFirstRecycler.adapter = _adapter
    }
}