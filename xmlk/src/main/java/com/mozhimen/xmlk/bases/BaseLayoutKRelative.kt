package com.mozhimen.xmlk.bases

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.mozhimen.xmlk.commons.ILayoutK

/**
 * @ClassName LayoutKRelative
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
abstract class BaseLayoutKRelative @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RelativeLayout(context, attrs, defStyleAttr), ILayoutK {
}