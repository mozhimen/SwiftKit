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
class TextKProgress @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr), IViewK {

    companion object {
        const val SCALE = 1.0f

        const val STYLE_BALL_PULSE = 1
        const val STYLE_BALL_JUMP = 2

        const val PROGRESS_STATE_IDLE = 0//开始下载
        const val PROGRESS_STATE_LOADING = 1//下载之中
        const val PROGRESS_STATE_PAUSE = 2//暂停下载
        const val PROGRESS_STATE_FINISH = 3//下载完成
    }

    //////////////////////////////////////////////////////////////////

    private var mBackgroundColor = Color.parseColor("#BFE8D9")//背景颜色
    private var mBackgroundColorStart = Color.parseColor("#00A667")
    private var mBackgroundColorEnd = Color.WHITE//下载中后半部分后面背景颜色
    private var mTextColorDefault = Color.parseColor("#00A667")//文字颜色
    private var mTextColorUnCover = Color.BLACK
    private var mTextColorCover = Color.parseColor("#00A667")//覆盖后颜色
    private var mBorderRadius = 0f
    private var mBorderWidth = 2f.dp2px()//边框宽度
    private var mBorderColor = Color.parseColor("#BFE8D9")//边框颜色
    private var mBallStyle = STYLE_BALL_JUMP//点动画样式
    private var mBallRadius = 6f//点的半径
    private var mBallSpacing = 4f//点的间隙
    private var mEnableAnimation = false
    private var mMinProgress = 0
    private var mMaxProgress = 100

    //////////////////////////////////////////////////////////////////

    private var mProgress = 0f
    private var mProgressState = 0
    private var mCurrentText: CharSequence? = null//记录当前文字

    //////////////////////////////////////////////////////////////////

    private var mProgressPercent = 0f
    private var mProgressDestination = 0f
    private var mTextBottomBorder = 0f
    private var mTextRightBorder = 0f
    private val scaleFloats = floatArrayOf(SCALE, SCALE, SCALE)
    private val translateYFloats = FloatArray(3)

    //////////////////////////////////////////////////////////////////

    @Volatile
    private lateinit var mTextPaint: Paint//按钮文字画笔
    private lateinit var mBackgroundPaint: Paint//背景画笔
    private lateinit var mProgressAnimation: ValueAnimator//下载平滑动画
    private var mAnimators: ArrayList<ValueAnimator?>? = null//点运动动画
    private var mProgressTextGradient: LinearGradient? = null
//    private val mBackgroundBoundsBorder = RectF()
    private val mBackgroundBounds = RectF()

    //////////////////////////////////////////////////////////////////

    init {
        initAttrs(attrs)
        initView()
        setupAnimations()
    }

    //////////////////////////////////////////////////////////////////

    override fun initView() {
        //设置背景画笔
        mBackgroundPaint = Paint()
        mBackgroundPaint.setAntiAlias(true)
        mBackgroundPaint.setStyle(Paint.Style.FILL)
        //设置文字画笔
        mTextPaint = Paint()
        mTextPaint.setAntiAlias(true)
        mTextPaint.setTextSize(textSize)
        //解决文字有时候画不出问题
        setLayerType(LAYER_TYPE_SOFTWARE, mTextPaint)
        //初始化状态设为NORMAL
        mProgressState = PROGRESS_STATE_IDLE
    }

    override fun initAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextKProgress)
        try {
            mBackgroundColor =
                typedArray.getColor(R.styleable.TextKProgress_textKProgress_backgroundColor, mBackgroundColor)
            mBackgroundColorStart =
                typedArray.getColor(R.styleable.TextKProgress_textKProgress_backgroundColorStart, mBackgroundColorStart)
            mBackgroundColorEnd =
                typedArray.getColor(R.styleable.TextKProgress_textKProgress_backgroundColorEnd, mBackgroundColorEnd)
            mTextColorDefault =
                typedArray.getColor(R.styleable.TextKProgress_textKProgress_textColorDefault, mTextColorDefault)
            mTextColorUnCover =
                typedArray.getColor(R.styleable.TextKProgress_textKProgress_textColorUnCover, mTextColorUnCover)
            mTextColorCover =
                typedArray.getColor(R.styleable.TextKProgress_textKProgress_textColorCover, mTextColorCover)
            mBorderRadius =
                typedArray.getDimension(R.styleable.TextKProgress_textKProgress_borderRadius, mBorderRadius)
            mBorderWidth =
                typedArray.getDimension(R.styleable.TextKProgress_textKProgress_borderWidth, mBorderWidth)
            mBorderColor =
                typedArray.getColor(R.styleable.TextKProgress_textKProgress_borderColor, mBorderColor)
            mBallStyle =
                typedArray.getInt(R.styleable.TextKProgress_textKProgress_ballStyle, mBallStyle)
            mBallRadius =
                typedArray.getDimension(R.styleable.TextKProgress_textKProgress_ballRadius, mBallRadius)
            mBallSpacing =
                typedArray.getDimension(R.styleable.TextKProgress_textKProgress_ballSpacing, mBallSpacing)
            mEnableAnimation =
                typedArray.getBoolean(R.styleable.TextKProgress_textKProgress_enableAnimation, mEnableAnimation)
            mMinProgress =
                typedArray.getInt(R.styleable.TextKProgress_textKProgress_minProgress, mMinProgress)
            mMaxProgress =
                typedArray.getInt(R.styleable.TextKProgress_textKProgress_maxProgress, mMaxProgress)
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
        mProgressState = progressSavedState.state
        mProgress = progressSavedState.progress.toFloat()
        mCurrentText = progressSavedState.currentText
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        if (mCurrentText == null) mCurrentText = ""
        return ProgressSavedState(superState, mProgress.toInt(), mProgressState, mCurrentText.toString())
    }

    //////////////////////////////////////////////////////////////////

    fun getProgress(): Float {
        return mProgress
    }

    fun getProgressState(): Int {
        return mProgressState
    }

    //////////////////////////////////////////////////////////////////

    override fun setBackgroundColor(@ColorInt backgroundColor: Int) {
        mBackgroundColor = backgroundColor
        invalidate()
    }

    fun setBackgroundColorStart(@ColorInt backgroundColorStart: Int) {
        mBackgroundColorStart = backgroundColorStart
        invalidate()
    }

    fun setBackgroundColorEnd(@ColorInt backgroundColorEnd: Int) {
        mBackgroundColorEnd = backgroundColorEnd
        invalidate()
    }

    override fun setTextColor(@ColorInt textColor: Int) {
        mTextColorDefault = textColor
        super.setTextColor(textColor)
    }

    fun setTextColorUnCover(@ColorInt textColorNormal: Int) {
        mTextColorUnCover = textColorNormal
    }

    fun setTextColorCover(@ColorInt textColorCover: Int) {
        mTextColorCover = textColorCover
    }

    //////////////////////////////////////////////////////////////////

    fun setBorderRadius(buttonRadius: Float) {
        mBorderRadius = buttonRadius
        invalidate()
    }

    fun setBorderWidth(width: Int) {
        mBorderWidth = width.dp2px()
        invalidate()
    }

    fun setBorderColor(@ColorInt borderColor: Int) {
        mBorderColor = borderColor
        invalidate()
    }

    //////////////////////////////////////////////////////////////////

    fun setBallStyle(ballStyle: Int) {
        mBallStyle = ballStyle
    }

    fun setBallRadius(ballRadius: Float) {
        mBallRadius = ballRadius
    }

    fun setBallSpacing(ballSpacing: Float) {
        mBallSpacing = ballSpacing
    }

    fun setEnableAnimation(enableAnimation: Boolean) {
        mEnableAnimation = enableAnimation
    }

    fun setMinProgress(minProgress: Int) {
        mMinProgress = minProgress
    }

    fun setMaxProgress(maxProgress: Int) {
        mMaxProgress = maxProgress
    }

    //////////////////////////////////////////////////////////////////

    fun setProgress(progress: Float) {
        mProgress = progress
        invalidate()
    }

    fun setProgressState(state: Int) {
        //状态确实有改变
        if (mProgressState != state) {
            mProgressState = state
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
        mCurrentText = charSequence
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
            mCurrentText = text
        }
        if (progress >= mMinProgress && progress <= mMaxProgress) {
            if (mEnableAnimation) {
                mProgressDestination = progress
                if (mProgressAnimation.isRunning) {
                    mProgressAnimation.resume()
                }
                mProgressAnimation.start()
            } else {
                mProgress = progress
                invalidate()
            }
        } else if (progress < mMinProgress) {
            mProgress = 0f
        } else if (progress > mMaxProgress) {
            mProgress = 100f
            invalidate()
        }
    }

    //////////////////////////////////////////////////////////////////

    fun drawLoadingBall(canvas: Canvas) {
        for (i in 0..2) {
            canvas.save()
            val translateX: Float = mTextRightBorder + 10 + mBallRadius * 2 * i + mBallSpacing * i
            canvas.translate(translateX, mTextBottomBorder)
            canvas.drawCircle(0f, translateYFloats[i], mBallRadius * scaleFloats[i], mTextPaint)
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
                scaleFloats[i] = animation.animatedValue as Float
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
            val scaleAnim = ValueAnimator.ofFloat(mTextBottomBorder, mTextBottomBorder - mBallRadius * 2, mTextBottomBorder)
            scaleAnim.setDuration(600)
            scaleAnim.repeatCount = -1
            scaleAnim.startDelay = delays[i].toLong()
            scaleAnim.addUpdateListener { animation ->
                translateYFloats[i] = animation.animatedValue as Float
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
        if (null != mAnimators) {
            for (i in mAnimators!!.indices) {
                val animator = mAnimators!![i]
                animator?.start()
            }
        }
    }

    private fun stopAnimators() {
        if (mAnimators != null) {
            for (animator in mAnimators!!) {
                if (animator != null && animator.isStarted) {
                    animator.end()
                }
            }
        }
    }

    private fun drawText(canvas: Canvas) {
        //计算Baseline绘制的Y坐标
        val y = canvas.height / 2 - (mTextPaint.descent() / 2 + mTextPaint.ascent() / 2)
        if (mCurrentText == null) {
            mCurrentText = if (isInEditMode) text else ""
        }
        val textWidth = mTextPaint.measureText(mCurrentText.toString())
        mTextBottomBorder = y
        mTextRightBorder = (measuredWidth + textWidth) / 2
        when (mProgressState) {
            PROGRESS_STATE_PAUSE, PROGRESS_STATE_LOADING -> {
                //进度条压过距离
                val coverLength = measuredWidth * mProgressPercent
                //开始渐变指示器
                val indicator1 = measuredWidth / 2.0f - textWidth / 2
                //结束渐变指示器
                val indicator2 = measuredWidth / 2.0f + textWidth / 2
                //文字变色部分的距离
                val coverTextLength = textWidth / 2 - measuredWidth / 2.0f + coverLength
                val textProgress = coverTextLength / textWidth
                if (coverLength <= indicator1) {
                    mTextPaint.setShader(null)
                    mTextPaint.color = mTextColorUnCover
                } else if (indicator1 < coverLength && coverLength <= indicator2) {
                    //设置变色效果
                    mProgressTextGradient = LinearGradient(
                        (measuredWidth - textWidth) / 2, 0f, (measuredWidth + textWidth) / 2, 0f, intArrayOf(mTextColorCover, mTextColorUnCover), floatArrayOf(textProgress, textProgress + 0.001f),
                        Shader.TileMode.CLAMP
                    )
                    mTextPaint.color = mTextColorCover
                    mTextPaint.setShader(mProgressTextGradient)
                } else {
                    mTextPaint.setShader(null)
                    mTextPaint.color = mTextColorCover
                }
                canvas.drawText(mCurrentText.toString(), (measuredWidth - textWidth) / 2, y, mTextPaint)
            }

            PROGRESS_STATE_IDLE, PROGRESS_STATE_FINISH -> {
                mTextPaint.setShader(null)
                mTextPaint.color = mTextColorDefault
                canvas.drawText(mCurrentText.toString(), (measuredWidth - textWidth) / 2, y, mTextPaint)
            }

            else -> {
                mTextPaint.setShader(null)
                mTextPaint.color = mTextColorDefault
                canvas.drawText(mCurrentText.toString(), (measuredWidth - textWidth) / 2, y, mTextPaint)
            }
        }
    }

    private fun drawBackground(canvas: Canvas) {
        when (mProgressState) {
            PROGRESS_STATE_PAUSE, PROGRESS_STATE_LOADING -> {
                mBackgroundBounds.left = mBorderWidth
                mBackgroundBounds.top = mBorderWidth
                mBackgroundBounds.right = measuredWidth - mBorderWidth
                mBackgroundBounds.bottom = measuredHeight - mBorderWidth

                if (mBorderWidth >= 1f) {
                    //下载中 需要绘制边框
                    mBackgroundPaint.style = Paint.Style.STROKE
                    mBackgroundPaint.color = mBorderColor
                    mBackgroundPaint.strokeWidth = mBorderWidth
                    canvas.drawRoundRect(mBackgroundBounds, mBorderRadius, mBorderRadius, mBackgroundPaint)
                }

                //画背景板
                mBackgroundPaint.style = Paint.Style.FILL_AND_STROKE
                mBackgroundPaint.color = mBackgroundColorEnd
                canvas.drawRoundRect(mBackgroundBounds, mBorderRadius, mBorderRadius, mBackgroundPaint)

                canvas.save()
                //画进度条
                //设置图层显示模式为 SRC_ATOP
                val porterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
                mBackgroundPaint.color = mBackgroundColorStart
                mBackgroundPaint.setXfermode(porterDuffXfermode)
                //计算 src 矩形的右边界
                mProgressPercent = mProgress / (mMaxProgress + 0f)//计算当前的进度
                val right: Float = mBackgroundBounds.right * mProgressPercent
                //在dst画出src矩形
//                canvas.drawRect(mBackgroundBoundsBorder.left, mBackgroundBoundsBorder.top, right, mBackgroundBoundsBorder.bottom, mBackgroundPaint)
                mBackgroundBounds.right = right
                canvas.drawRoundRect(mBackgroundBounds, mBorderRadius, mBorderRadius, mBackgroundPaint)
                canvas.restore()

                mBackgroundPaint.setXfermode(null)
            }

            PROGRESS_STATE_IDLE, PROGRESS_STATE_FINISH -> {
                mBackgroundBounds.left = mBorderWidth
                mBackgroundBounds.top = mBorderWidth
                mBackgroundBounds.right = measuredWidth - mBorderWidth
                mBackgroundBounds.bottom = measuredHeight - mBorderWidth
                mBackgroundPaint.style = Paint.Style.FILL
                mBackgroundPaint.color = mBackgroundColor
                canvas.drawRoundRect(mBackgroundBounds, mBorderRadius, mBorderRadius, mBackgroundPaint)
            }

            else -> {
                mBackgroundBounds.left = mBorderWidth
                mBackgroundBounds.top = mBorderWidth
                mBackgroundBounds.right = measuredWidth - mBorderWidth
                mBackgroundBounds.bottom = measuredHeight - mBorderWidth
                mBackgroundPaint.style = Paint.Style.FILL
                mBackgroundPaint.color = mBackgroundColor
                canvas.drawRoundRect(mBackgroundBounds, mBorderRadius, mBorderRadius, mBackgroundPaint)
            }
        }
    }

    private fun setupAnimations() {
        if (mEnableAnimation) {
            //ProgressBar的动画
            mProgressAnimation = ValueAnimator.ofFloat(0f, 1f).setDuration(500)
            mProgressAnimation.addUpdateListener(AnimatorUpdateListener { animation ->
                val timePercent = animation.animatedValue as Float
                mProgress += (mProgressDestination - mProgress) * timePercent
                invalidate()
            })
            setupBallStyle(mBallStyle)
        }
    }

    //设置点动画样式
    private fun setupBallStyle(ballStyle: Int) {
        mBallStyle = ballStyle
        mAnimators = if (ballStyle == STYLE_BALL_PULSE) {
            createBallPulseAnimators()
        } else {
            createBallJumpAnimators()
        }
    }
}