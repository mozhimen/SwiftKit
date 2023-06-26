package com.mozhimen.componentktest.navigatek

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVBVM
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.componentk.navigatek.NavigateK
import com.mozhimen.componentk.navigatek.bases.BaseNavigateKViewModel
import com.mozhimen.componentk.navigatek.cons.CNavigateK
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
            val fragmentId: Int = navController.currentDestination?.id ?: NavigateK.getId(_fragments[0])
            return fragmentId.also { field = it }
        }

    override fun initData(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            _currentItemId = savedInstanceState.getInt(CNavigateK.NAVIGATEK_SAVED_CURRENT_ID, -1)
        }
        super.initData(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(CNavigateK.NAVIGATEK_SAVED_CURRENT_ID, _currentItemId)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        _currentItemId = savedInstanceState.getInt(CNavigateK.NAVIGATEK_SAVED_CURRENT_ID, -1)
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