package com.mozhimen.taskk.temps

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindViewLifecycle
import com.mozhimen.taskk.bases.BaseWakeBefDestroyTaskK
import com.mozhimen.kotlin.utilk.android.animation.UtilKAnimatorWrapper
import com.mozhimen.kotlin.utilk.wrapper.UtilKAnim
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
            UtilKAnimatorWrapper.cancel_removeAllListeners(it.value)
            UtilKAnim.stopAnim(it.key)
        }
        _viewAnims.clear()
    }
}