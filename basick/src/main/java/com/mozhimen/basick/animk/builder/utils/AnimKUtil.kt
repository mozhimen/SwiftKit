package com.mozhimen.basick.animk.builder.utils

import android.animation.Animator
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mozhimen.basick.animk.builder.AnimKBuilder
import com.mozhimen.basick.elemk.android.animation.BaseLayoutParamsAnimatorListenerAdapter
import com.mozhimen.basick.elemk.android.animation.BaseViewHolderAnimatorListenerAdapter

/**
 * @ClassName AnimKUtil
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/4/24 23:50
 * @Version 1.0
 */
object AnimKUtil {
    /**
     * 高度
     * //真正实现具体展开动画的方法，使用ValueAnimator.ofInt生成一系列高度值，然后添加上面的监听
     * //监听动画的变化，不断设定view的高度值
     */
    @JvmStatic
    fun get_ofHeight(view: View, startHeight: Int, endHeight: Int): Animator =
        AnimKTypeUtil.get_ofHeight(view, startHeight, endHeight).build()

    @JvmStatic
    fun get_ofHeight_viewHolder(view: View, viewHolder: ViewHolder, startHeight: Int, endHeight: Int): Animator =
        AnimKTypeUtil.get_ofHeight_viewHolder(view, viewHolder, startHeight, endHeight).build()

    @JvmStatic
    fun get_ofHeight_viewHolder(viewHolder: ViewHolder): Animator =
        AnimKTypeUtil.get_ofHeight_viewHolder(viewHolder).build()
}