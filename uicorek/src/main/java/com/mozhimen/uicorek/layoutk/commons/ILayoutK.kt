package com.mozhimen.uicorek.layoutk.commons

import android.util.AttributeSet

/**
 * @ClassName ILayoutK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/24 15:45
 * @Version 1.0
 */
interface ILayoutK {
    fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int)
    fun initView()
}