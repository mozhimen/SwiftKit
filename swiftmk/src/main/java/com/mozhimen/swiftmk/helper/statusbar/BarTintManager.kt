package com.mozhimen.swiftmk.helper.statusbar

import android.app.Activity
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout

/**
 * @ClassName BarTintManager
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:14
 * @Version 1.0
 */
class BarTintManager(activity: Activity) {
    private var xBarAvailable = false
    private var xBarTintEnabled = false
    private lateinit var xBarTintView: View

    companion object {
        const val DEFAULT_TINT_COLOR = -0x67000000
    }

    init {
        val window = activity.window
        //获取DecorView
        val viewGroup = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //检查主题中是否有透明的状态栏

            //检查主题中是否有透明的状态栏
            val attrs = intArrayOf(android.R.attr.windowTranslucentStatus)
            val a = activity.obtainStyledAttributes(attrs)
            try {
                xBarAvailable = a.getBoolean(0, false)
            } finally {
                a.recycle()
            }
            val winParams = window.attributes
            val bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            if (winParams.flags and bits != 0) {
                xBarAvailable = true
            }
        }

        if (xBarAvailable) {
            setupBarView(activity, viewGroup as ViewGroup)
        }
    }

    /**
     * 初始化状态栏
     */
    private fun setupBarView(activity: Activity, viewGroup: ViewGroup) {
        xBarTintView = View(activity)
        //设置高度匹配StatusBar的高度
        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getBarHeight(activity))
        layoutParams.gravity = Gravity.TOP
        xBarTintView.layoutParams = layoutParams
        xBarTintView.setBackgroundColor(DEFAULT_TINT_COLOR)
        //默认不显示
        xBarTintView.visibility = View.GONE
        //decorView添加状态栏高度的View
        viewGroup.addView(xBarTintView)
    }

    /**
     * 获取状态栏高度
     */
    private fun getBarHeight(activity: Activity): Int {
        var barHeight = 0
        val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            barHeight = activity.resources.getDimensionPixelSize(resourceId)
        }
        return barHeight
    }

    /**
     * 显示状态栏
     */
    fun setBarTintEnabled(enabled: Boolean) {
        xBarTintEnabled = enabled
        if (xBarTintEnabled)
            xBarTintView.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    /**
     * 设置状态栏颜色
     */
    fun setBarTintColor(colorId: Int) {
        if (xBarAvailable) xBarTintView.setBackgroundColor(colorId)
    }
}