package com.mozhimen.basick.utilk.google.material

import androidx.coordinatorlayout.widget.CoordinatorLayout
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
    fun scroll2top(appBarLayout: AppBarLayout) {
        if (appBarLayout.layoutParams is CoordinatorLayout.LayoutParams) {
            val behavior = (appBarLayout.layoutParams as CoordinatorLayout.LayoutParams).behavior
            if (behavior is AppBarLayout.Behavior) {
                val topAndBottomOffset = behavior.topAndBottomOffset
                if (topAndBottomOffset != 0) {
                    behavior.setTopAndBottomOffset(0)
                }
            }
        }
    }
}