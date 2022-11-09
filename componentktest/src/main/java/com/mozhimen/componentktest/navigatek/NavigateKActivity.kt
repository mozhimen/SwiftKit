package com.mozhimen.componentktest.navigatek

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.mozhimen.basick.basek.BaseKActivityVBVM
import com.mozhimen.basick.extsk.et
import com.mozhimen.componentk.navigatek.NavigateK
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.ActivityNavigatekBinding
import com.mozhimen.componentktest.navigatek.fragments.FirstFragment
import com.mozhimen.componentktest.navigatek.fragments.SecondFragment

class NavigateKActivity : BaseKActivityVBVM<ActivityNavigatekBinding, NavigateKViewModel>() {

    private val _fragments = listOf(FirstFragment::class.java, SecondFragment::class.java)
    private lateinit var _navController: NavController
    private val _navOptions = NavOptions.Builder().setLaunchSingleTop(true).build()
    private var _currentItemId: Int = 0
        set(value) {
            if (value == -1 || !this::_navController.isInitialized) return
            _navController.navigate(resId = value, args = null, navOptions = _navOptions)
            Log.d(TAG, "backQueue: ${_navController.backQueue.joinToString { it.destination.displayName }}")
            field = value
        }

    companion object {
        private const val navigatek_saved_current_id = "navigatek_saved_current_id"
    }

    override fun initData(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            _currentItemId = savedInstanceState.getInt(navigatek_saved_current_id, -1)
        }
        initView(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        _navController = NavigateK.buildNavGraph(this, R.id.navigatek_fragment_container, _fragments, _currentItemId)
        vm.liveFragmentId.observe(this) {
            if (it != null && _navController.findDestination(it) != null && _navController.currentDestination?.id != it) {
                _currentItemId = it
            } else {
                "please add this destination to list".et(TAG)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(navigatek_saved_current_id, _currentItemId)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        _currentItemId = savedInstanceState.getInt(navigatek_saved_current_id, -1)
    }

    override fun bindViewVM(vb: ActivityNavigatekBinding) {
    }
}