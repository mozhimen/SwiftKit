package com.mozhimen.xmlk.bases

import android.content.Context
import android.util.AttributeSet
import android.widget.GridLayout
import androidx.annotation.RequiresApi
import com.mozhimen.kotlin.elemk.android.os.cons.CVersCode
import com.mozhimen.xmlk.commons.ILayoutK

/**
 * @ClassName BaseLayoutKGrid
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/29 0:04
 * @Version 1.0
 */
abstract class BaseLayoutKGrid : GridLayout, ILayoutK {

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    @RequiresApi(CVersCode.V_21_5_L)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)
}