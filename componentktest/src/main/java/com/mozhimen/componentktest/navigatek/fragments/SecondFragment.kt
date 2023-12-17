package com.mozhimen.componentktest.navigatek.fragments

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseFragmentVB
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.componentk.navigatek.NavigateKFragmentProxy
import com.mozhimen.componentk.navigatek.helpers.getDestinationId
import com.mozhimen.componentk.navigatek.mos.MNavigateKConfig
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.FragmentSecondBinding
import com.mozhimen.componentktest.navigatek.NavigateKActivity

@OptIn(OptInApiCall_BindLifecycle::class, OptInApiInit_ByLazy::class)
class SecondFragment : BaseFragmentVB<FragmentSecondBinding>() {
    private val _fragments = listOf(ThirdFragment::class.java, ForthFragment::class.java)
    private val _navigateKFragmentProxy by lazy {
        NavigateKFragmentProxy(requireActivity(),this, R.id.navigatek_fragment_container, _fragments, MNavigateKConfig(isFragmentShowHide = true))//show_hide方式
//        NavigateKProxy(this, R.id.navigatek_fragment_container, _fragments)
    }

    val navigateKProxy get() = _navigateKFragmentProxy

    @OptIn(OptInApiCall_BindLifecycle::class, OptInApiInit_ByLazy::class)
    override fun initView(savedInstanceState: Bundle?) {
        arguments?.getString(FirstFragment.KEY_FIRST,"这是原有的数据")?.let {
            vb.navigatekFragmentSecondTxt1.text = it
        }
        vb.navigatekFragmentSecondTxt2.setOnClickListener {
            (requireActivity() as NavigateKActivity).navigateKProxy.startDestinationId(FirstFragment::class.java.getDestinationId())
        }

        ///////////////////////////////////////////////////////////////////////

        _navigateKFragmentProxy.bindLifecycle(this)
    }
}