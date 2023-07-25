package com.mozhimen.componentktest.navigatek

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVBVM
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.componentk.navigatek.NavigateK
import com.mozhimen.componentk.navigatek.bases.BaseNavigateKViewModel
import com.mozhimen.componentk.navigatek.helpers.DestinationUtil
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.ActivityNavigatekBinding
import com.mozhimen.componentktest.navigatek.fragments.FirstFragment
import com.mozhimen.componentktest.navigatek.fragments.SecondFragment

class NavigateKActivity : BaseActivityVBVM<ActivityNavigatekBinding, BaseNavigateKViewModel>() {

    private val _fragments = listOf(FirstFragment::class.java, SecondFragment::class.java)
    lateinit var navController: NavController
    private var _currentItemId: Int = 0
        set(value) {
            if (value == -1 || !this::navController.isInitialized) return
            navController.navigate(value)
            Log.d(TAG, "backQueue: ${navController.backQueue.joinToString { it.destination.displayName }}")
            field = value
        }
        get() {
            if (field != 0 || !this::navController.isInitialized) return field
            val fragmentId: Int = navController.currentDestination?.id ?: DestinationUtil.getId(_fragments[0])
            return fragmentId.also { field = it }
        }

    override fun initView(savedInstanceState: Bundle?) {
        navController = NavigateK.buildNavGraph(this, R.id.navigatek_fragment_container, _fragments, _currentItemId)
        vm.liveFragmentId.observe(this) {
            if (it != null) {
                genNavigateK(it)
            } else {
                "please add this destination to list".et(TAG)
            }
        }
        vm.liveSetPopupFlag.observe(this) {
            if (it != null) {
                navController.popBackStack()
            }
        }
    }

    private fun genNavigateK(id: Int?) {
        if (id != null && navController.findDestination(id) != null && navController.currentDestination?.id != id) {
            _currentItemId = id
        }
    }

    override fun bindViewVM(vb: ActivityNavigatekBinding) {
    }
}