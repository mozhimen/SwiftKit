package com.mozhimen.basick.utilk.google.material

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout

/**
 * @ClassName UtilKAppBarLayout
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/25
 * @Version 1.0
 */
object UtilKAppBarLayout {
    @JvmStatic
    fun requireLayoutParams_ofCoordinatorLayout(appBarLayout: AppBarLayout): CoordinatorLayout.LayoutParams? {
        val layoutParams = appBarLayout.layoutParams ?: return null
        if (layoutParams !is CoordinatorLayout.LayoutParams)
            throw IllegalStateException("Make sure you are using the LinearLayoutManager")
        return layoutParams
    }

    @JvmStatic
    fun scroll2top(appBarLayout: AppBarLayout) {
        when(val layoutParams = appBarLayout.layoutParams){
            is CoordinatorLayout.LayoutParams -> {
                val behavior = layoutParams.behavior
                if (behavior is AppBarLayout.Behavior) {
                    if (behavior.topAndBottomOffset != 0)
                        behavior.setTopAndBottomOffset(0)
                }
            }
        }
    }
}