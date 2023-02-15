package com.mozhimen.uicorek.viewk.bases

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.uicorek.viewk.commons.IViewK

/**
 * @ClassName ICustomView
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/7 18:01
 * @Version 1.0
 */
abstract class BaseViewK :
    View, IViewK {

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    val TAG = "${this.javaClass.simpleName}>>>>>"

    var centerX = 0f
    var centerY = 0f
    var sideLength = 0f//真实半径
    var realRadius = sideLength / 2f//最短边长

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        centerX = width / 2f
        centerY = height / 2f
        realRadius = width.coerceAtMost(height).toFloat() / 2f
        sideLength = width.coerceAtMost(height).toFloat()
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun initFlag() {}

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {}

    override fun initData() {}

    override fun initPaint() {}

    override fun initView() {}

}