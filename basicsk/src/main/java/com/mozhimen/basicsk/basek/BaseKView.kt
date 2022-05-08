package com.mozhimen.basicsk.basek

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.mozhimen.basicsk.extsk.dp2px
import com.mozhimen.basicsk.basek.commons.IBaseKView

/**
 * @ClassName ICustomView
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/7 18:01
 * @Version 1.0
 */
abstract class BaseKView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr), IBaseKView {

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

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {}

    override fun initData() {}

    override fun initPaint() {}

    override fun initView() {}

    override fun requireStart() {}

    override fun requireStop() {}
}