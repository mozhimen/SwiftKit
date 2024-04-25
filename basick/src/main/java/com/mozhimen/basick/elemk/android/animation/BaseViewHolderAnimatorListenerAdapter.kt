package com.mozhimen.basick.elemk.android.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * @ClassName BaseViewHolderAnimatorListenerAdapter
 * @Description //设定在动画开始结束和取消状态下是否可以被回收
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/4/24 23:24
 * @Version 1.0
 */
class BaseViewHolderAnimatorListenerAdapter(
    private val _viewHolder: RecyclerView.ViewHolder
) : AnimatorListenerAdapter() {
    override fun onAnimationStart(animation: Animator) { //开始时
        _viewHolder.setIsRecyclable(false)
    }

    override fun onAnimationEnd(animation: Animator) { //结束时
        _viewHolder.setIsRecyclable(true)
    }

    override fun onAnimationCancel(animation: Animator) { //取消时
        _viewHolder.setIsRecyclable(true)
    }
}