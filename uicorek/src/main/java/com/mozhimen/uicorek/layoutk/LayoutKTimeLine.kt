package com.mozhimen.uicorek.layoutk

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKLinear


/**
 * @ClassName LayoutKTimeLine
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/1 14:00
 * @Version 1.0
 */
class LayoutKTimeLine @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : BaseLayoutKLinear(context, attrs, defStyleAttr) {

    private lateinit var _linePaint: Paint
    private lateinit var _pointPaint: Paint

    private lateinit var _attrs: Attrs
    private lateinit var _pointIcon: Bitmap
    private var _currentOrientation = orientation//默认垂直
    private var _isDrawLine = true//开关
    private var _rootCenter = 0
    private var _rootLeft = 0
    private var _rootTop = 0
    private var _rootRight = 0
    private var _rootBottom = 0
    private var _firstX = 0//第一个点的位置
    private var _firstY = 0
    private var _lastX = 0//最后一个图的位置
    private var _lastY = 0

    private var sideRelative = 0//参照点

    init {
        setWillNotDraw(false)
        initAttrs(attrs, defStyleAttr)
        initPaint()
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        _attrs = AttrsParser.parseAttrs(context, attrs)
        _pointIcon = (UtilKRes.getDrawable(_attrs.pointResId) as BitmapDrawable).bitmap
    }

    fun getLineWidth(): Int {
        return _attrs.lineWidth
    }

    fun setLineWidth(lineWidth: Int) {
        _attrs.lineWidth = lineWidth
        invalidate()
    }

    fun isDrawLine(): Boolean {
        return _isDrawLine
    }

    fun setIsDrawLine(isDrawLine: Boolean) {
        _isDrawLine = isDrawLine
        invalidate()
    }

    fun getLinePaint(): Paint {
        return _linePaint
    }

    fun setLinePaint(linePaint: Paint) {
        _linePaint = linePaint
        invalidate()
    }

    fun getPointSize(): Int {
        return _attrs.pointSize
    }

    fun setPointSize(pointSize: Int) {
        _attrs.pointSize = pointSize
        invalidate()
    }

    fun getPointColor(): Int {
        return _attrs.pointColor
    }

    fun setPointColor(pointColor: Int) {
        _attrs.pointColor = pointColor
        invalidate()
    }

    fun getPointPaint(): Paint {
        return _pointPaint
    }

    fun setPointPaint(pointPaint: Paint) {
        _pointPaint = pointPaint
        invalidate()
    }

    fun getLineColor(): Int {
        return _attrs.lineColor
    }

    fun setLineColor(lineColor: Int) {
        _attrs.lineColor = lineColor
        invalidate()
    }

    fun getLineMarginOffset(): Int {
        return _attrs.lineMarginOffset
    }

    fun setLineMarginOffset(lineMarginOffset: Int) {
        _attrs.lineMarginOffset = lineMarginOffset
        invalidate()
    }

    fun getLineDynamicOffset(): Int {
        return _attrs.lineDynamicOffset
    }

    fun setLineDynamicOffset(lineDynamicOffset: Int) {
        _attrs.lineDynamicOffset = lineDynamicOffset
        invalidate()
    }

    fun getPointIcon(): Bitmap {
        return _pointIcon
    }

    fun setIcon(icon: Bitmap) {
        _pointIcon = icon
    }

    fun setPointIconResId(resId: Int) {
        _pointIcon = (UtilKRes.getDrawable(resId) as BitmapDrawable).bitmap
        invalidate()
    }

    fun getLineGravity(): Int {
        return _attrs.lineGravity
    }

    fun setLineGravity(lineGravity: Int) {
        _attrs.lineGravity = lineGravity
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        calculateSideRelative()
        if (_isDrawLine) {
            drawTimeLine(canvas)
        }
    }

    private fun initPaint() {
        _linePaint = Paint()
        _linePaint.apply {
            isAntiAlias = true
            isDither = true
            color = _attrs.lineColor
            strokeWidth = _attrs.lineWidth.toFloat()
            style = Paint.Style.FILL_AND_STROKE
        }

        _pointPaint = Paint()
        _pointPaint.apply {
            _pointPaint.isAntiAlias = true
            _pointPaint.isDither = true
            _pointPaint.color = _attrs.pointColor
            _pointPaint.style = Paint.Style.FILL
        }
    }

    private fun calculateSideRelative() {
        _rootLeft = left
        _rootTop = top
        _rootRight = right
        _rootBottom = bottom
        if (_currentOrientation == VERTICAL) _rootCenter = _rootLeft + _rootRight shr 1
        if (_currentOrientation == HORIZONTAL) _rootCenter = _rootTop + _rootBottom shr 1
        val isCorrect = _attrs.lineGravity == AttrsParser.GRAVITY_CENTER || (_attrs.lineGravity + _currentOrientation) % 2 != 0
        if (isCorrect) {
            when (_attrs.lineGravity) {
                AttrsParser.GRAVITY_CENTER -> sideRelative = _rootCenter
                AttrsParser.GRAVITY_LEFT -> sideRelative = _rootLeft
                AttrsParser.GRAVITY_TOP -> sideRelative = _rootTop
                AttrsParser.GRAVITY_RIGHT -> sideRelative = _rootRight
                AttrsParser.GRAVITY_BOTTOM -> sideRelative = _rootBottom
            }
        } else {
            sideRelative = 0
        }
    }

    private fun drawTimeLine(canvas: Canvas) {
        val childCount = childCount
        if (childCount > 0) {
            if (childCount > 1) {//大于1，证明至少有2个，也就是第一个和第二个之间连成线，第一个和最后一个分别有点/icon
                when (_currentOrientation) {
                    VERTICAL -> {
                        drawFirstChildViewVertical(canvas)
                        drawLastChildViewVertical(canvas)
                        drawBetweenLineVertical(canvas)
                    }
                    HORIZONTAL -> {
                        drawFirstChildViewHorizontal(canvas)
                        drawLastChildViewHorizontal(canvas)
                        drawBetweenLineHorizontal(canvas)
                    }
                    else -> {}
                }
            } else if (childCount == 1) {
                when (_currentOrientation) {
                    VERTICAL -> drawFirstChildViewVertical(canvas)
                    HORIZONTAL -> drawFirstChildViewHorizontal(canvas)
                    else -> {}
                }
            }
        }
    }

    private fun drawFirstChildViewVertical(canvas: Canvas) {
        if (getChildAt(0) != null) {
            val top = getChildAt(0).top
            _firstX = if (sideRelative >= _rootCenter) sideRelative - _attrs.lineMarginOffset else sideRelative + _attrs.lineMarginOffset//记录值
            _firstY = top + getChildAt(0).paddingTop + _attrs.lineDynamicOffset
            canvas.drawCircle(_firstX.toFloat(), _firstY.toFloat(), _attrs.pointSize.toFloat(), _pointPaint) //画一个圆
        }
    }

    private fun drawLastChildViewVertical(canvas: Canvas) {
        if (getChildAt(childCount - 1) != null) {
            val top = getChildAt(childCount - 1).top
            _lastX = (if (sideRelative >= _rootCenter) sideRelative - _attrs.lineMarginOffset else sideRelative + _attrs.lineMarginOffset) - (_pointIcon.width shr 1)//记录值
            _lastY = top + getChildAt(childCount - 1).paddingTop + _attrs.lineDynamicOffset
            canvas.drawBitmap(_pointIcon, _lastX.toFloat(), _lastY.toFloat(), null)//画一个图
        }
    }

    private fun drawBetweenLineVertical(canvas: Canvas) {
        canvas.drawLine(_firstX.toFloat(), _firstY.toFloat(), _firstX.toFloat(), _lastY.toFloat(), _linePaint)//画剩下的
        for (i in 0 until childCount - 1) {
            if (getChildAt(i) != null && i != 0) {//画了线，就画圆
                val top = getChildAt(i).top
                val y = top + getChildAt(i).paddingTop + _attrs.lineDynamicOffset//记录值
                canvas.drawCircle(_firstX.toFloat(), y.toFloat(), _attrs.pointSize.toFloat(), _pointPaint)
            }
        }
    }

    private fun drawFirstChildViewHorizontal(canvas: Canvas) {
        if (getChildAt(0) != null) {
            val left = getChildAt(0).left
            _firstX = left + getChildAt(0).paddingLeft + _attrs.lineDynamicOffset//记录值
            _firstY = if (sideRelative >= _rootCenter) sideRelative - _attrs.lineMarginOffset else sideRelative + _attrs.lineMarginOffset
            canvas.drawCircle(_firstX.toFloat(), _firstY.toFloat(), _attrs.pointSize.toFloat(), _pointPaint)//画一个圆
        }
    }

    private fun drawLastChildViewHorizontal(canvas: Canvas) {
        if (getChildAt(childCount - 1) != null) {
            val left = getChildAt(childCount - 1).left
            _lastX = left + getChildAt(childCount - 1).paddingLeft + _attrs.lineDynamicOffset//记录值
            _lastY = (if (sideRelative >= _rootCenter) sideRelative - _attrs.lineMarginOffset else sideRelative + _attrs.lineMarginOffset) - (_pointIcon.width shr 1)
            canvas.drawBitmap(_pointIcon, _lastX.toFloat(), _lastY.toFloat(), null) //画一个图
        }
    }

    private fun drawBetweenLineHorizontal(canvas: Canvas) {
        canvas.drawLine(_firstX.toFloat(), _firstY.toFloat(), _lastX.toFloat(), _firstY.toFloat(), _linePaint)//画剩下的线
        for (i in 0 until childCount - 1) {
            if (getChildAt(i) != null && i != 0) { //画了线，就画圆
                val left = getChildAt(i).left
                val x = left + getChildAt(i).paddingLeft + _attrs.lineDynamicOffset//记录值
                canvas.drawCircle(x.toFloat(), _firstY.toFloat(), _attrs.pointSize.toFloat(), _pointPaint)
            }
        }
    }

    override fun setOrientation(orientation: Int) {
        super.setOrientation(orientation)
        _currentOrientation = orientation
        invalidate()
    }

    private data class Attrs(
        var lineMarginOffset: Int,//line location
        var lineDynamicOffset: Int,
        var lineWidth: Int,//line property
        var lineColor: Int,
        var lineGravity: Int,//line gravity(默认垂直的左边)
        var pointSize: Int,//point property
        var pointColor: Int,
        var pointResId: Int
    )

    private object AttrsParser {
        const val GRAVITY_CENTER = 0
        const val GRAVITY_LEFT = 2
        const val GRAVITY_TOP = 1
        const val GRAVITY_RIGHT = 4
        const val GRAVITY_BOTTOM = 3

        const val LINE_MARGIN_SIDE: Int = 10//line location
        const val LINE_DYNAMIC_DIMEN: Int = 0
        val LINE_WIDTH: Int = 2f.dp2px()//line property
        val LINE_COLOR: Int = UtilKRes.getColor(R.color.blue_normal)
        const val LINE_GRAVITY: Int = GRAVITY_LEFT//line gravity(默认垂直的左边)
        val POINT_SIZE: Int = 0f.dp2px()//point property
        val POINT_COLOR: Int = LINE_COLOR
        val POINT_RES_ID: Int = R.mipmap.viewk_qr_scan_success

        fun parseAttrs(context: Context, attrs: AttributeSet?): Attrs {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutKTimeLine)
            val lineMarginOffset =
                typedArray.getDimensionPixelOffset(R.styleable.LayoutKTimeLine_layoutKTimeLine_lineMarginOffset, LINE_MARGIN_SIDE)
            val lineDynamicOffset =
                typedArray.getDimensionPixelOffset(R.styleable.LayoutKTimeLine_layoutKTimeLine_lineDynamicOffset, LINE_DYNAMIC_DIMEN)
            val lineWidth =
                typedArray.getDimensionPixelOffset(R.styleable.LayoutKTimeLine_layoutKTimeLine_lineWidth, LINE_WIDTH)
            val lineColor =
                typedArray.getColor(R.styleable.LayoutKTimeLine_layoutKTimeLine_lineColor, LINE_COLOR)
            val lineGravity =
                typedArray.getInt(R.styleable.LayoutKTimeLine_layoutKTimeLine_lineGravity, LINE_GRAVITY)
            val pointSize =
                typedArray.getDimensionPixelSize(R.styleable.LayoutKTimeLine_layoutKTimeLine_pointSize, POINT_SIZE)
            val pointColor =
                typedArray.getColor(R.styleable.LayoutKTimeLine_layoutKTimeLine_pointColor, POINT_COLOR)
            val pointResId =
                typedArray.getResourceId(R.styleable.LayoutKTimeLine_layoutKTimeLine_pointIconSrc, POINT_RES_ID)
            typedArray.recycle()
            return Attrs(lineMarginOffset, lineDynamicOffset, lineWidth, lineColor, lineGravity, pointSize, pointColor, pointResId)
        }
    }
}