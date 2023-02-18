package com.mozhimen.uicorek.layoutk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.GridLayout
import com.mozhimen.basick.animk.builder.AnimKBuilder
import com.mozhimen.basick.animk.builder.temps.BackgroundColorAnimatorType
import com.mozhimen.basick.elemk.mos.MArea
import com.mozhimen.basick.elemk.mos.MAreaF
import com.mozhimen.basick.utilk.UtilKGesture
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.basick.utilk.exts.normalize
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKFrame


/**
 * @ClassName LayoutKGridFill
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/29 16:45
 * @Version 1.0
 */
class LayoutKGridFill @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
    BaseLayoutKFrame(context, attrs, defStyleAttr, defStyleRes) {

    private var _gridRowCount = 10 //行数量
    private var _gridColumnCount = 10 //列数量
    private var _gridColor: Int = 0
    private var _cellDefaultColor: Int = 0    //默认格子颜色
    private var _cellHoverColor: Int = 0//滑动时进入格子的暂时颜色
    private var _cellSelectColor: Int = 0 //选中的颜色
    private var _lineColor: Int = 0
    private var _innerLineWidth = 0f
    private var _outerLineWidth = 0f

    init {
        if (!isInEditMode) {
            _gridColor = UtilKRes.getColor(R.color.blue_light)
            _cellDefaultColor = UtilKRes.getColor(R.color.gray_light)
            _cellHoverColor = UtilKRes.getColor(R.color.blue_light)
            _cellSelectColor = UtilKRes.getColor(R.color.blue_normal)
            _lineColor = UtilKRes.getColor(R.color.blue_dark)
            _innerLineWidth = 1f.dp2px().toFloat()
            _outerLineWidth = 2f.dp2px().toFloat()
            initAttrs(attrs, defStyleAttr)
            post {
                addView(_gridLayout)
                (_gridLayout.layoutParams as LayoutParams).gravity = Gravity.CENTER
                initCells()
                invalidate()
            }
        }
    }

    private lateinit var _cells: Array<Array<View>>    //二维格子
    private val _areaCell by lazy { MArea((width - paddingLeft - paddingRight) / _gridColumnCount, (height - paddingTop - paddingBottom) / _gridRowCount) }
    private val _areaGrid by lazy {
        MAreaF(
            (_areaCell.width * _gridColumnCount).toFloat(), (_areaCell.height * _gridRowCount).toFloat(),
            paddingLeft.toFloat() + (this.width.toFloat() - (_areaCell.width * _gridColumnCount).toFloat()) / 2f, paddingTop.toFloat() + (this.height.toFloat() - (_areaCell.height * _gridRowCount).toFloat()) / 2f
        )
    }

    private val _gridLayout by lazy {
        GridLayout(context).apply {
            layoutParams = LayoutParams(_areaGrid.width.toInt(), _areaGrid.height.toInt())
            setBackgroundColor(_gridColor)
            orientation = GridLayout.HORIZONTAL
            columnCount = _gridColumnCount
            rowCount = _gridRowCount
        }
    }

    private val _linePaint by lazy {
        Paint().apply {
            color = _lineColor
            style = Paint.Style.STROKE
        }
    }

    private val _linePath = Path()

    private var _cellLast: View? = null    //上一个  view

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutKGridFill)
            _gridRowCount = typedArray.getInteger(R.styleable.LayoutKGridFill_layoutKGridFill_gridRowCount, _gridRowCount)
            _gridColumnCount = typedArray.getInteger(R.styleable.LayoutKGridFill_layoutKGridFill_gridColumnCount, _gridColumnCount)
            _gridColor = typedArray.getColor(R.styleable.LayoutKGridFill_layoutKGridFill_gridColor, _gridColor)
            _cellDefaultColor = typedArray.getColor(R.styleable.LayoutKGridFill_layoutKGridFill_cellDefaultColor, _cellDefaultColor)
            _cellHoverColor = typedArray.getColor(R.styleable.LayoutKGridFill_layoutKGridFill_cellHoverColor, _cellHoverColor)
            _cellSelectColor = typedArray.getColor(R.styleable.LayoutKGridFill_layoutKGridFill_cellSelectColor, _cellSelectColor)
            _lineColor = typedArray.getColor(R.styleable.LayoutKGridFill_layoutKGridFill_lineColor, _lineColor)
            _innerLineWidth = typedArray.getDimension(R.styleable.LayoutKGridFill_layoutKGridFill_innerLineWidth, _innerLineWidth)
            _outerLineWidth = typedArray.getDimension(R.styleable.LayoutKGridFill_layoutKGridFill_outerLineWidth, _outerLineWidth)
            typedArray.recycle()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initCells() {
        _cells = Array(_gridRowCount, init = {
            //列
            Array(_gridColumnCount, init = {
                View(context).apply {
                    tag = Status.DEFAULT
                    this.layoutParams = LayoutParams(_areaCell.width, _areaCell.height)
                    setBackgroundColor(_cellDefaultColor)
                    setOnClickListener {
                        if (tag == Status.SELECTED) return@setOnClickListener
                        when (tag) {
                            Status.DEFAULT -> {
                                startBackgroundAnim(this, _cellDefaultColor, _cellSelectColor)
                            }
                            Status.HOVER -> {
                                startBackgroundAnim(this, _cellHoverColor, _cellSelectColor)
                            }
                        }
                        tag = Status.SELECTED
                    }
                    setOnLongClickListener {
                        if (tag == Status.DEFAULT) return@setOnLongClickListener true
                        when (tag) {
                            Status.SELECTED -> {
                                startBackgroundAnim(this, _cellSelectColor, _cellDefaultColor)
                            }
                            Status.HOVER -> {
                                startBackgroundAnim(this, _cellHoverColor, _cellDefaultColor)
                            }
                        }
                        tag = Status.DEFAULT
                        return@setOnLongClickListener true
                    }
                }
            })
        })
        for (i in _cells.indices) {
            for (j in 0 until _cells[i].size) {
                _gridLayout.addView(_cells[i][j])
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                if (UtilKGesture.isTapInArea(x, y, _areaGrid.left, _areaGrid.top, _areaGrid.right, _areaGrid.bottom)) {
                    startGestureMoveAnim(x, y)
                }
            }
            MotionEvent.ACTION_UP -> {
                var cell: View
                for (i in _cells.indices) {
                    for (j in 0 until _cells[i].size) {
                        cell = _cells[i][j]
                        if (cell.tag == Status.HOVER) {
                            cell.tag = Status.DEFAULT
                            startBackgroundAnim(cell, _cellHoverColor, _cellDefaultColor)
                        }
                    }
                }
                _cellLast = null
            }
        }
        return super.dispatchTouchEvent(event)
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)

        val left: Float = _areaGrid.left
        val top: Float = _areaGrid.top
        val right: Float = _areaGrid.right
        val bottom: Float = _areaGrid.bottom
        for (i in 0.._gridRowCount) {
            val y = (_areaCell.height * i) + top
            _linePaint.strokeWidth = if (i == 0 || i == _gridRowCount) _outerLineWidth else _innerLineWidth
            _linePath.moveTo(left, y)
            _linePath.lineTo(left + _areaGrid.width, y)
            canvas.drawPath(_linePath, _linePaint)
            _linePath.reset()
        }
        for (i in 0.._gridColumnCount) {
            val x = (_areaCell.width * i) + left
            _linePaint.strokeWidth = if (i == 0 || i == _gridColumnCount) _outerLineWidth else _innerLineWidth
            _linePath.moveTo(x, top)
            _linePath.lineTo(x, top + _areaGrid.height)
            canvas.drawPath(_linePath, _linePaint)
            _linePath.reset()
        }
    }

    private fun startGestureMoveAnim(x: Float, y: Float) {
        val i = (x / _areaCell.width).toInt().normalize(0, _gridColumnCount - 1)        //计算索引位置
        val j = (y / _areaCell.height).toInt().normalize(0, _gridRowCount - 1)
        if (j >= _gridRowCount || i >= _gridColumnCount) return
        val cell = _cells[j][i]
        if (_cellLast != null && _cellLast == cell) return
        if (cell.tag == Status.HOVER || cell.tag == Status.SELECTED) return

        _cellLast?.run {
            tag = Status.DEFAULT
            setBackgroundColor(_cellDefaultColor)
        }
        _cellLast = cell
        cell.tag = Status.HOVER
        cell.setBackgroundColor(_cellHoverColor)
    }

    private fun startBackgroundAnim(view: View, vararg color: Int) {
        AnimKBuilder.asAnimator().add(BackgroundColorAnimatorType().setColors(*color).setView(view)).setDuration(500).build().start()
    }

    private object Status {
        const val DEFAULT = 0
        const val HOVER = 1
        const val SELECTED = 2
    }
}