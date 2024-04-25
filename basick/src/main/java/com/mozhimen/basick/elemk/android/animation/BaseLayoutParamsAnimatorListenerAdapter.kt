package com.mozhimen.basick.elemk.android.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

/**
 * @ClassName BaseLayoutParamsAnimatorListenerAdapter
 * @Description //设定在动画结束后View的宽度和高度分别为match_parent,warp_content
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/4/24 23:53
 * @Version 1.0
 */
class BaseLayoutParamsAnimatorListenerAdapter (private val _view: View, private val _lpWidth: Int, private val _lpHeight: Int) : AnimatorListenerAdapter() {
    override fun onAnimationEnd(animation: Animator) {
        val lp = _view.layoutParams
        lp.width = _lpWidth
        lp.height = _lpHeight
        _view.layoutParams = lp
    }
}