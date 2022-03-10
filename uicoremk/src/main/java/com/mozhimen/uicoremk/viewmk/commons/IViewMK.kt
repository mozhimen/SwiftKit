package com.mozhimen.uicoremk.viewmk.commons

import android.util.AttributeSet

/**
 * @ClassName IViewMK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/7 18:16
 * @Version 1.0
 */
interface IViewMK {
    fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int)
    fun initPaint()
    fun initData()
    fun initView()
    fun requireStart()
    fun requireStop()
}