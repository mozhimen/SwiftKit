package com.mozhimen.xmlk.bases

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.mozhimen.xmlk.commons.ILayoutK

/**
 * @ClassName BaseTextK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/22
 * @Version 1.0
 */
open class BaseTextK : AppCompatTextView, ILayoutK {
    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)
}