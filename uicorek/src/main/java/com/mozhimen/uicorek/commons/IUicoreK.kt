package com.mozhimen.uicorek.commons

import android.util.AttributeSet
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName IUicoreK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/7 18:16
 * @Version 1.0
 */
interface IUicoreK : IUtilK {
    fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {}
    fun initAttrs(attrs: AttributeSet?) {}
    fun initView() {}
}