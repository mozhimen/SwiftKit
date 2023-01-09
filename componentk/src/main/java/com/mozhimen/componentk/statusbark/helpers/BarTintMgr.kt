package com.mozhimen.componentk.statusbark.helpers

import android.app.Activity
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import com.mozhimen.basick.elemk.cons.VersionCode
import com.mozhimen.basick.utilk.bar.UtilKBarStatus

/**
 * @ClassName BarTintManager
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:14
 * @Version 1.0
 */
class BarTintMgr(activity: Activity) {
    private var _BarAvailable = false
    private var _BarTintEnabled = false
    private lateinit var _BarTintView: View

    companion object {
        const val DEFAULT_TINT_COLOR = -0x67000000
    }

    init {
        val window = activity.window
        //获取DecorView
        val viewGroup = window.decorView
        if (Build.VERSION.SDK_INT >= VersionCode.V_19_44_K) {
            //检查主题中是否有透明的状态栏
            val attrs = intArrayOf(android.R.attr.windowTranslucentStatus)
            val a = activity.obtainStyledAttributes(attrs)
            try {
                _BarAvailable = a.getBoolean(0, false)
            } finally {
                a.recycle()
            }
            val winParams = window.attributes
            val bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            if (winParams.flags and bits != 0) {
                _BarAvailable = true
            }
        }

        if (_BarAvailable) {
            setupBarView(activity, viewGroup as ViewGroup)
        }
    }

    /**
     * 初始化状态栏
     */
    private fun setupBarView(activity: Activity, viewGroup: ViewGroup) {
        _BarTintView = View(activity)
        //设置高度匹配StatusBar的高度
        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, UtilKBarStatus.getStatusBarHeight(true))
        layoutParams.gravity = Gravity.TOP
        _BarTintView.layoutParams = layoutParams
        _BarTintView.setBackgroundColor(DEFAULT_TINT_COLOR)
        //默认不显示
        _BarTintView.visibility = View.GONE
        //decorView添加状态栏高度的View
        viewGroup.addView(_BarTintView)
    }

    /**
     * 显示状态栏
     */
    fun setBarTintEnabled(enabled: Boolean) {
        _BarTintEnabled = enabled
        if (_BarTintEnabled)
            _BarTintView.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    /**
     * 设置状态栏颜色
     */
    fun setBarTintColor(colorId: Int) {
        if (_BarAvailable) _BarTintView.setBackgroundColor(colorId)
    }
}