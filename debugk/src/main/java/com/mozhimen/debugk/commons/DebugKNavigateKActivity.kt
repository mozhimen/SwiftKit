package com.mozhimen.debugk.commons

import android.os.Bundle
import androidx.navigation.NavController
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVBVM
import com.mozhimen.basick.utilk.exts.et
import com.mozhimen.componentk.navigatek.NavigateK
import com.mozhimen.componentk.statusbark.StatusBarK
import com.mozhimen.debugk.R
import com.mozhimen.debugk.databinding.DebugkActivityNavigatekBinding


/**
 * @ClassName DebugKNavigateActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/22 10:55
 * @Version 1.0
 */
abstract class DebugKNavigateKActivity : BaseActivityVBVM<DebugkActivityNavigatekBinding, DebugKNavigateKViewModel>() {
    private val _fragments by lazy { getFragments() }

    private lateinit var _navController: NavController
    private var _currentItemId: Int = 0
        set(value) {
            if (value == -1 || !this::_navController.isInitialized) return
            _navController.navigate(resId = value)
            //Log.d(TAG, "backQueue: ${_navController.backQueue.joinToString { it.destination.displayName }}")
            field = value
        }

    abstract fun getFragments(): List<Class<*>>

    override fun initFlag() {
        StatusBarK.initStatusBar(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            _currentItemId = savedInstanceState.getInt(DEBUGK_NAVIGATEK_SAVED_CURRENT_ID, -1)
        }
        super.initData(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        _navController = NavigateK.buildNavGraph(this, R.id.debugk_navigatek_fragment_container, _fragments, _currentItemId)
        vm.liveFragmentId.observe(this) {
            if (it != null && _navController.findDestination(it) != null && _navController.currentDestination?.id != it) {
                _currentItemId = it
            } else {
                "please add this destination to list ".et(TAG)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(DEBUGK_NAVIGATEK_SAVED_CURRENT_ID, _currentItemId)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        _currentItemId = savedInstanceState.getInt(DEBUGK_NAVIGATEK_SAVED_CURRENT_ID, -1)
    }

    override fun bindViewVM(vb: DebugkActivityNavigatekBinding) {
    }

    companion object {
        private const val DEBUGK_NAVIGATEK_SAVED_CURRENT_ID = "debugk_navigatek_saved_current_id"
    }
}