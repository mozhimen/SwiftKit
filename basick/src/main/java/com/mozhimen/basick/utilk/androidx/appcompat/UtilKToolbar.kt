package com.mozhimen.basick.utilk.androidx.appcompat

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

/**
 * @ClassName UtilKToobar
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/11/16 11:49
 * @Version 1.0
 */
object UtilKToolbar {
    @JvmStatic
    fun get(activity: AppCompatActivity, @IdRes intResId: Int = androidx.appcompat.R.id.action_bar): Toolbar =
        activity.findViewById(intResId)

    //////////////////////////////////////////////////////////////

    //自定义标题
    @JvmStatic
    fun applyCustomView(toolbar: Toolbar, customView: View, drawIcon: Drawable? = null) {
        toolbar.removeAllViews()//移除掉所有的子View
        toolbar.setContentInsetsRelative(0, 0)//设置左右两边的边距为0
        toolbar.setContentInsetsAbsolute(0, 0)
        //layoutParams
        var layoutParams = customView.layoutParams//没有设置LayoutParams 创建一个占满父控件的LayoutParams
        if (layoutParams == null) {
            layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT)
        } else if (layoutParams !is Toolbar.LayoutParams) {
            layoutParams = Toolbar.LayoutParams(layoutParams)
        }
        customView.layoutParams = layoutParams
        toolbar.navigationIcon = drawIcon
        toolbar.addView(customView)
    }
}