package com.mozhimen.uicorek.textk.progress

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.Shader
import android.os.Parcelable
import android.text.TextUtils
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import com.mozhimen.basick.utilk.android.util.dp2px
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.textk.progress.helpers.ProgressSavedState
import com.mozhimen.uicorek.viewk.commons.IViewK

/**
 * @ClassName TextKProgress
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/22 18:35
 * @Version 1.0s
 */
class TextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr), IViewK {

    companion object {
        const val SCALE = 1.0f

        const val STYLE_BALL_PULSE = 1
        const val STYLE_BALL_JUMP = 2

        const val PROGRESS_STATE_IDLE = 0//开始下载
        const val PROGRESS_STATE_LOADING = 1//下载之中
//        const val PROGRESS_STATE_PAUSE = 2//暂停下载
        const val PROGRESS_STATE_FINISH = 3//下载完成

        const val PROGRESS_STYLE_ROUND = 0//圆形进度条
        const val PROGRESS_STYLE_RECT = 1//方形进度条
    }

    //////////////////////////////////////////////////////////////////

    private var _backgroundColor = Color.parseColor("#BFE8D9")//背景颜色
    private var _backgroundColorStart = Color.parseColor("#00A667")
    private var _backgroundColorEnd = Color.WHITE//下载中后半部分后面背景颜色
    private var _textColorDefault = Color.parseColor("#00A667")//文字颜色
    private var _textColorUnCover = Color.BLACK
    private var _textColorCover = Color.parseColor("#00A667")//覆盖后颜色
    private var _borderRadius = 0f
    private var _borderWidth = 2f.dp2px()//边框宽度
    private var _borderColor = Color.parseColor("#BFE8D9")//边框颜色
    private var _ballStyle = STYLE_BALL_JUMP//点动画样式
    private var _ballRadius = 6f//点的半径
    private var _ballSpacing = 4f//点的间隙
    private var _enableAnimation = false
    private var _minProgress = 0
    private var _maxProgress = 100
    private var _progressStyle = PROGRESS_STYLE_ROUND

    //////////////////////////////////////////////////////////////////

    private var _progress = 0f
    private var _progressState = 0
    private var _currentText: CharSequence? = null//记录当前文字

    //////////////////////////////////////////////////////////////////

    private var _progressPercent = 0f
    private var _progressDestination = 0f
    private var _textBottomBorder = 0f
    private var _textRightBorder = 0f
    private val _scaleFloats = floatArrayOf(SCALE, SCALE, SCALE)
    private val _translateYFloats = FloatArray(3)

    //////////////////////////////////////////////////////////////////

    @Volatile
    private lateinit var _textPaint: Paint//按钮文字画笔
    private lateinit var _backgroundPaint: Paint//背景画笔
    private lateinit var _progressAnimation: ValueAnimator//下载平滑动画
    private var _animators: ArrayList<ValueAnimator?>? = null//点运动动画
    private var _progressTextGradient: LinearGradient? = null

    //    private val mBackgroundBoundsBorder = RectF()
    private val _backgroundBounds = RectF()

    //////////////////////////////////////////////////////////////////

    init {
        initAttrs(attrs)
        initView()
        setupAnimations()
    }

    //////////////////////////////////////////////////////////////////

    override fun initView() {
        //设置背景画笔
        _backgroundPaint = Paint()
        _backgroundPaint.setAntiAlias(true)
        _backgroundPaint.setStyle(Paint.Style.FILL)
        //设置文字画笔
        _textPaint = Paint()
        _textPaint.setAntiAlias(true)
        _textPaint.setTextSize(textSize)
        _textPaint.typeface = typeface
        //解决文字有时候画不出问题
        setLayerType(LAYER_TYPE_SOFTWARE, _textPaint)
        //初始化状态设为NORMAL
        _progressState = PROGRESS_STATE_IDLE
    }

    override fun initAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextKProgress)
        try {
            _backgroundColor =
                typedArray.getColor(R.styleable.TextKProgress_textKProgress_backgroundColor, _backgroundColor)
            _backgroundColorStart =
                typedArray.getColor(R.styleable.TextKProgress_textKProgress_backgroundColorStart, _backgroundColorStart)
            _backgroundColorEnd =
                typedArray.getColor(R.styleable.TextKProgress_textKProgress_backgroundColorEnd, _backgroundColorEnd)
            _textColorDefault =
                typedArray.getColor(R.styleable.TextKProgress_textKProgress_textColorDefault, _textColorDefault)
            _textColorUnCover =
                typedArray.getColor(R.styleable.TextKProgress_textKProgress_textColorUnCover, _textColorUnCover)
            _textColorCover =
                typedArray.getColor(R.styleable.TextKProgress_textKProgress_textColorCover, _textColorCover)
            _borderRadius =
                typedArray.getDimension(R.styleable.TextKProgress_textKProgress_borderRadius, _borderRadius)
            _borderWidth =
                typedArray.getDimension(R.styleable.TextKProgress_textKProgress_borderWidth, _borderWidth)
            _borderColor =
                typedArray.getColor(R.styleable.TextKProgress_textKProgress_borderColor, _borderColor)
            _ballStyle =
                typedArray.getInt(R.styleable.TextKProgress_textKProgress_ballStyle, _ballStyle)
            _ballRadius =
                typedArray.getDimension(R.styleable.TextKProgress_textKProgress_ballRadius, _ballRadius)
            _ballSpacing =
                typedArray.getDimension(R.styleable.TextKProgress_textKProgress_ballSpacing, _ballSpacing)
            _enableAnimation =
                typedArray.getBoolean(R.styleable.TextKProgress_textKProgress_enableAnimation, _enableAnimation)
            _minProgress =
                typedArray.getInt(R.styleable.TextKProgress_textKProgress_minProgress, _minProgress)
            _maxProgress =
                typedArray.getInt(R.styleable.TextKProgress_textKProgress_maxProgress, _maxProgress)
            _progressStyle =
                typedArray.getInt(R.styleable.TextKProgress_textKProgress_progressStyle, _progressStyle)
        } finally {
            typedArray.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        startDraw(canvas)
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val progressSavedState = state as ProgressSavedState
        super.onRestoreInstanceState(progressSavedState.superState)
        _progressState = progressSavedState.state
        _progress = progressSavedState.progress.toFloat()
        _currentText = progressSavedState.currentText
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        if (_currentText == null) _currentText = ""
        return ProgressSavedState(superState, _progress.toInt(), _progressState, _currentText.toString())
    }

    //////////////////////////////////////////////////////////////////

    fun getProgress(): Float {
        return _progress
    }

    fun getProgressState(): Int {
        return _progressState
    }

    //////////////////////////////////////////////////////////////////

    override fun setBackgroundColor(@ColorInt backgroundColor: Int) {
        _backgroundColor = backgroundColor
        invalidate()
    }

    fun setBackgroundColorStart(@ColorInt backgroundColorStart: Int) {
        _backgroundColorStart = backgroundColorStart
        invalidate()
    }

    fun setBackgroundColorEnd(@ColorInt backgroundColorEnd: Int) {
        _backgroundColorEnd = backgroundColorEnd
        invalidate()
    }

    override fun setTextColor(@ColorInt textColor: Int) {
        _textColorDefault = textColor
        super.setTextColor(textColor)
    }

    fun setTextColorUnCover(@ColorInt textColorNormal: Int) {
        _textColorUnCover = textColorNormal
    }

    fun setTextColorCover(@ColorInt textColorCover: Int) {
        _textColorCover = textColorCover
    }

    //////////////////////////////////////////////////////////////////

    fun setBorderRadius(buttonRadius: Float) {
        _borderRadius = buttonRadius
        invalidate()
    }

    fun setBorderWidth(width: Int) {
        _borderWidth = width.dp2px()
        invalidate()
    }

    fun setBorderColor(@ColorInt borderColor: Int) {
        _borderColor = borderColor
        invalidate()
    }

    //////////////////////////////////////////////////////////////////

    fun setBallStyle(ballStyle: Int) {
        _ballStyle = ballStyle
    }

    fun setBallRadius(ballRadius: Float) {
        _ballRadius = ballRadius
    }

    fun setBallSpacing(ballSpacing: Float) {
        _ballSpacing = ballSpacing
    }

    fun setEnableAnimation(enableAnimation: Boolean) {
        _enableAnimation = enableAnimation
    }

    fun setMinProgress(minProgress: Int) {
        _minProgress = minProgress
    }

    fun setMaxProgress(maxProgress: Int) {
        _maxProgress = maxProgress
    }

    //////////////////////////////////////////////////////////////////

    fun setProgress(progress: Float) {
        _progress = progress
        invalidate()
    }

    fun setProgressState(state: Int) {
        //状态确实有改变
        if (_progressState != state) {
            _progressState = state
            invalidate()
            if (state == PROGRESS_STATE_FINISH) {
                //开启点动画
                startAnimators()
            } else {
                stopAnimators()
            }
        }
    }

    /**
     * 设置当前按钮文字
     */
    fun setCurrentText(charSequence: CharSequence) {
        _currentText = charSequence
        invalidate()
    }

    /**
     * 设置带下载进度的文字
     */
    fun setProgressText(text: String, progress: Int) {
        setProgressText(text, progress.toFloat())
    }

    /**
     * 设置带下载进度的文字
     */
    fun setProgressText(text: String, progress: Float) {
        if (!TextUtils.isEmpty(text)) {
            _currentText = text
        }
        if (progress >= _minProgress && progress <= _maxProgress) {
            if (_enableAnimation) {
                _progressDestination = progress
                if (_progressAnimation.isRunning) {
                    _progressAnimation.resume()
                }
                _progressAnimation.start()
            } else {
                _progress = progress
                invalidate()
            }
        } else if (progress < _minProgress) {
            _progress = 0f
        } else if (progress > _maxProgress) {
            _progress = 100f
            invalidate()
        }
    }

    //////////////////////////////////////////////////////////////////

    fun drawLoadingBall(canvas: Canvas) {
        for (i in 0..2) {
            canvas.save()
            val translateX: Float = _textRightBorder + 10 + _ballRadius * 2 * i + _ballSpacing * i
            canvas.translate(translateX, _textBottomBorder)
            canvas.drawCircle(0f, _translateYFloats[i], _ballRadius * _scaleFloats[i], _textPaint)
            canvas.restore()
        }
    }

    fun createBallPulseAnimators(): java.util.ArrayList<ValueAnimator?> {
        val animators = java.util.ArrayList<ValueAnimator?>()
        val delays = intArrayOf(120, 240, 360)
        for (i in 0..2) {
            val scaleAnim = ValueAnimator.ofFloat(1f, 0.3f, 1f)
            scaleAnim.setDuration(750)
            scaleAnim.repeatCount = -1
            scaleAnim.startDelay = delays[i].toLong()
            scaleAnim.addUpdateListener { animation ->
                _scaleFloats[i] = animation.animatedValue as Float
                postInvalidate()
            }
            animators.add(scaleAnim)
        }
        return animators
    }

    fun createBallJumpAnimators(): java.util.ArrayList<ValueAnimator?>? {
        val animators = java.util.ArrayList<ValueAnimator?>()
        val delays = intArrayOf(70, 140, 210)
        for (i in 0..2) {
            val scaleAnim = ValueAnimator.ofFloat(_textBottomBorder, _textBottomBorder - _ballRadius * 2, _textBottomBorder)
            scaleAnim.setDuration(600)
            scaleAnim.repeatCount = -1
            scaleAnim.startDelay = delays[i].toLong()
            scaleAnim.addUpdateListener { animation ->
                _translateYFloats[i] = animation.animatedValue as Float
                postInvalidate()
            }
            animators.add(scaleAnim)
        }
        return animators
    }

    //////////////////////////////////////////////////////////////////

    private fun startDraw(canvas: Canvas) {
        drawBackground(canvas)
        drawText(canvas)
    }

    private fun startAnimators() {
        if (null != _animators) {
            for (i in _animators!!.indices) {
                val animator = _animators!![i]
                animator?.start()
            }
        }
    }

    private fun stopAnimators() {
        if (_animators != null) {
            for (animator in _animators!!) {
                if (animator != null && animator.isStarted) {
                    animator.end()
                }
            }
        }
    }

    private fun drawText(canvas: Canvas) {
        //计算Baseline绘制的Y坐标
        val y = canvas.height / 2 - (_textPaint.descent() / 2 + _textPaint.ascent() / 2)
        if (_currentText == null) {
            _currentText = if (isInEditMode) text else ""
        }
        val textWidth = _textPaint.measureText(_currentText.toString())
        _textBottomBorder = y
        _textRightBorder = (measuredWidth + textWidth) / 2
        when (_progressState) {
            /*PROGRESS_STATE_PAUSE,*/ PROGRESS_STATE_LOADING -> {
                //进度条压过距离
                val coverLength = measuredWidth * _progressPercent
                //开始渐变指示器
                val indicator1 = measuredWidth / 2.0f - textWidth / 2
                //结束渐变指示器
                val indicator2 = measuredWidth / 2.0f + textWidth / 2
                //文字变色部分的距离
                val coverTextLength = textWidth / 2 - measuredWidth / 2.0f + coverLength
                val textProgress = coverTextLength / textWidth
                if (coverLength <= indicator1) {
                    _textPaint.setShader(null)
                    _textPaint.color = _textColorUnCover
                } else if (indicator1 < coverLength && coverLength <= indicator2) {
                    //设置变色效果
                    _progressTextGradient = LinearGradient(
                        (measuredWidth - textWidth) / 2, 0f, (measuredWidth + textWidth) / 2, 0f, intArrayOf(_textColorCover, _textColorUnCover), floatArrayOf(textProgress, textProgress + 0.001f),
                        Shader.TileMode.CLAMP
                    )
                    _textPaint.color = _textColorCover
                    _textPaint.setShader(_progressTextGradient)
                } else {
                    _textPaint.setShader(null)
                    _textPaint.color = _textColorCover
                }
                canvas.drawText(_currentText.toString(), (measuredWidth - textWidth) / 2, y, _textPaint)
            }

            PROGRESS_STATE_IDLE, PROGRESS_STATE_FINISH -> {
                _textPaint.setShader(null)
                _textPaint.color = _textColorDefault
                canvas.drawText(_currentText.toString(), (measuredWidth - textWidth) / 2, y, _textPaint)
            }

            else -> {
                _textPaint.setShader(null)
                _textPaint.color = _textColorDefault
                canvas.drawText(_currentText.toString(), (measuredWidth - textWidth) / 2, y, _textPaint)
            }
        }
    }

    private fun drawBackground(canvas: Canvas) {
        when (_progressState) {
            /*PROGRESS_STATE_PAUSE,*/ PROGRESS_STATE_LOADING -> {
                _backgroundBounds.left = _borderWidth
                _backgroundBounds.top = _borderWidth
                _backgroundBounds.right = measuredWidth - _borderWidth
                _backgroundBounds.bottom = measuredHeight - _borderWidth

                if (_borderWidth >= 1f) {
                    //下载中 需要绘制边框
                    _backgroundPaint.style = Paint.Style.STROKE
                    _backgroundPaint.color = _borderColor
                    _backgroundPaint.strokeWidth = _borderWidth
                    canvas.drawRoundRect(_backgroundBounds, _borderRadius, _borderRadius, _backgroundPaint)
                }

                //画背景板
                _backgroundPaint.style = Paint.Style.FILL_AND_STROKE
                _backgroundPaint.color = _backgroundColorEnd
                canvas.drawRoundRect(_backgroundBounds, _borderRadius, _borderRadius, _backgroundPaint)

                canvas.save()
                //画进度条
                //设置图层显示模式为 SRC_ATOP
                val porterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
                _backgroundPaint.color = _backgroundColorStart
                _backgroundPaint.setXfermode(porterDuffXfermode)
                //计算 src 矩形的右边界
                _progressPercent = _progress / (_maxProgress + 0f)//计算当前的进度
                val right: Float = _backgroundBounds.right * _progressPercent
                //在dst画出src矩形
                _backgroundBounds.right = right
                if (_progressStyle == PROGRESS_STYLE_ROUND) {
                    canvas.drawRoundRect(_backgroundBounds, _borderRadius, _borderRadius, _backgroundPaint)
                } else
                    canvas.drawRect(_backgroundBounds.left, _backgroundBounds.top, right, _backgroundBounds.bottom, _backgroundPaint)
                canvas.restore()

                _backgroundPaint.setXfermode(null)
            }

            PROGRESS_STATE_IDLE, PROGRESS_STATE_FINISH -> {
                _backgroundBounds.left = _borderWidth
                _backgroundBounds.top = _borderWidth
                _backgroundBounds.right = measuredWidth - _borderWidth
                _backgroundBounds.bottom = measuredHeight - _borderWidth
                _backgroundPaint.style = Paint.Style.FILL
                _backgroundPaint.color = _backgroundColor
                canvas.drawRoundRect(_backgroundBounds, _borderRadius, _borderRadius, _backgroundPaint)
            }

            else -> {
                _backgroundBounds.left = _borderWidth
                _backgroundBounds.top = _borderWidth
                _backgroundBounds.right = measuredWidth - _borderWidth
                _backgroundBounds.bottom = measuredHeight - _borderWidth
                _backgroundPaint.style = Paint.Style.FILL
                _backgroundPaint.color = _backgroundColor
                canvas.drawRoundRect(_backgroundBounds, _borderRadius, _borderRadius, _backgroundPaint)
            }
        }
    }

    private fun setupAnimations() {
        if (_enableAnimation) {
            //ProgressBar的动画
            _progressAnimation = ValueAnimator.ofFloat(0f, 1f).setDuration(500)
            _progressAnimation.addUpdateListener(AnimatorUpdateListener { animation ->
                val timePercent = animation.animatedValue as Float
                _progress += (_progressDestination - _progress) * timePercent
                invalidate()
            })
            setupBallStyle(_ballStyle)
        }
    }

    //设置点动画样式
    private fun setupBallStyle(ballStyle: Int) {
        _ballStyle = ballStyle
        _animators = if (ballStyle == STYLE_BALL_PULSE) {
            createBallPulseAnimators()
        } else {
            createBallJumpAnimators()
        }
    }
}