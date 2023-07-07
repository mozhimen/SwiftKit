package com.mozhimen.componentk.navigatek

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.mozhimen.basick.lintk.optin.annors.ADescription
import com.mozhimen.basick.lintk.optin.annors.AOptLazyInit
import com.mozhimen.basick.elemk.lifecycle.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.componentk.navigatek.bases.BaseNavigateKViewModel


/**
 * @ClassName NavigateKWithVMDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/17 16:44
 * @Version 1.0
 */
@AOptLazyInit
class NavigateKWithVMDelegate<A>(
    private val _activity: A,
    private val _vm: BaseNavigateKViewModel,
    private val _fragmentLayoutId: Int,
    private val _fragments: List<Class<*>>
) :
    BaseWakeBefDestroyLifecycleObserver() where A : LifecycleOwner, A : FragmentActivity {

    lateinit var navController: NavController
    private var _currentItemId: Int = 0
        set(value) {
            if (value == -1 || !this::navController.isInitialized) return
            navController.navigate(value)
            field = value
        }
        get() {
            if (field != 0 || !this::navController.isInitialized) return field
            val fragmentId: Int = navController.currentDestination?.id ?: NavigateK.getId(_fragments.getOrNull(0) ?: return 0)
            return fragmentId.also { field = it }
        }

    override fun onDestroy(owner: LifecycleOwner) {
        _vm.liveFragmentId.value = null
        _vm.liveSetPopupFlag.value = null
        super.onDestroy(owner)
    }

    override fun onCreate(owner: LifecycleOwner) {
        navController = NavigateK.buildNavGraph(_activity, _fragmentLayoutId, _fragments, _currentItemId)
        _vm.liveFragmentId.observe(_activity) {
            if (it != null) {
                genNavigateK(it)
            } else {
                "please add this destination to list".et(TAG)
            }
        }
        _vm.liveSetPopupFlag.observe(_activity) {
            if (it != null) {
                navController.popBackStack()
            }
        }
        super.onCreate(owner)
    }

    private fun genNavigateK(id: Int?) {
        if (id != null && navController.findDestination(id) != null && navController.currentDestination?.id != id) {
            _currentItemId = id
        }
    }
}