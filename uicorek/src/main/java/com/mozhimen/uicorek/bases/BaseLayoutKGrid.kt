package com.mozhimen.uicorek.bases

import android.content.Context
import android.util.AttributeSet
import android.widget.GridLayout
import com.mozhimen.uicorek.commons.ILayoutK

/**
 * @ClassName BaseLayoutKGrid
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/29 0:04
 * @Version 1.0
 */
abstract class BaseLayoutKGrid @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    GridLayout(context, attrs, defStyleAttr), ILayoutK {
}