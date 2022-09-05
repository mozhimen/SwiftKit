package com.mozhimen.basick.basek

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.mozhimen.basick.basek.commons.IBaseKLayout

/**
 * @ClassName ILayout
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/24 14:39
 * @Version 1.0
 */
abstract class BaseKLayoutFrame : FrameLayout, IBaseKLayout {

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    val TAG = "${this.javaClass.simpleName}>>>>>"

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {}
    override fun initView() {}
}