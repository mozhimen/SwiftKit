package com.mozhimen.basick.elemk.android.view

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import com.mozhimen.basick.utilk.android.view.UtilKStatusBar
import com.mozhimen.basick.utilk.android.view.UtilKDecorView
import com.mozhimen.basick.utilk.android.view.applyGone
import com.mozhimen.basick.utilk.android.view.applyVisibleIfElseGone

/**
 * @ClassName BarTintManager
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:14
 * @Version 1.0
 */
class ColorfulStatusBar(private val _activity: Activity) {
    private var _isColorfulStatusBarEnabled = false
    private lateinit var _colorfulStatusBarView: View

    init {
        if (UtilKStatusBar.isTranslucent(_activity)) {
            setupColorfulStatusBarView(_activity)
        }
    }

    fun setEnable(enabled: Boolean) {
        _isColorfulStatusBarEnabled = enabled
        _colorfulStatusBarView.applyVisibleIfElseGone(_isColorfulStatusBarEnabled)
    }

    fun setColor(@ColorInt intColor: Int) {
        if (UtilKStatusBar.isTranslucent(_activity)) _colorfulStatusBarView.setBackgroundColor(intColor)
    }

    private fun setupColorfulStatusBarView(activity: Activity) {
        _colorfulStatusBarView = View(activity)
        //设置高度匹配StatusBar的高度
        val layoutParams =
            FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, UtilKStatusBar.getHeight(false)).apply {
                gravity = Gravity.TOP
            }
        _colorfulStatusBarView.layoutParams = layoutParams
        _colorfulStatusBarView.setBackgroundColor(-0x67000000)
        //默认不显示
        _colorfulStatusBarView.applyGone()
        //decorView添加状态栏高度的View
        UtilKDecorView.getAsViewGroup(activity).addView(_colorfulStatusBarView)
    }
}