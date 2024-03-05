package com.mozhimen.xmlk.commons

import android.util.AttributeSet
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName IXmlK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/7 18:16
 * @Version 1.0
 */
interface IXmlK : IUtilK {
    fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {}
    fun initAttrs(attrs: AttributeSet?) {}
    fun initView() {}
}