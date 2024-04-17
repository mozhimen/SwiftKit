package com.mozhimen.xmlk.bases

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.xmlk.commons.ILayoutK

/**
 * @ClassName LayoutKLinear
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
abstract class BaseLayoutKLinear : LinearLayout, ILayoutK {

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    @RequiresApi(CVersCode.V_21_5_L)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)
}