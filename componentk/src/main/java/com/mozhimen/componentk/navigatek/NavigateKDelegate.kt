package com.mozhimen.componentk.navigatek

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.mozhimen.basick.elemk.annors.ADescription
import com.mozhimen.basick.elemk.lifecycle.bases.BaseDelegateLifecycleObserver
import com.mozhimen.basick.utilk.exts.et
import com.mozhimen.componentk.navigatek.bases.BaseNavigateKViewModel


/**
 * @ClassName NavigateKDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/17 16:44
 * @Version 1.0
 */
@ADescription("init in global, no by lazy")
class NavigateKDelegate<T>(
    private val _owner: T,
    private val _fragmentLayoutId: Int,
    vararg val _fragments: Class<*>
) :
    BaseDelegateLifecycleObserver(_owner) where T : LifecycleOwner, T : FragmentActivity {
    private val liveFragmentId = MutableLiveData<Int?>(null)
    private val liveSetPopupFlag = MutableLiveData<Boolean?>(null)
    lateinit var navController: NavController
    var currentItemId: Int = 0
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

    override fun onCreate(owner: LifecycleOwner) {
        navController = NavigateK.buildNavGraph(_owner, _fragmentLayoutId, _fragments.toList(), currentItemId)
        liveFragmentId.observe(_owner) {
            if (it != null) {
                genNavigateK(it)
            } else {
                "please add this destination to list".et(TAG)
            }
        }
        liveSetPopupFlag.observe(_owner) {
            if (it != null) {
                navController.popBackStack()
            }
        }
        super.onCreate(owner)
    }

    private fun genNavigateK(id: Int?) {
        if (id != null && navController.findDestination(id) != null && navController.currentDestination?.id != id) {
            currentItemId = id
        }
    }
}