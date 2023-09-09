package com.mozhimen.componentktest.navigatek

import android.os.Bundle
import androidx.navigation.ui.NavigationUI
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.componentk.navigatek.NavigateKProxy
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.ActivityNavigatekBinding
import com.mozhimen.componentktest.navigatek.fragments.FirstFragment
import com.mozhimen.componentktest.navigatek.fragments.SecondFragment

@OptIn(OptInApiCall_BindLifecycle::class, OptInApiInit_ByLazy::class)
class NavigateKActivity : BaseActivityVB<ActivityNavigatekBinding>() {

    private val _fragments = listOf(FirstFragment::class.java, SecondFragment::class.java)
    private val _navigateKProxy by lazy { NavigateKProxy(this, R.id.navigatek_fragment_container, _fragments) }

    val navigateKProxy get() = _navigateKProxy

    override fun initView(savedInstanceState: Bundle?) {
        setSupportActionBar(vb.navigatekToolbar)
        _navigateKProxy.bindLifecycle(this)
        NavigationUI.setupActionBarWithNavController(this, _navigateKProxy.navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return _navigateKProxy.navController.navigateUp()
    }
}