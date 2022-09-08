package com.mozhimen.basick.basek.commons

import android.util.AttributeSet

/**
 * @ClassName IBaseKLayout
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 16:34
 * @Version 1.0
 */
interface IBaseKLayout {
    fun initFlag()
    fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int)
    fun initView()
}