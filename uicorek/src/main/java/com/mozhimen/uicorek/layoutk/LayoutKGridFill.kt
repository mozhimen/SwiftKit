package com.mozhimen.uicorek.layoutk

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.GridLayout
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKFrame

/**
 * @ClassName LayoutKGridFill
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/28 22:22
 * @Version 1.0
 */
class LayoutKGridFill @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
    BaseLayoutKFrame(context, attrs, defStyleAttr, defStyleRes) {

    private val _gridRowCount = 15 //行数量
    private val _gridColumnCount = 10 //列数量
    private val defaultGridColor = Color.parseColor("#D8D5D7")    //默认格子颜色
    private val scrollSelectColor = Color.parseColor("#A6D8A9")    //滑动时选中的颜色
    private val selectColor = Color.parseColor("#4FD855")    //选中的颜色
    private val _dashLength = 3f.dp2px().toFloat()

    private lateinit var _array: Array<Array<View>>    //二维格子
    private var _preView: View? = null    //上一个  view
    private val _initPoint: Point = Point(30f.dp2px(), 50f.dp2px())    //初始位置
    private val _sizePoint: Point = Point(30f.dp2px(), 30f.dp2px())    //格子大小
    private val _downPoint: PointF = PointF(0f, 0f)    //按下位置
    private val _viewPoint: PointF = PointF(0f, 0f)    //View 位置
    private var _isDown = false    //是否为按下状态


    private val _path = Path()
    private val _paint by lazy {
        Paint().apply {
            color = Color.GRAY
            strokeWidth = 1f.dp2px().toFloat()
            style = Paint.Style.STROKE
            pathEffect = _dashPathEffect
        }
    }

    private val _dashPathEffect by lazy {
        DashPathEffect(
            floatArrayOf(_dashLength, _dashLength), _dashLength
        )
    }

    private val _sampleView by lazy {
        View(context).apply {
            val layoutParams = LayoutParams(_sizePoint.x, _sizePoint.y)
            layoutParams.marginStart = _initPoint.x
            layoutParams.topMargin = _initPoint.y
            this.layoutParams = layoutParams
            setBackgroundColor(selectColor)
        }
    }

    private val _layoutKGridFill by lazy {
        LayoutKGridUnTouch(context).apply {
            val layoutParams = LayoutParams(_sizePoint.x * _gridColumnCount, _sizePoint.y * _gridRowCount)
            layoutParams.marginStart = _initPoint.x
            layoutParams.topMargin = _initPoint.y + (_sizePoint.y * 2)

            this.layoutParams = layoutParams
            setBackgroundColor(Color.BLUE)
            orientation = GridLayout.HORIZONTAL
            columnCount = _gridColumnCount
            rowCount = _gridRowCount
        }
    }

    init {
        addView(_layoutKGridFill)
        addView(_sampleView)
        initGridView()
        setWillNotDraw(false)
    }

    private fun initGridView() {
        _array = Array(_gridRowCount,
            init = {
                //列
                Array(_gridColumnCount, init = {
                    View(context).apply {
                        val layoutParams = LayoutParams(30f.dp2px(), 30f.dp2px())
                        this.layoutParams = layoutParams
                        setBackgroundColor(defaultGridColor)
                    }
                })
            })
        for (i in _array.indices) {
            for (j in 0 until _array[i].size) {
                val v = _array[i][j]
                _layoutKGridFill.addView(v)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                _isDown = true
                invalidate()
                _downPoint.x = event.x
                _downPoint.y = event.y
                _viewPoint.x = _sampleView.x
                _viewPoint.y = _sampleView.y
            }
            MotionEvent.ACTION_MOVE -> {
                val x = (event.x - _downPoint.x) + _viewPoint.x
                val y = (event.y - _downPoint.y) + _viewPoint.y
                move(x, y)
                if (x > _layoutKGridFill.x && y > _layoutKGridFill.y && x < (_layoutKGridFill.width + _layoutKGridFill.x) && y < (_layoutKGridFill.height + _layoutKGridFill.y)) {
                    fillingGrid(x, y)
                }
            }
            MotionEvent.ACTION_UP -> {
                _isDown = false
                invalidate()
                val x = (event.x - _downPoint.x) + _viewPoint.x
                val y = (event.y - _downPoint.y) + _viewPoint.y
                _preView?.let {
                    if (x > _layoutKGridFill.x && y > _layoutKGridFill.y && x < (_layoutKGridFill.width + _layoutKGridFill.x) && y < (_layoutKGridFill.height + _layoutKGridFill.y)) {
                        it.tag = 1
                        setBgColor(it, scrollSelectColor, selectColor)
                    } else {
                        it.tag = 0
                        setBgColor(it, scrollSelectColor, defaultGridColor)
                    }
                }
                val animate = _sampleView.animate()
                animate.x(_initPoint.x.toFloat())
                animate.y(_initPoint.y.toFloat())
                animate.start()
                _preView = null
            }

        }

        return true
    }

    private fun setBgColor(view: View, vararg color: Int) {
        val colorAnim = ObjectAnimator.ofInt(view, "backgroundColor", *color)
        colorAnim.duration = 500
        colorAnim.setEvaluator(ArgbEvaluator())
        colorAnim.start()
    }

    private fun move(x: Float, y: Float) {
        //计算右边和下边边界
        val right = (width - _sizePoint.x)
        val bottom = (height - _sizePoint.y)
        //内部直接滑动
        if (x >= 0 && y >= 0 && x <= right && y <= bottom) {
            _sampleView.x = x
            _sampleView.y = y
            return
        }
        //-------------- 拖动边界判断
        if (x < 0 && y < 0) { //左上角
            _sampleView.x = 0f
            _sampleView.y = 0f
        } else if (x < 0 && y > bottom) { //左下角
            _sampleView.x = 0f
            _sampleView.y = bottom.toFloat()
        } else if (x > right && y < 0) { //右上角
            _sampleView.x = right.toFloat()
            _sampleView.y = 0f
        } else if (x > right && y > bottom) { //右下角
            _sampleView.x = right.toFloat()
            _sampleView.y = bottom.toFloat()
        } else if ((x > 0 && x < right) && y < 0) { // y越上界
            _sampleView.x = x
            _sampleView.y = 0f
        } else if ((x > 0 && x < right) && y > bottom) { // y越下界
            _sampleView.x = x
            _sampleView.y = bottom.toFloat()
        } else if ((y > 0 && y < bottom) && x < 0) { // x越左界
            _sampleView.x = 0f
            _sampleView.y = y
        } else if ((y > 0 && y < bottom) && x > right) { // x越右界
            _sampleView.x = right.toFloat()
            _sampleView.y = y
        }
    }

    private fun fillingGrid(x: Float, y: Float) {
        //计算框内位置，+ 格子的一半，等于中心点距离边上的位置
        val nx = x - _layoutKGridFill.x + (_sizePoint.x / 2)
        val ny = y - _layoutKGridFill.y + (_sizePoint.y / 2)

        //计算索引位置
        val i = (nx / _sizePoint.x).toInt()
        val j = (ny / _sizePoint.y).toInt()

        if (j >= _gridRowCount || i >= _gridColumnCount) return

        val v = _array[j][i]
        if (_preView != null && _preView == v) {
            return
        }
        if (v.tag == 1) {
            return
        }
        _preView?.run {
            tag = 0
            setBgColor(this, scrollSelectColor, defaultGridColor)
        }
        _preView = v
        v.tag = 1
        setBgColor(v, defaultGridColor, scrollSelectColor)
    }

    /**
     * 等别的 view 绘制完成后，在进行绘制，否则会被覆盖
     * @param canvas Canvas
     */
    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        if (!_isDown) return
        val initX = _initPoint.x.toFloat()
        val initY = _initPoint.y + (_sizePoint.y * 2).toFloat()
        val gridWidth = _layoutKGridFill.width
        val gridHeight = _layoutKGridFill.height
        for (i in 0.._gridRowCount) {
            val y = (_sizePoint.y * i) + initY
            _path.moveTo(initX, y)
            _path.lineTo(initX + gridWidth, y)
            canvas?.drawPath(_path, _paint)
            _path.reset()
        }
        for (i in 0.._gridColumnCount) {
            val x = (_sizePoint.x * i) + initX
            _path.moveTo(x, initY)
            _path.lineTo(x, initY + gridHeight)
            canvas?.drawPath(_path, _paint)
            _path.reset()
        }

    }
}

