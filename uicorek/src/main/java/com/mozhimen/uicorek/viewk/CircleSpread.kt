package com.mozhimen.uicorek.viewk

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.basek.BaseKView
import com.mozhimen.basick.basek.commons.IBaseKViewAction
import com.mozhimen.uicorek.R

/**
 * @ClassName CircleSpread
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/1 15:40
 * @Version 1.0
 */
class CircleSpread @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) :
    BaseKView(context, attrs, defStyleAttr, defStyleRes), IBaseKViewAction {

    //region #variate
    private var _centerColor = 0xFFFFFF //中心圆颜色
    private var _centerDrawable: Drawable? = null//中心圆图片
    private var _centerRadius = 20f.dp2px()//中心圆半径
    private var _spreadColor = 0xFFFFFF //扩散圆颜色
    private var _spreadCount = 4 //扩散圆最大数
    private var _spreadIsRipple = true//是否是波纹(否为波浪)
    private var _circlesDistance = 5f.dp2px() //每个扩散圆的距离
    private var _animTime = 2000 //从中心圆扩散到边界圆的间隔

    private lateinit var _centerPaint: Paint //中心圆paint
    private lateinit var _spreadPaint: Paint //扩散圆paint
    private lateinit var _circleRectF: RectF

    private var _centerBitmap: Bitmap? = null
    private var _realSpreadCount = _spreadCount
    private var _radiusAddStep = 2f.dp2px()
    private var _alphaMinusStep = 255f * _radiusAddStep / (realRadius - _centerRadius)
    private var _interval: Long =
        _animTime.toLong() * _radiusAddStep.toLong() / (realRadius.toLong() - _centerRadius.toLong())

    private val _spreadCircles = ArrayDeque<Pair<Float, Float>>()//半径,alpha

    private var _isStop = false
    //endregion

    override fun requireStop() {
        _isStop = true
    }

    override fun requireStart() {
        _isStop = false
        _spreadCircles.add(_centerRadius.toFloat() to 255f)
        invalidate()
    }

    //region #private function
    init {
        initAttrs(attrs, defStyleAttr)
        initPaint()
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleSpread)
        _centerColor =
            typedArray.getColor(R.styleable.CircleSpread_circleSpread_centerColor, _centerColor)
        _centerDrawable =
            typedArray.getDrawable(R.styleable.CircleSpread_circleSpread_centerDrawable)
        _centerRadius = typedArray.getDimensionPixelSize(
            R.styleable.CircleSpread_circleSpread_centerRadius,
            _centerRadius
        )
        _spreadColor =
            typedArray.getColor(R.styleable.CircleSpread_circleSpread_spreadColor, _spreadColor)
        _spreadCount =
            typedArray.getInteger(R.styleable.CircleSpread_circleSpread_spreadCount, _spreadCount)
        _spreadIsRipple = typedArray.getBoolean(
            R.styleable.CircleSpread_circleSpread_spreadIsRipple,
            _spreadIsRipple
        )
        _circlesDistance =
            typedArray.getDimensionPixelOffset(
                R.styleable.CircleSpread_circleSpread_circlesDistance,
                _circlesDistance
            )
        _animTime = typedArray.getInteger(R.styleable.CircleSpread_circleSpread_animTime, _animTime)
        typedArray.recycle()

        _centerDrawable?.let {
            _centerBitmap = (it as BitmapDrawable).bitmap
        }
    }

    override fun initPaint() {
        super.initPaint()
        _centerPaint = Paint()
        _centerPaint.isAntiAlias = true
        _centerPaint.color = _centerColor

        _spreadPaint = Paint()
        _spreadPaint.color = _spreadColor
        _spreadPaint.isAntiAlias = true
        if (_spreadIsRipple) {
            _spreadPaint.style = Paint.Style.FILL
        } else {
            _spreadPaint.style = Paint.Style.STROKE
        }
        _spreadPaint.alpha = 255
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initData()
    }

    override fun initData() {
        //计算真实的阔散圆数量
        _realSpreadCount =
            if (((realRadius - _centerRadius) / _circlesDistance) < _spreadCount) {
                ((realRadius - _centerRadius) / _circlesDistance).toInt()
            } else _spreadCount
        _spreadCircles.add(_centerRadius.toFloat() to 255f)
        //计算时间及步长
        _interval =
            _animTime.toLong() * _radiusAddStep.toLong() / (realRadius.toLong() - _centerRadius.toLong())
        _alphaMinusStep = 255f * _radiusAddStep / (realRadius - _centerRadius)
        //rect
        _circleRectF =
            RectF(
                centerX - _centerRadius,
                centerY - _centerRadius,
                centerX + _centerRadius,
                centerY + _centerRadius
            )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawCircleSpread(canvas)
            drawCircleCenter(canvas)
            changeRadiusAndAlpha()
        }
    }

    private fun changeRadiusAndAlpha() {
        _spreadCircles.forEachIndexed { i, c ->
            _spreadCircles[i] =
                c.first + _radiusAddStep to c.second - _alphaMinusStep
        }

        if (_spreadCircles[_spreadCircles.size - 1].first - _centerRadius >= _circlesDistance && _spreadCircles.size < _realSpreadCount && !_isStop) {
            _spreadCircles.addLast(_centerRadius.toFloat() to 255f)
        }

        if (_spreadCircles[0].first >= realRadius || _spreadCircles[0].second <= 0f) {
            _spreadCircles.removeFirst()
        }

        if (_spreadCircles.size > 0) {
            postInvalidateDelayed(_interval)
        }
    }

    private fun drawCircleCenter(canvas: Canvas) {
        _centerBitmap?.let {
            canvas.drawBitmap(it, null, _circleRectF, _centerPaint)
        } ?: canvas.drawCircle(centerX, centerY, _centerRadius.toFloat(), _centerPaint)
    }

    private fun drawCircleSpread(canvas: Canvas) {
        _spreadCircles.forEach {
            _spreadPaint.alpha = it.second.toInt()
            canvas.drawCircle(centerX, centerY, it.first, _spreadPaint)
        }
    }
    //endregion
}