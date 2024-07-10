package com.mozhimen.xmlk.bases

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.xmlk.commons.IViewK

/**
 * @ClassName ICustomView
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/7 18:01
 * @Version 1.0
 */
abstract class BaseViewK : View, IViewK {

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    @RequiresApi(CVersCode.V_21_5_L)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    ////////////////////////////////////////////////////////////

    protected var widthF = 0f
    protected var heightF = 0f
    protected var centerX = 0f
    protected var centerY = 0f
    protected var sideLength = 0f//最短边长
    protected var realRadius = 0f//真实半径

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        widthF = w.toFloat() - paddingStart - paddingEnd
        heightF = h.toFloat() - paddingTop - paddingBottom
        centerX = widthF / 2f
        centerY = heightF / 2f
        sideLength = widthF.coerceAtMost(heightF)
        realRadius = sideLength / 2f
        super.onSizeChanged(w, h, oldw, oldh)
    }
}