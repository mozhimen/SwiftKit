package com.mozhimen.basick.utilk

import android.animation.*
import android.view.View
import android.view.animation.*

/**
 * @ClassName UtilKAnim
 * @Description 不推荐直接使用,因为属性动画需要不及时释放,会造成内存泄露
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/18 0:25
 * @Version 1.0
 */
object UtilKAnim {
    private const val TAG = "UtilKAnim>>>>>"

    @JvmStatic
    fun releaseAnimation(vararg objs: Any) {
        if (objs.isEmpty()) return
        for (obj in objs) {
            if (obj is Animation) {
                UtilKAnimation.releaseAnimation(obj)
            } else if (obj is Animator) {
                UtilKAnimator.releaseAnimator(obj)
            }
        }
    }

    @JvmStatic
    fun stopAnim(view: View) {
        view.apply {
            animation?.cancel()
            view.clearAnimation()
        }
    }
}