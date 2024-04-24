package com.mozhimen.basick.taskk.temps

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import com.mozhimen.basick.lintk.optins.OApiInit_ByLazy
import com.mozhimen.basick.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optins.OApiCall_BindViewLifecycle
import com.mozhimen.basick.taskk.bases.BaseWakeBefDestroyTaskK
import com.mozhimen.basick.utilk.android.animation.UtilKAnimator
import com.mozhimen.basick.utilk.wrapper.UtilKAnim
import java.util.concurrent.ConcurrentHashMap

/**
 * @ClassName TaskKAnimKAnimator
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/20 22:34
 * @Version 1.0
 */
@OApiCall_BindViewLifecycle
@OApiCall_BindLifecycle
@OApiInit_ByLazy
class TaskKAnimator : BaseWakeBefDestroyTaskK() {
    private val _viewAnims: ConcurrentHashMap<View, Animator> = ConcurrentHashMap()

    ///////////////////////////////////////////////////////////////////

    fun add(view: View, animator: Animator) {
        _viewAnims[view] = animator
    }

    fun remove(view: View){
        _viewAnims.remove(view)
    }

    ///////////////////////////////////////////////////////////////////

    override fun isActive(): Boolean =
        _viewAnims.isNotEmpty()

    override fun cancel() {
        _viewAnims.forEach {
            (it.value as ValueAnimator).removeAllUpdateListeners()
            UtilKAnimator.cancel_removeAllListeners(it.value)
            UtilKAnim.stopAnim(it.key)
        }
        _viewAnims.clear()
    }
}