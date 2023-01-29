package com.mozhimen.uicorek.layoutk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.GridLayout
import com.mozhimen.basick.animk.builders.AnimKBuilder
import com.mozhimen.basick.animk.builders.temps.BackgroundColorAnimatorType
import com.mozhimen.basick.elemk.mos.MArea
import com.mozhimen.basick.utilk.UtilKGesture
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.basick.utilk.exts.colorStr2Int
import com.mozhimen.basick.utilk.exts.dp2px
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

    private val _gridRowCount = 15 //行数量
    private val _gridColumnCount = 10 //列数量
    private val _gridColor = UtilKRes.getColor(R.color.blue_light)
    private val _cellDefaultColor = "#D8D5D7".colorStr2Int()    //默认格子颜色
    private val _cellHoverColor = "#A6D8A9".colorStr2Int()    //滑动时进入格子的暂时颜色
    private val _cellSelectColor = "#4FD855".colorStr2Int()    //选中的颜色
    private val _innerLineWidth = 2f.dp2px().toFloat()
    private val _outerLineWidth = 4f.dp2px().toFloat()

    private lateinit var _cells: Array<Array<View>>    //二维格子
    private val _areaGrid by lazy { MArea(width - paddingLeft - paddingRight, height - paddingTop - paddingBottom, left + paddingLeft, top + paddingTop) }
    private val _areaCell by lazy { MArea(width / _gridColumnCount, height / _gridRowCount) }

    private val _gridLayout by lazy {
        GridLayout(context).apply {
            layoutParams = LayoutParams(_areaGrid.width, _areaGrid.height)
            setBackgroundColor(_gridColor)
            orientation = GridLayout.HORIZONTAL
            columnCount = _gridColumnCount
            rowCount = _gridRowCount
        }
    }

    private var _cellLast: View? = null    //上一个  view

    init {
        post {
            addView(_gridLayout)
            initCells()
        }
        setWillNotDraw(false)
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
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                val x = event.rawX
                val y = event.rawY
                if (UtilKGesture.isTapInArea(x, y, _areaGrid.left.toFloat(), _areaGrid.top.toFloat(), _areaGrid.right.toFloat(), _areaGrid.bottom.toFloat())) {
                    startGestureMoveAnim(x, y)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun startGestureMoveAnim(x: Float, y: Float) {
        //计算框内位置，+ 格子的一半，等于中心点距离边上的位置
        val nx = x - _gridLayout.x + (_areaCell.width / 2)
        val ny = y - _gridLayout.y + (_areaCell.height / 2)

        //计算索引位置
        val i = (nx / _areaCell.width).toInt()
        val j = (ny / _areaCell.height).toInt()

        if (j >= _gridRowCount || i >= _gridColumnCount) return
        val cell = _cells[j][i]
        if (_cellLast != null && _cellLast == cell) return
        if (cell.tag == Status.HOVER || cell.tag == Status.SELECTED) return

        _cellLast?.run {
            tag = Status.DEFAULT
            startBackgroundAnim(this, _cellHoverColor, _cellDefaultColor)
        }
        _cellLast = cell
        cell.tag = Status.HOVER
        startBackgroundAnim(cell, _cellDefaultColor, _cellHoverColor)
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