package com.mozhimen.basick.basek.commons

import android.util.AttributeSet

/**
 * @ClassName IViewK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/7 18:16
 * @Version 1.0
 */
interface IBaseKView {
    fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int)
    fun initPaint()
    fun initData()
    fun initView()
}