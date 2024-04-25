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
        AnimKBuilder.asAnimator().add(AnimKTypeUtil.get_ofHeight(view, startHeight, endHeight)).build().apply {
            addListener(BaseLayoutParamsAnimatorListenerAdapter(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        }

    @JvmStatic
    fun get_ofHeight_viewHolder(view: View, viewHolder: ViewHolder, startHeight: Int, endHeight: Int): Animator =
        get_ofHeight(view, startHeight, endHeight).apply {
            addListener(BaseViewHolderAnimatorListenerAdapter(viewHolder))//设定该Item在动画开始结束和取消时能否被recycle
        }

    @JvmStatic
    fun get_ofHeight_viewHolder(viewHolder: ViewHolder): Animator {
        val parent = viewHolder.itemView.parent as View
        val startHeight = viewHolder.itemView.measuredHeight//测量扩展动画的起始高度和结束高度
        viewHolder.itemView.measure(View.MeasureSpec.makeMeasureSpec(parent.measuredWidth, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        val endHeight = viewHolder.itemView.measuredHeight
        return get_ofHeight_viewHolder(viewHolder.itemView, viewHolder, startHeight, endHeight)
    }
}