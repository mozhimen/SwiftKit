package com.mozhimen.uicorek.bases

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.mozhimen.uicorek.commons.ILayoutK

/**
 * @ClassName LayoutKFrame
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/24 14:39
 * @Version 1.0
 */
abstract class BaseLayoutKFrame @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr), ILayoutK {
}