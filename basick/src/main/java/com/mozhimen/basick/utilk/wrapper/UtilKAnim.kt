package com.mozhimen.basick.utilk.wrapper

import android.animation.Animator
import android.app.Activity
import android.view.View
import android.view.animation.Animation
import androidx.annotation.RequiresApi
import androidx.annotation.TransitionRes
import android.transition.TransitionInflater
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.android.animation.UtilKAnimator
import com.mozhimen.basick.utilk.android.app.UtilKActivityWrapper
import com.mozhimen.basick.utilk.android.view.UtilKAnimation
import com.mozhimen.basick.utilk.android.view.UtilKWindow
import com.mozhimen.basick.utilk.bases.BaseUtilK

/**
 * @ClassName UtilKAnim
 * @Description 不推荐直接使用,因为属性动画需要不及时释放,会造成内存泄露
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/18 0:25
 * @Version 1.0
 */

fun View.stopAnim() {
    UtilKAnim.stopAnim(this)
}

object UtilKAnim : BaseUtilK() {
    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun applyActivityAnim(activity: Activity, @TransitionRes intTransitionRes: Int) {
        UtilKActivityWrapper.requestWindowFeature_ofCONTENT_TRANSITIONS(activity)
        UtilKWindow.applyEnterTransition(activity, TransitionInflater.from(activity).inflateTransition(intTransitionRes))
    }

    /**
     * 释放Anim
     */
    @JvmStatic
    fun releaseAnim(vararg objs: Any) {
        if (objs.isEmpty()) return
        for (obj in objs) {
            if (obj is Animation) UtilKAnimation.cancel_releaseAnimationListener(obj)
            else if (obj is Animator) UtilKAnimator.cancel_removeAllListeners(obj)
        }
    }

    /**
     * 停止View的Anim
     */
    @JvmStatic
    fun stopAnim(view: View) {
        view.apply {
            animation?.cancel()
            clearAnimation()
        }
    }
}