package com.mozhimen.uicoremk.viewmk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.mozhimen.basicsmk.utilmk.dp2px
import com.mozhimen.basicsmk.utilmk.sp2px
import com.mozhimen.uicoremk.R
import com.mozhimen.uicoremk.viewmk.commons.ViewMK

/**
 * @ClassName ViewMKSteps
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/25 22:09
 * @Version 1.0
 */
class ViewMKSteps @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ViewMK(context, attrs, defStyleAttr) {

    private var _doneLineColor = ContextCompat.getColor(context, R.color.blue_normal)
    private var _undoLineColor = ContextCompat.getColor(context, R.color.blue_light)
    private var _doneNumberColor = ContextCompat.getColor(context, android.R.color.white)
    private var _undoNumberColor = ContextCompat.getColor(context, android.R.color.white)
    private var _undoTitleColor = ContextCompat.getColor(context, android.R.color.black)
    private var _doneTitleColor = ContextCompat.getColor(context, R.color.blue_light)

    private var _margin = 20f.dp2px()
    private var _titleMarginTop = 10f.dp2px()
    private var _circleRadius = 30f.dp2px()
    private var _lineWidth = 5f.dp2px()
    private var _numberTextSize = 18f.sp2px()
    private var _titleTextSize = 18f.sp2px()

    private lateinit var circlePaint: Paint
    private lateinit var linePaint: Paint
    private lateinit var numberPaint: Paint
    private lateinit var titlePaint: Paint

    private var _leftX = 0f
    private var _rightX = 0f
    private var _leftY = 0f
    private var _rightY = 0f

    private var _realCenterY = 0f
    private var _circleDistance = 0f
    private var _position = 0

    private lateinit var _titles: Array<String>

    //设置titles
    fun setTitles(titles: Array<String>) {
        this._titles = titles
    }

    fun goto(position: Int) {
        if (position == 0) {
            reset()
            return
        }

        if (position > 0 && position <= _titles.size) {
            _position = position
            invalidate()
        }
    }

    //下一步
    fun next() {
        if (_position < _titles.size) {
            _position++
            invalidate()
        }
    }

    //上一步
    fun back() {
        if (_position > 0) {
            _position--
            invalidate()
        }
    }

    //重置
    fun reset() {
        if (_position != 0) {
            _position = 0
            invalidate()
        }
    }

    init {
        initAttrs(attrs)
        initPaint()
    }

    override fun initAttrs(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ViewMKSteps)
            _margin = typedArray.getDimensionPixelOffset(R.styleable.ViewMKSteps_viewMKSteps_margin, _margin)
            _titleMarginTop = typedArray.getDimensionPixelOffset(R.styleable.ViewMKSteps_viewMKSteps_titleMarginTop, _titleMarginTop)
            _doneLineColor = typedArray.getColor(R.styleable.ViewMKSteps_viewMKSteps_doneLineColor, _doneLineColor)
            _undoLineColor = typedArray.getColor(R.styleable.ViewMKSteps_viewMKSteps_undoLineColor, _undoLineColor)

            _doneNumberColor = typedArray.getColor(R.styleable.ViewMKSteps_viewMKSteps_doneNumberColor, _doneNumberColor)
            _undoNumberColor = typedArray.getColor(R.styleable.ViewMKSteps_viewMKSteps_undoNumberColor, _undoNumberColor)

            _doneTitleColor = typedArray.getColor(R.styleable.ViewMKSteps_viewMKSteps_doneTitleColor, _doneTitleColor)
            _undoTitleColor = typedArray.getColor(R.styleable.ViewMKSteps_viewMKSteps_undoTitleColor, _undoTitleColor)

            _circleRadius = typedArray.getDimensionPixelSize(R.styleable.ViewMKSteps_viewMKSteps_circleRadius, _circleRadius)
            _lineWidth = typedArray.getDimensionPixelOffset(R.styleable.ViewMKSteps_viewMKSteps_lineWidth, _lineWidth)
            _numberTextSize = typedArray.getDimensionPixelSize(R.styleable.ViewMKSteps_viewMKSteps_numberTextSize, _numberTextSize)
            _titleTextSize = typedArray.getDimensionPixelSize(R.styleable.ViewMKSteps_viewMKSteps_titleTextSize, _titleTextSize)
            typedArray.recycle()
        }
    }

    override fun initPaint() {
        //初始化线条画笔
        linePaint = Paint()
        linePaint.isAntiAlias = true
        linePaint.style = Paint.Style.FILL

        //初始化圆画笔
        circlePaint = Paint()
        circlePaint.isAntiAlias = true
        circlePaint.style = Paint.Style.FILL

        //初始化圆内数字画笔
        numberPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DEV_KERN_TEXT_FLAG)
        numberPaint.textSize = _numberTextSize.toFloat()
        numberPaint.typeface = Typeface.DEFAULT_BOLD // 采用默认的宽度
        numberPaint.color = _undoNumberColor

        //初始化圆标题画笔
        titlePaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DEV_KERN_TEXT_FLAG)
        titlePaint.textSize = _titleTextSize.toFloat()
        titlePaint.typeface = Typeface.DEFAULT_BOLD // 采用默认的宽度
        titlePaint.color = _undoTitleColor
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        _realCenterY = _circleRadius + paddingTop + _margin.toFloat()
        _leftX = (this.left + paddingLeft + _margin + _circleRadius).toFloat()
        _leftY = _realCenterY - _lineWidth / 2f
        _rightX = (this.right - paddingRight - _margin - _circleRadius).toFloat()
        _rightY = _realCenterY + _lineWidth / 2f
        _circleDistance = (width - 2 * _leftX) / (_titles.size - 1) //计算每个圆之间的相距距离
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawLines(canvas)
        drawCircles(canvas)
        drawNumbers(canvas)
        drawTitles(canvas)
    }

    private fun drawTitles(canvas: Canvas) {
        _titles.indices.forEach { i ->
            val rect = Rect()
            titlePaint.getTextBounds(_titles[i], 0, _titles[i].length, rect) //获取标题文字相关宽高属性
            if (i == _position - 1) {
                titlePaint.color = _doneTitleColor
            } else {
                titlePaint.color = _undoTitleColor
            }
            when (i) {
                0 -> {
                    canvas.drawText(_titles[0], _leftX - rect.width() / 2, _realCenterY + _circleRadius + _titleMarginTop, titlePaint)
                }
                _titles.size - 1 -> {
                    canvas.drawText(
                        _titles[i],
                        width - _leftX - (rect.width() / 2),
                        _realCenterY + _circleRadius + _titleMarginTop,
                        titlePaint
                    )
                }
                else -> {
                    canvas.drawText(
                        _titles[i],
                        _leftX + i * _circleDistance - rect.width() / 2,
                        _realCenterY + _circleRadius + _titleMarginTop,
                        titlePaint
                    )
                }
            }
        }
    }

    private fun drawNumbers(canvas: Canvas) {
        _titles.indices.forEach { i ->
            val str = (i + 1).toString() //声明步骤数
            val rect = Rect()
            numberPaint.getTextBounds(str, 0, str.length, rect) //获取步骤数文字相关宽高属性
            if (i <= _position - 1) {
                circlePaint.color = _doneNumberColor
            } else {
                circlePaint.color = _undoNumberColor
            }
            canvas.drawText(str, _leftX + i * _circleDistance - rect.width() / 2, _realCenterY + rect.height() / 2, numberPaint) //开始写圆内步骤数
        }
    }

    private fun drawCircles(canvas: Canvas) {
        _titles.indices.forEach { i ->
            if (i <= _position - 1) {
                circlePaint.color = _doneLineColor
            } else {
                circlePaint.color = _undoLineColor
            }

            canvas.drawCircle(_leftX + i * _circleDistance, _realCenterY, _circleRadius.toFloat(), circlePaint)
        }

    }

    private fun drawLines(canvas: Canvas) {
        _titles.indices.forEach { i ->
            when (i) {
                0 -> {
                    if (_position == 0) {
                        linePaint.color = _undoLineColor
                    } else {
                        linePaint.color = _doneLineColor
                    }
                    canvas.drawRect(
                        _leftX,
                        _leftY,
                        _leftX + _circleDistance / 2,
                        _rightY,
                        linePaint
                    )
                }
                _titles.size - 1 -> {
                    if (_position == _titles.size) {
                        linePaint.color = _doneLineColor
                    } else {
                        linePaint.color = _undoLineColor
                    }
                    canvas.drawRect(
                        _rightX - _circleDistance / 2,
                        _leftY,
                        _rightX,
                        _rightY,
                        linePaint
                    )
                }
                else -> {
                    if (i <= _position - 1) {
                        linePaint.color = _doneLineColor
                    } else {
                        linePaint.color = _undoLineColor
                    }
                    canvas.drawRect(
                        _leftX + _circleDistance * (i - 0.5f),
                        _leftY,
                        _leftX + _circleDistance * (i + 0.5f),
                        _rightY,
                        linePaint
                    )
                }
            }
        }
    }
}