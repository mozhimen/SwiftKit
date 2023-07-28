package com.mozhimen.componentktest.navigatek

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.componentk.navigatek.NavigateKProxy
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.ActivityNavigatekBinding
import com.mozhimen.componentktest.navigatek.fragments.FirstFragment
import com.mozhimen.componentktest.navigatek.fragments.SecondFragment

class NavigateKActivity : BaseActivityVB<ActivityNavigatekBinding>() {

    private val _fragments = listOf(FirstFragment::class.java, SecondFragment::class.java)

    @OptIn(OptInApiCall_BindLifecycle::class, OptInApiInit_ByLazy::class)
    private val _navigateKProxy by lazy { NavigateKProxy(this, R.id.navigatek_fragment_container, _fragments) }
    val navigateKProxy get() = _navigateKProxy

    @OptIn(OptInApiCall_BindLifecycle::class, OptInApiInit_ByLazy::class)
    override fun initView(savedInstanceState: Bundle?) {
        _navigateKProxy.bindLifecycle(this)
    }
}