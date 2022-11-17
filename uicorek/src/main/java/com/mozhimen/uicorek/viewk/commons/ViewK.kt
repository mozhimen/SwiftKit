package com.mozhimen.uicorek.viewk.commons

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.mozhimen.basick.extsk.dp2px

/**
 * @ClassName ICustomView
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/7 18:01
 * @Version 1.0
 */
abstract class ViewK :
    View, IViewK {

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    val TAG = "${this.javaClass.simpleName}>>>>>"

    var centerX = 30f.dp2px().toFloat()
    var centerY = 30f.dp2px().toFloat()
    var sideLength = 30f.dp2px()//真实半径
    var realRadius = sideLength / 2f//最短边长

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        centerX = width / 2f
        centerY = height / 2f
        realRadius = width.coerceAtMost(height) / 2f
        sideLength = width.coerceAtMost(height)
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun initFlag() {}

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {}

    override fun initData() {}

    override fun initPaint() {}

    override fun initView() {}

}