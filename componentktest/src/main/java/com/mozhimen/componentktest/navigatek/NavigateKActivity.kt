package com.mozhimen.componentktest.navigatek

import android.os.Bundle
import androidx.navigation.ui.NavigationUI
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.componentk.navigatek.NavigateKActivityProxy
import com.mozhimen.componentk.navigatek.mos.MNavigateKConfig
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.ActivityNavigatekBinding
import com.mozhimen.componentktest.navigatek.fragments.FirstFragment
import com.mozhimen.componentktest.navigatek.fragments.SecondFragment

@OptIn(OptInApiCall_BindLifecycle::class, OptInApiInit_ByLazy::class)
class NavigateKActivity : BaseActivityVB<ActivityNavigatekBinding>() {

    private val _fragments = listOf(FirstFragment::class.java, SecondFragment::class.java)
    private val _navigateKActivityProxy by lazy {
        NavigateKActivityProxy(this, R.id.navigatek_fragment_container, _fragments, MNavigateKConfig(isFragmentShowHide = true))//show_hide方式
//        NavigateKProxy(this, R.id.navigatek_fragment_container, _fragments)
    }

    val navigateKProxy get() = _navigateKActivityProxy

    override fun initView(savedInstanceState: Bundle?) {
//        setSupportActionBar(vb.navigatekToolbar)//如果没有toolbar
        _navigateKActivityProxy.bindLifecycle(this)
        NavigationUI.setupActionBarWithNavController(this, _navigateKActivityProxy.navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return _navigateKActivityProxy.navController.navigateUp()
    }
}