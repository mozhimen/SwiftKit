package com.mozhimen.xmlk.bases

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.motion.widget.MotionLayout
import com.mozhimen.xmlk.commons.ILayoutK

/**
 * @ClassName LayoutKFrame
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
abstract class BaseLayoutKMotion : MotionLayout, ILayoutK {

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)
}