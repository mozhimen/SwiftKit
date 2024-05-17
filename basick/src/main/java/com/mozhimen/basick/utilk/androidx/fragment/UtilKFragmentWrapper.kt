package com.mozhimen.basick.utilk.androidx.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.commons.ISuspendExt_Listener
import com.mozhimen.basick.utilk.androidx.lifecycle.UtilKLifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * @ClassName UtilKFragmentWrapper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/16
 * @Version 1.0
 */
fun Fragment.runOnViewLifecycleState(state: Lifecycle.State, coroutineContext: CoroutineContext = EmptyCoroutineContext, block: ISuspendExt_Listener<CoroutineScope>) {
    UtilKFragmentWrapper.runOnViewLifecycleState(this, state, coroutineContext, block)
}

////////////////////////////////////////////////////////////////////

object UtilKFragmentWrapper {
    @JvmStatic
    fun runOnViewLifecycleState(fragment: Fragment, state: Lifecycle.State, coroutineContext: CoroutineContext = EmptyCoroutineContext, block: ISuspendExt_Listener<CoroutineScope>) {
        UtilKLifecycleOwner.runOnLifecycleState(fragment.viewLifecycleOwner, fragment.lifecycleScope, state, coroutineContext, block)
    }
}