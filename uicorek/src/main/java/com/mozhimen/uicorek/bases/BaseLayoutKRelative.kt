package com.mozhimen.uicorek.bases

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.mozhimen.uicorek.commons.ILayoutK

/**
 * @ClassName LayoutKRelative
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/24 14:39
 * @Version 1.0
 */
abstract class BaseLayoutKRelative @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RelativeLayout(context, attrs, defStyleAttr), ILayoutK {
}