package com.mozhimen.basick.basek.view

import android.util.AttributeSet
import com.mozhimen.basick.basek.commons.IBaseKView

/**
 * @ClassName BaseViewCallback
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/11 17:07
 * @Version 1.0
 */
open class BaseKViewCallback : IBaseKView {
    override fun initFlag() {}

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {}

    override fun initPaint() {}

    override fun initData() {}

    override fun initView() {}
}