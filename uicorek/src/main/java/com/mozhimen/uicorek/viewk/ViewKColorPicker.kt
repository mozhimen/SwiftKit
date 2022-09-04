package com.mozhimen.uicorek.viewk

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import com.mozhimen.basick.basek.BaseKView
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.uicorek.R

/**
 * @ClassName ViewKColorPicker
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/3 22:05
 * @Version 1.0
 */
class ViewKColorPicker @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
    BaseKView(context, attrs, defStyleAttr, defStyleRes) {

    //region # variate
    private var _colorStart = Color.WHITE
    private var _colorEnd = Color.BLACK
    private var _paintCircle: Paint? = null
    private var _paintOutline: Paint? = null
    private var _progress: Float = ANIM_MIN_VAL
    //endregion

    init {
        initView()
        initAttrs(attrs, defStyleAttr)
    }

    /**
     * 外围颜色
     */
    var colorOutline: Int
        get() = _paintOutline?.color ?: Color.TRANSPARENT
        set(value) {
            _paintOutline?.color = value
            invalidate()
        }

    /**
     * 颜色键值对
     */
    var colorPair: Pair<Int, Int>
        get() = _colorStart to _colorEnd
        set(value) {
            _colorStart = value.first
            _colorEnd = value.second
            invalidateColors()
        }

    /**
     * 重置
     */
    fun invalidateColors() {
        // If the value is set here, it risks getting a solid color if width is blue.
        // This way, it will be refreshed on onDraw.
        _paintCircle = null
        _paintOutline = null
        invalidate()
    }

    /**
     * 颜色是否设置
     * @return Boolean
     */
    fun areColorsSet(): Boolean =
        _paintCircle != null && _paintOutline != null

    /**
     * 反转选择
     */
    fun reverseSelection() {
        if (this.isSelected) {
            onDeselect(true)
        } else {
            onSelect(true)
        }
    }

    /**
     * 选择
     * @param animated Boolean
     */
    fun onSelect(animated: Boolean) {
        if (!this.isSelected) {
            startAnim(ANIM_MIN_VAL, ANIM_MAX_VAL, animated)
            this.isSelected = true
        }
    }

    /**
     * 反选
     * @param animated Boolean
     */
    fun onDeselect(animated: Boolean) {
        if (this.isSelected) {
            startAnim(ANIM_MAX_VAL, ANIM_MIN_VAL, animated)
            this.isSelected = false
        }
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewKColorPicker)
        _colorStart = typedArray.getColor(R.styleable.ViewKColorPicker_viewKColorPicker_colorStart, _colorStart)
        _colorEnd = typedArray.getColor(R.styleable.ViewKColorPicker_viewKColorPicker_colorEnd, _colorEnd)
        typedArray.recycle()
    }

    override fun initView() {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (_paintCircle == null) {
            _paintCircle = createPaintInside()
        }

        if (_paintOutline == null) {
            _paintOutline = createPaintOutside()
        }

        canvas.drawCircle(
            width.toFloat() / 2.0f,
            height.toFloat() / 2.0f,
            (realRadius - OUTER_PADDING.dp2px().toFloat()) * _progress,
            _paintOutline!!
        )

        canvas.drawCircle(
            width.toFloat() / 2.0f,
            height.toFloat() / 2.0f,
            (realRadius - INNER_PADDING.dp2px().toFloat()) * _progress,
            _paintCircle!!
        )
    }

    //region # private func
    private fun startAnim(fromVal: Float, toVal: Float, isAnimated: Boolean) {
        if (isAnimated) {
            val ofFloat = ObjectAnimator.ofFloat(fromVal, toVal)
            ofFloat.duration = ANIM_DURATION
            ofFloat.interpolator = AccelerateDecelerateInterpolator()
            ofFloat.addUpdateListener(animUpdateListener())
            ofFloat.start()
            return
        }
        this._progress = toVal
        invalidate()
    }

    private fun animUpdateListener(): ValueAnimator.AnimatorUpdateListener =
        ValueAnimator.AnimatorUpdateListener { valAnimator ->
            val valueAnimator = valAnimator?.animatedValue ?: throw NullPointerException()
            this._progress = (valueAnimator as Float).toFloat()
            this.invalidate()
        }

    private fun createPaintInside(): Paint {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        paint.shader = LinearGradient(
            width.toFloat(),
            0f,
            0f,
            height.toFloat(),
            _colorStart,
            _colorEnd,
            Shader.TileMode.MIRROR
        )
        return paint
    }

    private fun createPaintOutside(): Paint {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = Color.WHITE
        paint.shader = LinearGradient(
            width.toFloat(),
            0f,
            0f,
            height.toFloat(),
            _colorStart,
            _colorEnd,
            Shader.TileMode.CLAMP
        )
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = SHADER_STROKE.dp2px().toFloat()
        return paint
    }

    private companion object {
        private const val ANIM_MAX_VAL = 1.0f
        private const val ANIM_MIN_VAL = 0.0f
        private const val ANIM_DURATION = 250L
        private const val SHADER_STROKE = 3f
        private const val INNER_PADDING = 6f
        private const val OUTER_PADDING = 2f
    }
    //endregion
}