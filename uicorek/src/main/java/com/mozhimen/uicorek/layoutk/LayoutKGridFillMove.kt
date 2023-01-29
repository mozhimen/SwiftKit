package com.mozhimen.uicorek.layoutk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.GridLayout
import com.mozhimen.basick.animk.builders.AnimKBuilder
import com.mozhimen.basick.animk.builders.temps.BackgroundColorAnimatorType
import com.mozhimen.basick.elemk.mos.MArea
import com.mozhimen.basick.utilk.exts.colorStr2Int
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKFrame

/**
 * @ClassName LayoutKGridFill
 * @Description
 * 界面中的概念:
 * cell 单独的格子(这里指代View)
 * grid 网格(特指内部为空)
 * table 表格(cells+grid)
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/28 22:22
 * @Version 1.0
 */
class LayoutKGridFillMove @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
    BaseLayoutKFrame(context, attrs, defStyleAttr, defStyleRes) {

    //region # variate
    private val _gridRowCount = 15 //行数量
    private val _gridColumnCount = 10 //列数量
    private val _cellDefaultColor = "#D8D5D7".colorStr2Int()    //默认格子颜色
    private val _cellHoverColor = "#A6D8A9".colorStr2Int()    //滑动时进入格子的暂时颜色
    private val _cellSelectColor = "#4FD855".colorStr2Int()    //选中的颜色
    private val _dashLineWidth = 3f.dp2px().toFloat()

    private lateinit var _cells: Array<Array<View>>    //二维格子
    private var _cellLast: View? = null    //上一个  view

    private val _areaSampleCell: MArea = MArea(30f.dp2px(), 30f.dp2px(), 30f.dp2px(), 50f.dp2px())    //格子大小
    private val _locView: PointF = PointF(0f, 0f)    //View位置
    private val _locFingerDown: PointF = PointF(0f, 0f)    //按下位置
    private var _isFingerDown = false    //是否为按下状态

    private val _dashLinePath = Path()
    private val _dashLinePathEffect by lazy {
        DashPathEffect(
            floatArrayOf(_dashLineWidth, _dashLineWidth), _dashLineWidth
        )
    }
    private val _dashLinePaint by lazy {
        Paint().apply {
            color = Color.GRAY
            strokeWidth = 1f.dp2px().toFloat()
            style = Paint.Style.STROKE
            pathEffect = _dashLinePathEffect
        }
    }

    private val _sampleCell by lazy {
        View(context).apply {
            val layoutParams = LayoutParams(_areaSampleCell.width, _areaSampleCell.height)
            layoutParams.marginStart = _areaSampleCell.left
            layoutParams.topMargin = _areaSampleCell.top
            this.layoutParams = layoutParams
            setBackgroundColor(_cellSelectColor)
        }
    }

    private val _layoutKGridFill by lazy {
        LayoutKGridUnTouch(context).apply {
            val layoutParams = LayoutParams(_areaSampleCell.width * _gridColumnCount, _areaSampleCell.height * _gridRowCount)
            layoutParams.marginStart = _areaSampleCell.left
            layoutParams.topMargin = _areaSampleCell.top + (_areaSampleCell.height * 2)
            this.layoutParams = layoutParams
            setBackgroundColor(Color.BLUE)
            orientation = GridLayout.HORIZONTAL
            columnCount = _gridColumnCount
            rowCount = _gridRowCount
        }
    }
    //endregion

    init {
        addView(_layoutKGridFill)
        addView(_sampleCell)
        initCells()
    }

    private fun initCells() {
        _cells = Array(_gridRowCount, init = {
            //列
            Array(_gridColumnCount, init = {
                View(context).apply {
                    val layoutParams = LayoutParams(30f.dp2px(), 30f.dp2px())
                    this.layoutParams = layoutParams
                    setBackgroundColor(_cellDefaultColor)
                }
            })
        })
        var view: View
        for (i in _cells.indices) {
            for (j in 0 until _cells[i].size) {
                view = _cells[i][j]
                _layoutKGridFill.addView(view)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                _isFingerDown = true
                invalidate()
                _locFingerDown.x = event.x
                _locFingerDown.y = event.y
                _locView.x = _sampleCell.x
                _locView.y = _sampleCell.y
            }
            MotionEvent.ACTION_MOVE -> {
                val x = (event.x - _locFingerDown.x) + _locView.x
                val y = (event.y - _locFingerDown.y) + _locView.y
                moveSampleCell(x, y)
                if (x > _layoutKGridFill.x && y > _layoutKGridFill.y && x < (_layoutKGridFill.width + _layoutKGridFill.x) && y < (_layoutKGridFill.height + _layoutKGridFill.y)) {
                    fillingGrid(x, y)
                }
            }
            MotionEvent.ACTION_UP -> {
                _isFingerDown = false
                invalidate()
                val x = (event.x - _locFingerDown.x) + _locView.x
                val y = (event.y - _locFingerDown.y) + _locView.y
                _cellLast?.let {
                    if (x > _layoutKGridFill.x && y > _layoutKGridFill.y && x < (_layoutKGridFill.width + _layoutKGridFill.x) && y < (_layoutKGridFill.height + _layoutKGridFill.y)) {
                        it.tag = 1
                        setCellBackgroundColor(it, _cellHoverColor, _cellSelectColor)
                    } else {
                        it.tag = 0
                        setCellBackgroundColor(it, _cellHoverColor, _cellDefaultColor)
                    }
                }
                val animate = _sampleCell.animate()
                animate.x(_areaSampleCell.left.toFloat())
                animate.y(_areaSampleCell.top.toFloat())
                animate.start()
                _cellLast = null
            }

        }

        return true
    }

    private fun setCellBackgroundColor(view: View, vararg color: Int) {
        AnimKBuilder.asAnimator().add(BackgroundColorAnimatorType().setColors(*color).setView(view)).setDuration(500).build().start()
    }

    private fun moveSampleCell(x: Float, y: Float) {
        //计算右边和下边边界
        val right = (width - _areaSampleCell.width)
        val bottom = (height - _areaSampleCell.height)
        //内部直接滑动
        if (x >= 0 && y >= 0 && x <= right && y <= bottom) {
            _sampleCell.x = x
            _sampleCell.y = y
            return
        }
        //-------------- 拖动边界判断
        if (x < 0 && y < 0) { //左上角
            _sampleCell.x = 0f
            _sampleCell.y = 0f
        } else if (x < 0 && y > bottom) { //左下角
            _sampleCell.x = 0f
            _sampleCell.y = bottom.toFloat()
        } else if (x > right && y < 0) { //右上角
            _sampleCell.x = right.toFloat()
            _sampleCell.y = 0f
        } else if (x > right && y > bottom) { //右下角
            _sampleCell.x = right.toFloat()
            _sampleCell.y = bottom.toFloat()
        } else if ((x > 0 && x < right) && y < 0) { // y越上界
            _sampleCell.x = x
            _sampleCell.y = 0f
        } else if ((x > 0 && x < right) && y > bottom) { // y越下界
            _sampleCell.x = x
            _sampleCell.y = bottom.toFloat()
        } else if ((y > 0 && y < bottom) && x < 0) { // x越左界
            _sampleCell.x = 0f
            _sampleCell.y = y
        } else if ((y > 0 && y < bottom) && x > right) { // x越右界
            _sampleCell.x = right.toFloat()
            _sampleCell.y = y
        }
    }

    private fun fillingGrid(x: Float, y: Float) {
        //计算框内位置，+ 格子的一半，等于中心点距离边上的位置
        val nx = x - _layoutKGridFill.x + (_areaSampleCell.width / 2)
        val ny = y - _layoutKGridFill.y + (_areaSampleCell.height / 2)

        //计算索引位置
        val i = (nx / _areaSampleCell.width).toInt()
        val j = (ny / _areaSampleCell.height).toInt()

        if (j >= _gridRowCount || i >= _gridColumnCount) return

        val cell = _cells[j][i]
        if (_cellLast != null && _cellLast == cell) {
            return
        }
        if (cell.tag == 1) {
            return
        }
        _cellLast?.run {
            tag = 0
            setCellBackgroundColor(this, _cellHoverColor, _cellDefaultColor)
        }
        _cellLast = cell
        cell.tag = 1
        setCellBackgroundColor(cell, _cellDefaultColor, _cellHoverColor)
    }

    /**
     * 等别的 view 绘制完成后，在进行绘制，否则会被覆盖
     * @param canvas Canvas
     */
    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        //if (!_isFingerDown) return
        val initX = _areaSampleCell.left.toFloat()
        val initY = _areaSampleCell.top + (_areaSampleCell.height * 2).toFloat()
        val gridWidth = _layoutKGridFill.width
        val gridHeight = _layoutKGridFill.height
        for (i in 0.._gridRowCount) {
            val y = (_areaSampleCell.height * i) + initY
            _dashLinePath.moveTo(initX, y)
            _dashLinePath.lineTo(initX + gridWidth, y)
            canvas?.drawPath(_dashLinePath, _dashLinePaint)
            _dashLinePath.reset()
        }
        for (i in 0.._gridColumnCount) {
            val x = (_areaSampleCell.width * i) + initX
            _dashLinePath.moveTo(x, initY)
            _dashLinePath.lineTo(x, initY + gridHeight)
            canvas?.drawPath(_dashLinePath, _dashLinePaint)
            _dashLinePath.reset()
        }
    }
}

