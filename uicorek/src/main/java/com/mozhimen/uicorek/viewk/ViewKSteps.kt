package com.mozhimen.uicorek.viewk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.sp2px
import com.mozhimen.basick.basek.view.BaseKView
import com.mozhimen.uicorek.R

/**
 * @ClassName ViewKSteps
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/25 22:09
 * @Version 1.0
 */
class ViewKSteps @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
    BaseKView(context, attrs, defStyleAttr, defStyleRes) {

    //region # variate
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
    private var _numberTextSize = 15f.sp2px()
    private var _titleTextSize = 15f.sp2px()

    private lateinit var _circlePaint: Paint
    private lateinit var _linePaint: Paint
    private lateinit var _numberPaint: Paint
    private lateinit var _titlePaint: Paint

    private var _leftX = 0f
    private var _rightX = 0f
    private var _leftY = 0f
    private var _rightY = 0f

    private var _realCenterY = 0f
    private var _circleDistance = 0f
    private var _position = 0

    private lateinit var _titles: Array<String>
    //endregion

    init {
        initAttrs(attrs, defStyleAttr)
        initPaint()
    }

    /**
     * 设置titles
     * @param titles Array<String>
     */
    fun setTitles(titles: Array<String>) {
        this._titles = titles
    }

    /**
     * 去指定的标签
     * @param position Int
     */
    fun goto(position: Int) {
        if (position == 0) {
            reset()
            return
        }

        if (position > 0 && position <= _titles.size) {
            _position = position
            postInvalidate()
        }
    }

    /**
     * 下一步
     */
    fun next() {
        if (_position < _titles.size) {
            _position++
            postInvalidate()
        }
    }

    /**
     * 上一步
     */
    fun back() {
        if (_position > 0) {
            _position--
            postInvalidate()
        }
    }

    /**
     * 重置
     */
    fun reset() {
        if (_position != 0) {
            _position = 0
            postInvalidate()
        }
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewKSteps)
        _margin = typedArray.getDimensionPixelOffset(R.styleable.ViewKSteps_viewKSteps_margin, _margin)
        _titleMarginTop = typedArray.getDimensionPixelOffset(R.styleable.ViewKSteps_viewKSteps_titleMarginTop, _titleMarginTop)
        _doneLineColor = typedArray.getColor(R.styleable.ViewKSteps_viewKSteps_doneLineColor, _doneLineColor)
        _undoLineColor = typedArray.getColor(R.styleable.ViewKSteps_viewKSteps_undoLineColor, _undoLineColor)

        _doneNumberColor = typedArray.getColor(R.styleable.ViewKSteps_viewKSteps_doneNumberColor, _doneNumberColor)
        _undoNumberColor = typedArray.getColor(R.styleable.ViewKSteps_viewKSteps_undoNumberColor, _undoNumberColor)

        _doneTitleColor = typedArray.getColor(R.styleable.ViewKSteps_viewKSteps_doneTitleColor, _doneTitleColor)
        _undoTitleColor = typedArray.getColor(R.styleable.ViewKSteps_viewKSteps_undoTitleColor, _undoTitleColor)

        _circleRadius = typedArray.getDimensionPixelSize(R.styleable.ViewKSteps_viewKSteps_circleRadius, _circleRadius)
        _lineWidth = typedArray.getDimensionPixelOffset(R.styleable.ViewKSteps_viewKSteps_lineWidth, _lineWidth)
        _numberTextSize = typedArray.getDimensionPixelSize(R.styleable.ViewKSteps_viewKSteps_numberTextSize, _numberTextSize)
        _titleTextSize = typedArray.getDimensionPixelSize(R.styleable.ViewKSteps_viewKSteps_titleTextSize, _titleTextSize)
        typedArray.recycle()
    }

    override fun initPaint() {
        //初始化线条画笔
        _linePaint = Paint()
        _linePaint.isAntiAlias = true
        _linePaint.style = Paint.Style.FILL

        //初始化圆画笔
        _circlePaint = Paint()
        _circlePaint.isAntiAlias = true
        _circlePaint.style = Paint.Style.FILL

        //初始化圆内数字画笔
        _numberPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DEV_KERN_TEXT_FLAG)
        _numberPaint.textSize = _numberTextSize.toFloat()
        _numberPaint.typeface = Typeface.DEFAULT_BOLD // 采用默认的宽度
        _numberPaint.color = _undoNumberColor

        //初始化圆标题画笔
        _titlePaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DEV_KERN_TEXT_FLAG)
        _titlePaint.textSize = _titleTextSize.toFloat()
        _titlePaint.typeface = Typeface.DEFAULT_BOLD // 采用默认的宽度
        _titlePaint.color = _undoTitleColor
    }

    override fun initData() {
        _realCenterY = _circleRadius + paddingTop + _margin.toFloat()
        _leftX = (this.left + paddingLeft + _margin + _circleRadius).toFloat()
        _leftY = _realCenterY - _lineWidth / 2f
        _rightX = (this.right - paddingRight - _margin - _circleRadius).toFloat()
        _rightY = _realCenterY + _lineWidth / 2f
        _circleDistance = (width - 2 * _leftX) / (_titles.size - 1) //计算每个圆之间的相距距离
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initData()
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
            _titlePaint.getTextBounds(_titles[i], 0, _titles[i].length, rect) //获取标题文字相关宽高属性
            if (i == _position - 1) {
                _titlePaint.color = _doneTitleColor
            } else {
                _titlePaint.color = _undoTitleColor
            }
            when (i) {
                0 -> {
                    canvas.drawText(_titles[0], _leftX - rect.width() / 2, _realCenterY + _circleRadius + _titleMarginTop, _titlePaint)
                }
                _titles.size - 1 -> {
                    canvas.drawText(
                        _titles[i],
                        width - _leftX - (rect.width() / 2),
                        _realCenterY + _circleRadius + _titleMarginTop,
                        _titlePaint
                    )
                }
                else -> {
                    canvas.drawText(
                        _titles[i],
                        _leftX + i * _circleDistance - rect.width() / 2,
                        _realCenterY + _circleRadius + _titleMarginTop,
                        _titlePaint
                    )
                }
            }
        }
    }

    private fun drawNumbers(canvas: Canvas) {
        _titles.indices.forEach { i ->
            val str = (i + 1).toString() //声明步骤数
            val rect = Rect()
            _numberPaint.getTextBounds(str, 0, str.length, rect) //获取步骤数文字相关宽高属性
            if (i <= _position - 1) {
                _circlePaint.color = _doneNumberColor
            } else {
                _circlePaint.color = _undoNumberColor
            }
            canvas.drawText(str, _leftX + i * _circleDistance - rect.width() / 2, _realCenterY + rect.height() / 2, _numberPaint) //开始写圆内步骤数
        }
    }

    private fun drawCircles(canvas: Canvas) {
        _titles.indices.forEach { i ->
            if (i <= _position - 1) {
                _circlePaint.color = _doneLineColor
            } else {
                _circlePaint.color = _undoLineColor
            }

            canvas.drawCircle(_leftX + i * _circleDistance, _realCenterY, _circleRadius.toFloat(), _circlePaint)
        }

    }

    private fun drawLines(canvas: Canvas) {
        _titles.indices.forEach { i ->
            when (i) {
                0 -> {
                    if (_position == 0) {
                        _linePaint.color = _undoLineColor
                    } else {
                        _linePaint.color = _doneLineColor
                    }
                    canvas.drawRect(
                        _leftX,
                        _leftY,
                        _leftX + _circleDistance / 2,
                        _rightY,
                        _linePaint
                    )
                }
                _titles.size - 1 -> {
                    if (_position == _titles.size) {
                        _linePaint.color = _doneLineColor
                    } else {
                        _linePaint.color = _undoLineColor
                    }
                    canvas.drawRect(
                        _rightX - _circleDistance / 2,
                        _leftY,
                        _rightX,
                        _rightY,
                        _linePaint
                    )
                }
                else -> {
                    if (i <= _position - 1) {
                        _linePaint.color = _doneLineColor
                    } else {
                        _linePaint.color = _undoLineColor
                    }
                    canvas.drawRect(
                        _leftX + _circleDistance * (i - 0.5f),
                        _leftY,
                        _leftX + _circleDistance * (i + 0.5f),
                        _rightY,
                        _linePaint
                    )
                }
            }
        }
    }
}