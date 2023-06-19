package com.mozhimen.uicorek.layoutk

import android.content.Context
import android.util.AttributeSet
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKFrame

/**
 * @ClassName LayoutKSquare
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/24 23:24
 * @Version 1.0
 */
class LayoutKSquare @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : BaseLayoutKFrame(context, attrs, defStyleAttr) {
    companion object {
        private const val MEASURE_MODE_MIN = 0
        private const val MEASURE_MODE_MAX = 1
        private const val MEASURE_MODE_WIDTH = 2
        private const val MEASURE_MODE_HEIGHT = 3
    }

    private var _measureMode = MEASURE_MODE_MAX

    init {
        initAttrs(attrs, defStyleAttr)
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutKSquare)
            _measureMode = typedArray.getInt(R.styleable.LayoutKSquare_layoutKSquare_measureMode, _measureMode)
            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val maxSpec = if (widthSize > heightSize) widthMeasureSpec else heightMeasureSpec
        val minSpec = if (widthSize > heightSize) heightMeasureSpec else widthMeasureSpec
        when (_measureMode) {
            MEASURE_MODE_WIDTH -> super.onMeasure(widthMeasureSpec, widthMeasureSpec)
            MEASURE_MODE_HEIGHT -> super.onMeasure(heightMeasureSpec, heightMeasureSpec)
            MEASURE_MODE_MAX -> super.onMeasure(maxSpec, maxSpec)
            MEASURE_MODE_MIN -> super.onMeasure(minSpec, minSpec)
        }
    }
}