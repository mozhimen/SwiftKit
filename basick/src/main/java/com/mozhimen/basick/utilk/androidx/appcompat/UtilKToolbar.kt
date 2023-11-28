package com.mozhimen.basick.utilk.androidx.appcompat

import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

/**
 * @ClassName UtilKToobar
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/11/16 11:49
 * @Version 1.0
 */
fun AppCompatActivity.getToolbar(): Toolbar =
    UtilKToolbar.getToolbar(this)

fun Toolbar.applyCustomView(customView: View, drawIcon: Drawable? = null) {
    UtilKToolbar.applyCustomView(this, customView, drawIcon)
}

object UtilKToolbar {
    @JvmStatic
    fun getToolbar(activity: AppCompatActivity): Toolbar =
        activity.findViewById(androidx.appcompat.R.id.action_bar)

    //////////////////////////////////////////////////////////////
    /**
     * 自定义标题
     */
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