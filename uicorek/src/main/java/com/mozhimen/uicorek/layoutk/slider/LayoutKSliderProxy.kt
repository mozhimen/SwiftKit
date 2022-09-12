package com.mozhimen.uicorek.layoutk.slider

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.text.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.GestureDetectorCompat
import com.mozhimen.basick.basek.BaseViewCallback
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.sp2px
import com.mozhimen.basick.utilk.UtilKDisplay
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.layoutk.slider.commons.*
import com.mozhimen.uicorek.layoutk.slider.mos.LayoutKSliderBubble
import com.mozhimen.uicorek.layoutk.slider.mos.Section
import com.mozhimen.uicorek.layoutk.slider.temps.EurosTextLabelFormatter
import com.mozhimen.uicorek.viewk.ViewKTouch
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * @ClassName LayoutKSliderProxy
 * @Description
 *
 *  minLabel               maxLabel LabelTop
 *  minVal    currentVal    maxVal  ValTop
 *
 *               rod    section
 * |----------<======>----||-----| Slider
 *           ___/\___
 *          |  tip  |             Bubble
 *
 *  minLabel               maxLabel LabelBottom
 *  minVal    currentVal    maxVal  ValBottom
 *
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/8 14:33
 * @Version 1.0
 */
class LayoutKSliderProxy(
    private val _layoutKSlider: LayoutKSlider,
    private val _context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    BaseViewCallback() {

    companion object {
        const val DISTANCE_TEXT_BAR = 10f
        const val BUBBLE_PADDING_HORIZONTAL = 15f
        const val BUBBLE_PADDING_VERTICAL = 10f
        const val BUBBLE_ARROW_HEIGHT = 10f
        const val BUBBLE_ARROW_WIDTH = 20f
    }


    //region # variate
    private lateinit var _paintSlider: Paint
    private lateinit var _paintRod: Paint
    private lateinit var _paintSection: Paint
    private lateinit var _paintLabelTop: TextPaint
    private lateinit var _paintLabelBottom: TextPaint
    private lateinit var _paintBubbleText: TextPaint
    private lateinit var _paintBubble: Paint

    //private var _sliderColor = UtilKRes.getColor(R.color.blue_normal)
    private var _sliderHeight = 15f.dp2px()
    private var _sliderPaddingHorizontal = 0f
    private var _sliderMultiRegions = false
    private var _sliderRegionLeftColor = UtilKRes.getColor(R.color.blue_normal)
    private var _sliderRegionRightColor = UtilKRes.getColor(R.color.blue_light)
    private var _sliderRegionLabelIsShow = true
    private var _sliderRegionLabelColorIsFollowRegion = false

    private var _rodColor = _sliderRegionLeftColor
    private var _rodIsInside = false
    private var _rodRadius = 0f

    private val _labelTextColor = UtilKRes.getColor(R.color.blue_normal)
    private var _labelTopIsShow = true
    private var _labelBottomIsShow = true
    private var _labelTopTextSize = 12f.dp2px()
    private var _labelBottomTextSize = 12f.sp2px()

    private var _sectionCrossLineIsShow = true
    private val _sectionCrossLineColor = _sliderRegionLeftColor
    private val _sectionCrossLineWidth = 2f.dp2px()
    private var _sectionColorAfterLast = false
    private var _sectionColorBeforeRod = true

    private var _bubbleIsShow = true
    private var _bubbleEnableEditOnClick = true
    private val _bubbleColor = Color.WHITE
    private val _bubbleColorEditing = UtilKRes.getColor(R.color.blue_light)
    private val _bubbleTextColor = UtilKRes.getColor(R.color.blue_normal)
    private val _bubbleTextSize = 16f.dp2px()


    private var _rodX = 0f
    private var _rodCurrentVal = 0f
    private var _rodOldVal = Float.MIN_VALUE
    private var _rodMaxVal = 1000f
    private var _rodMinVal = 0f
    private var _sliderWidth = 0f
    private var _sliderY = 0f
    private var _sliderCenterY = 0f
    private var _labelTextMin = ""
    private var _labelTextMax = ""
    private var _bubbleIsEditing = false
    private var _bubbleText = ""

    private var _parentViewScrollable: ViewGroup? = null
    private var _isScrolling = false
    private var _measureSpecHeight = 0

    fun getMeasureSpecHeight(): Int = _measureSpecHeight

    private val _layoutKSliderBubble = LayoutKSliderBubble()
    private var _viewKTouch: ViewKTouch? = null
    private lateinit var _editText: EditText
    private var _textLabelFormatter: ITextLabelFormatter = EurosTextLabelFormatter()
    private var _textRodFormatter: ITextRodFormatter? = null
    private var _editListener: IEditListener? = null
    private var _sliderListener: ISliderListener? = null
    private var _bubbleListener: IBubbleListener? = null

    private val _sections: MutableList<Section> = ArrayList()
    private var _gestureDetector: GestureDetectorCompat = GestureDetectorCompat(_context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            if (_layoutKSliderBubble.isTapInBubbleArea(e)) {
                onBubbleClicked()
            }
            return super.onSingleTapConfirmed(e)
        }

        override fun onContextClick(e: MotionEvent): Boolean {
            return super.onContextClick(e)
        }
    })
    //endregion

    fun setSliderListener(sliderListener: ISliderListener) {
        _sliderListener = sliderListener
    }

    fun setBubbleListener(bubbleListener: IBubbleListener) {
        _bubbleListener = bubbleListener
    }

    fun setEditListener(editListener: IEditListener) {
        _editListener = editListener
    }

    fun setTextLabelFormatter(textLabelFormatter: ITextLabelFormatter) {
        _textLabelFormatter = textLabelFormatter
        update()
    }

    fun setTextRodFormatter(textRodFormatter: ITextRodFormatter) {
        _textRodFormatter = textRodFormatter
        update()
    }

    fun addSection(sections: List<Section>) {
        _sections.addAll(sections)
        _sections.sort()
        update()
    }

    fun addSection(section: Section) {
        _sections.add(section)
        _sections.sort()
        update()
    }

    fun clearSections() {
        _sections.clear()
        update()
    }

    fun getRodMaxVal(): Float {
        return _rodMaxVal
    }

    fun setRodMaxVal(rodMaxVal: Float) {
        this._rodMaxVal = rodMaxVal
        updateValues()
        update()
    }

    fun setRodMinVal(rodMinVal: Float) {
        this._rodMinVal = rodMinVal
        updateValues()
        update()
    }

    fun getRodCurrentVal(): Float {
        return _rodCurrentVal
    }

    fun setRodCurrentVal(rodCurrentVal: Float) {
        _rodCurrentVal = rodCurrentVal
        updateValues()
        update()
    }

    fun setLabelTextMax(labelTextMax: String) {
        this._labelTextMax = labelTextMax
        _layoutKSlider.postInvalidate()
    }

    fun setLabelTextMin(labelTextMin: String) {
        this._labelTextMin = labelTextMin
        _layoutKSlider.postInvalidate()
    }

    fun setParentViewScrollable(parentViewScrollable: ViewGroup?) {
        _parentViewScrollable = parentViewScrollable
    }

    fun setSectionColorAfterLast(sectionColorAfterLast: Boolean) {
        this._sectionColorAfterLast = sectionColorAfterLast
        update()
    }

    fun setLabelTopIsShow(labelTopIsShow: Boolean) {
        this._labelTopIsShow = labelTopIsShow
        update()
    }

    fun setLabelBottomIsShow(labelBottomIsShow: Boolean) {
        this._labelBottomIsShow = labelBottomIsShow
        update()
    }

    fun setBubbleIsShow(bubbleIsShow: Boolean) {
        this._bubbleIsShow = bubbleIsShow
        update()
    }

    fun getBubbleIsShow(): Boolean = _bubbleIsShow

    fun setModeRegion(modeRegion: Boolean) {
        this._sliderMultiRegions = modeRegion
        update()
    }

    fun setSliderRegionLeftColor(sliderRegionLeftColor: Int) {
        this._sliderRegionLeftColor = sliderRegionLeftColor
        update()
    }

    fun setSliderRegionRightColor(sliderRegionRightColor: Int) {
        this._sliderRegionRightColor = sliderRegionRightColor
        update()
    }

    fun setLabelTopTextSize(textSize: Int) {
        _labelTopTextSize = textSize
        _paintLabelTop.textSize = textSize.toFloat()
        update()
    }

    fun setLabelBottomTextSize(textSize: Int) {
        _labelBottomTextSize = textSize
        _paintLabelBottom.textSize = textSize.toFloat()
        update()
    }

    init {
        initAttrs(attrs, defStyleAttr)
        initPaint()
        initView()
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs ?: return
        val typedArray = _context.obtainStyledAttributes(attrs, R.styleable.LayoutKSlider)
        _sectionColorAfterLast =
            typedArray.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_step_colorizeAfterLast, _sectionColorAfterLast)
        _sectionCrossLineIsShow =
            typedArray.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_step_drawLine, _sectionCrossLineIsShow)
        _sectionColorBeforeRod =
            typedArray.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_step_colorizeOnlyBeforeIndicator, _sectionColorBeforeRod)
        _labelTopIsShow =
            typedArray.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_textTop_visible, _labelTopIsShow)
        _labelTopTextSize =
            typedArray.getDimensionPixelSize(R.styleable.LayoutKSlider_layoutKSlider_textTop_size, _labelTopTextSize)
        _labelBottomIsShow =
            typedArray.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_textBottom_visible, _labelBottomIsShow)
        _labelBottomTextSize =
            typedArray.getDimensionPixelSize(R.styleable.LayoutKSlider_layoutKSlider_textBottom_size, _labelBottomTextSize)
        _sliderHeight =
            typedArray.getDimensionPixelOffset(R.styleable.LayoutKSlider_layoutKSlider_height, _sliderHeight)
        _bubbleIsShow =
            typedArray.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_draw_bubble, _bubbleIsShow)
        _sliderMultiRegions =
            typedArray.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_regions, _sliderMultiRegions)
        _sliderRegionLeftColor =
            typedArray.getColor(R.styleable.LayoutKSlider_layoutKSlider_region_leftColor, _sliderRegionLeftColor)
        _sliderRegionRightColor =
            typedArray.getColor(R.styleable.LayoutKSlider_layoutKSlider_region_rightColor, _sliderRegionRightColor)
        _rodIsInside =
            typedArray.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_indicator_inside, _rodIsInside)
        _sliderRegionLabelColorIsFollowRegion =
            typedArray.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_regions_textFollowRegionColor, _sliderRegionLabelColorIsFollowRegion)
        _sliderRegionLabelIsShow =
            typedArray.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_regions_centerText, _sliderRegionLabelIsShow)
        _bubbleEnableEditOnClick =
            typedArray.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_enableEditBubble, _bubbleEnableEditOnClick)
        typedArray.recycle()
    }

    override fun initPaint() {
        _paintRod = Paint()
        _paintRod.isAntiAlias = true
        _paintRod.strokeWidth = 2f
        _paintRod.color = _rodColor

        _paintSlider = Paint()
        _paintSlider.isAntiAlias = true
        _paintSlider.strokeWidth = 2f
        _paintSlider.color = _sliderRegionLeftColor

        _paintSection = Paint()
        _paintSection.isAntiAlias = true
        _paintSection.strokeWidth = _sectionCrossLineWidth.toFloat()
        _paintSection.color = _sectionCrossLineColor

        _paintBubble = Paint()
        _paintBubble.isAntiAlias = true
        _paintBubble.strokeWidth = 3f
        _paintBubble.color = _bubbleColor

        _paintLabelTop = TextPaint()
        _paintLabelTop.isAntiAlias = true
        _paintLabelTop.style = Paint.Style.FILL
        _paintLabelTop.color = _labelTextColor
        _paintLabelTop.textSize = _labelTopTextSize.toFloat()

        _paintLabelBottom = TextPaint()
        _paintLabelBottom.isAntiAlias = true
        _paintLabelBottom.style = Paint.Style.FILL
        _paintLabelBottom.color = _labelTextColor
        _paintLabelBottom.textSize = _labelBottomTextSize.toFloat()

        _paintBubbleText = TextPaint()
        _paintBubbleText.isAntiAlias = true
        _paintBubbleText.style = Paint.Style.FILL
        _paintBubbleText.color = _bubbleTextColor
        _paintBubbleText.textSize = _bubbleTextSize.toFloat()
    }

    override fun initView() {
        setLabelTopTextSize(_labelTopTextSize)
        setLabelBottomTextSize(_labelBottomTextSize)
    }

    fun onDraw(globalWidth: Int, globalHeight: Int, canvas: Canvas) {
        canvas.save()

        val paddingLeft = _sliderPaddingHorizontal
        val paddingRight = _sliderPaddingHorizontal
        if (isRegions()) {
            if (_sections.isEmpty()) {
                _paintRod.color = _sliderRegionLeftColor
                _paintBubble.color = _sliderRegionLeftColor
            } else {
                _paintRod.color = _sliderRegionRightColor
                _paintBubble.color = _sliderRegionRightColor
            }
        } else {
            val selectionBeforeCustor = findSectionOfCustor()
            if (selectionBeforeCustor != null) {
                _paintRod.color = selectionBeforeCustor.colorBefore
                _paintBubble.color = selectionBeforeCustor.colorBefore
            } else {
                if (_sectionColorAfterLast) {
                    val beforeCustor = findStepBeforeCustor()
                    if (beforeCustor != null) {
                        _paintRod.color = beforeCustor.colorAfter
                        _paintBubble.color = beforeCustor.colorAfter
                    }
                } else {
                    _paintRod.color = _rodColor
                    _paintBubble.color = _bubbleColor
                }
            }
        }
        val radiusCorner = _sliderHeight / 2f
        val indicatorCenterX = _rodX + paddingLeft
        kotlin.run { //background
            val centerCircleRight = globalWidth - paddingRight

            //grey background
            if (isRegions()) {
                if (_sections.isEmpty()) {
                    _paintSlider.color = _sliderRegionLeftColor
                } else {
                    _paintSlider.color = _sliderRegionRightColor
                }
            } else {
                _paintSlider.color = _sliderRegionLeftColor
            }
            canvas.drawCircle(paddingLeft, _sliderCenterY, radiusCorner, _paintSlider)
            canvas.drawCircle(centerCircleRight, _sliderCenterY, radiusCorner, _paintSlider)
            canvas.drawRect(
                paddingLeft,
                _sliderY,
                centerCircleRight,
                _sliderY + _sliderHeight,
                _paintSlider
            )
            if (isRegions()) {
                _paintSlider.color = _sliderRegionLeftColor
                canvas.drawCircle(paddingLeft, _sliderCenterY, radiusCorner, _paintSlider)
                canvas.drawRect(
                    paddingLeft,
                    _sliderY,
                    indicatorCenterX,
                    _sliderY + _sliderHeight,
                    _paintSlider
                )
            } else {
                var lastX = paddingLeft
                var first = true
                for (step in _sections) {
                    _paintSlider.color = step.colorBefore
                    if (first) {
                        canvas.drawCircle(paddingLeft, _sliderCenterY, radiusCorner, _paintSlider)
                    }
                    val x = step.startPos + paddingLeft
                    if (!_sectionColorBeforeRod) {
                        canvas.drawRect(lastX, _sliderY, x, _sliderY + _sliderHeight, _paintSlider)
                    } else {
                        canvas.drawRect(
                            lastX,
                            _sliderY,
                            x.coerceAtMost(indicatorCenterX),
                            _sliderY + _sliderHeight,
                            _paintSlider
                        )
                    }
                    lastX = x
                    first = false
                }
                if (_sectionColorAfterLast) {
                    //find the step just below currentValue
                    for (i in _sections.indices.reversed()) {
                        val step = _sections[i]
                        if (_rodCurrentVal - _rodMinVal > step.value) {
                            _paintSlider.color = step.colorAfter
                            canvas.drawRect(
                                step.startPos + paddingLeft,
                                _sliderY,
                                indicatorCenterX,
                                _sliderY + _sliderHeight,
                                _paintSlider
                            )
                            break
                        }
                    }
                }
            }
        }
        kotlin.run { //texts top (values)
            if (_labelTopIsShow) {
                val textY = _sliderY - UtilKDisplay.dp2px(DISTANCE_TEXT_BAR)
                if (isRegions()) {
                    val leftValue: Float
                    val rightValue: Float
                    if (_sliderRegionLabelIsShow) {
                        leftValue = _rodCurrentVal
                        rightValue = _rodMaxVal - leftValue
                    } else {
                        leftValue = _rodMinVal
                        rightValue = _rodMaxVal
                    }
                    if (_sliderRegionLabelColorIsFollowRegion) {
                        _paintLabelTop.color = _sliderRegionLeftColor
                    }
                    var textX: Float = if (_sliderRegionLabelIsShow) {
                        (indicatorCenterX - paddingLeft) / 2f + paddingLeft
                    } else {
                        paddingLeft
                    }
                    drawIndicatorsTextAbove(
                        canvas,
                        globalWidth,
                        formatRegionValue(0, leftValue),
                        _paintLabelTop,
                        textX,
                        textY,
                        Layout.Alignment.ALIGN_CENTER
                    )
                    if (_sliderRegionLabelColorIsFollowRegion) {
                        _paintLabelTop.color = _sliderRegionRightColor
                    }
                    textX = if (_sliderRegionLabelIsShow) {
                        indicatorCenterX + (_sliderWidth - indicatorCenterX - paddingLeft) / 2f + paddingLeft
                    } else {
                        paddingLeft + _sliderWidth
                    }
                    drawIndicatorsTextAbove(
                        canvas,
                        globalWidth,
                        formatRegionValue(1, rightValue),
                        _paintLabelTop,
                        textX,
                        textY,
                        Layout.Alignment.ALIGN_CENTER
                    )
                } else {
                    drawIndicatorsTextAbove(
                        canvas,
                        globalWidth,
                        formatTextLabel(_rodMinVal),
                        _paintLabelTop,
                        0 + paddingLeft,
                        textY,
                        Layout.Alignment.ALIGN_CENTER
                    )
                    for (step in _sections) {
                        drawIndicatorsTextAbove(
                            canvas,
                            globalWidth,
                            formatTextLabel(step.value),
                            _paintLabelTop,
                            step.startPos + paddingLeft,
                            textY,
                            Layout.Alignment.ALIGN_CENTER
                        )
                    }
                    drawIndicatorsTextAbove(
                        canvas,
                        globalWidth,
                        formatTextLabel(_rodMaxVal),
                        _paintLabelTop,
                        canvas.width.toFloat(),
                        textY,
                        Layout.Alignment.ALIGN_CENTER
                    )
                }
            }
        }
        kotlin.run { //steps + bottom text
            val bottomTextY = _sliderY + _sliderHeight + 15
            for (section in _sections) {
                if (_sectionCrossLineIsShow) {
                    canvas.drawLine(
                        section.startPos + paddingLeft,
                        _sliderY - _sliderHeight / 4f,
                        section.startPos + paddingLeft,
                        _sliderY + _sliderHeight + _sliderHeight / 4f,
                        _paintSection
                    )
                }
                if (_labelBottomIsShow) {
                    //drawMultilineText(canvas, maxText, canvas.getWidth() - settings.paintText.measureText(maxText), textY, settings.paintText, Layout.Alignment.ALIGN_OPPOSITE);
                    drawMultilineText(
                        canvas,
                        section.name,
                        section.startPos + paddingLeft,
                        bottomTextY,
                        _paintLabelBottom,
                        Layout.Alignment.ALIGN_CENTER
                    )
                }
            }
            if (_labelBottomIsShow) {
                if (!TextUtils.isEmpty(_labelTextMax)) {
                    drawMultilineText(
                        canvas,
                        _labelTextMax,
                        globalWidth.toFloat(),
                        bottomTextY,
                        _paintLabelBottom,
                        Layout.Alignment.ALIGN_CENTER
                    )
                }
                if (!TextUtils.isEmpty(_labelTextMin)) {
                    drawMultilineText(
                        canvas,
                        _labelTextMin,
                        0f,
                        bottomTextY,
                        _paintLabelBottom,
                        Layout.Alignment.ALIGN_CENTER
                    )
                }
            }
        }

        //indicator
        kotlin.run {
            val color = _paintRod.color
            canvas.drawCircle(indicatorCenterX, this._sliderCenterY, _rodRadius.toFloat(), _paintRod)
            _paintRod.color = Color.WHITE
            canvas.drawCircle(indicatorCenterX, this._sliderCenterY, _rodRadius * 0.85f, _paintRod)
            _paintRod.color = color
        }

        //bubble
        kotlin.run {
            if (_bubbleIsShow) {
                var bubbleCenterX = indicatorCenterX
                _layoutKSliderBubble.setX(bubbleCenterX - _layoutKSliderBubble.getWidth() / 2f)
                _layoutKSliderBubble.setY(0f)
                if (bubbleCenterX > globalWidth - _layoutKSliderBubble.getWidth() / 2f) {
                    bubbleCenterX = globalWidth - _layoutKSliderBubble.getWidth() / 2f
                } else if (bubbleCenterX - _layoutKSliderBubble.getWidth() / 2f < 0) {
                    bubbleCenterX = _layoutKSliderBubble.getWidth() / 2f
                }
                val trangleCenterX: Float = (bubbleCenterX + indicatorCenterX) / 2f
                drawBubble(canvas, bubbleCenterX, trangleCenterX, 0f)
            }
        }
        canvas.restore()
    }

    private fun scrollSlider(event: MotionEvent) {
        if (_isScrolling) {
            var evX = event.x
            evX -= _sliderPaddingHorizontal
            if (evX < 0) {
                evX = 0f
            }
            if (evX > _sliderWidth) {
                evX = _sliderWidth
            }
            _rodX = evX
            update()
        }
    }

    private fun onBubbleClicked() {
        if (_bubbleEnableEditOnClick) {
            this._bubbleIsEditing = true
            _editText = object : AppCompatEditText(_context) {
                override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
                    if (event.keyCode == KeyEvent.KEYCODE_BACK) {
                        dispatchKeyEvent(event)
                        closeEditText()
                        return false
                    }
                    return super.onKeyPreIme(keyCode, event)
                }
            }
            val editMaxCharCount = 9
            _editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(editMaxCharCount))
            _editText.isFocusable = true
            _editText.isFocusableInTouchMode = true
            _editText.setSelectAllOnFocus(true)
            _editText.isSingleLine = true
            _editText.gravity = Gravity.CENTER
            _editText.inputType = InputType.TYPE_CLASS_NUMBER
            _editText.setTextColor(_paintRod.color)
            _editText.background = ColorDrawable(Color.TRANSPARENT)
            _editText.setPadding(0, 0, 0, 0)
            _editText.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                UtilKDisplay.dp2px(_bubbleTextSize.toFloat()).toFloat()
            )
            _bubbleText = _rodCurrentVal.toInt().toString()
            _editText.setText(_bubbleText)
            val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.width = _layoutKSliderBubble.getWidth().toInt()
            params.height = _layoutKSliderBubble.getSliderHeight().toInt()
            _editText.layoutParams = params
            val rect = Rect()

            _layoutKSlider.getGlobalVisibleRect(rect)
            _viewKTouch = ViewKTouch(_context)
            _viewKTouch!!.setTouchArea(rect)
            getActivityDecorView().addView(_viewKTouch)
            _editText.postDelayed({
                val imm = _context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(_editText, InputMethodManager.SHOW_IMPLICIT)
                _viewKTouch!!.setListener {
                    closeEditText()
                }
            }, 300)
            _layoutKSlider.addView(_editText)
            _editText.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    editBubbleEditPosition()
                    _editText.viewTreeObserver.removeOnPreDrawListener(this)
                    return false
                }
            })
            _editText.requestFocus()
            _editText.requestFocusFromTouch()
            editBubbleEditPosition()
            if (_editListener != null) {
                _editListener!!.onEdit(_editText)
            }
            _editText.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    closeEditText()
                    return@OnKeyListener true
                }
                false
            })
            _editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    _bubbleText = _editText.text.toString()
                    updateBubbleWidth()
                    _layoutKSlider.invalidate()
                    editBubbleEditPosition()
                }

                override fun afterTextChanged(s: Editable) {}
            })
            _layoutKSlider.postInvalidate()
        }
        if (_bubbleListener != null) {
            _bubbleListener!!.onClick(_layoutKSlider)
        }
    }

    private fun updateBubbleWidth() {
        _layoutKSliderBubble.setWidth(calculateBubbleTextWidth() + (BUBBLE_PADDING_HORIZONTAL).dp2px() * 2f)
        _layoutKSliderBubble.setWidth(max(150f, _layoutKSliderBubble.getWidth()))
    }

    private fun calculateBubbleTextWidth(): Float {
        var bubbleText = formatTextLabel(_rodCurrentVal)
        if (_bubbleIsEditing) {
            bubbleText = _bubbleText
        }
        return _paintBubbleText.measureText(bubbleText)
    }

    private fun editBubbleEditPosition() {
        if (_bubbleIsEditing) {
            _editText.x = min(_layoutKSliderBubble.getX(), _layoutKSlider.width.toFloat() - _editText.width)
            _editText.y = _layoutKSliderBubble.getY()
            val params = _editText.layoutParams
            params.width = _layoutKSliderBubble.getWidth().toInt()
            params.height = _layoutKSliderBubble.getSliderHeight().toInt()
            _editText.layoutParams = params
            _editText.animate().alpha(1f)
        }
    }

    fun closeEditText() {
        _editText.clearFocus()
        val imm = _context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(_editText.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        (_viewKTouch!!.parent as ViewGroup).removeView(_viewKTouch)
        _layoutKSlider.removeView(_editText)
        _bubbleIsEditing = false
        if (TextUtils.isEmpty(_bubbleText)) {
            _bubbleText = _rodCurrentVal.toString()
        }
        var value: Float? = try {
            java.lang.Float.valueOf(_bubbleText)
        } catch (e: Exception) {
            e.printStackTrace()
            _rodMinVal
        }
        value = (value)!!.coerceAtMost(_rodMaxVal)
        value = max(value, _rodMaxVal)
        val valueAnimator = ValueAnimator.ofFloat(_rodCurrentVal, value)
        valueAnimator.addUpdateListener { animation ->
            setCurrentValueNoUpdate(animation.animatedValue as Float)
            _layoutKSlider.postInvalidate()
        }
        valueAnimator.interpolator = AccelerateInterpolator()
        valueAnimator.start()
        //editText = null
        _viewKTouch = null
        _layoutKSlider.postInvalidate()
    }

    private fun setCurrentValueNoUpdate(value: Float) {
        _rodCurrentVal = value
        _sliderListener?.onScroll(_layoutKSlider, _rodCurrentVal)
        updateValues()
    }

    private fun getActivityDecorView(): ViewGroup {
        return (_context as Activity).window.decorView as ViewGroup
    }

    private fun findSectionOfCustor(): Section? {
        for (i in _sections.indices) {
            val step = _sections[i]
            if (_rodCurrentVal - _rodMinVal <= step.value) {
                return step
            }
        }
        return null
    }

    private fun findStepBeforeCustor(): Section? {
        for (i in _sections.indices.reversed()) {
            val step = _sections[i]
            if (_rodCurrentVal - _rodMinVal >= step.value) {
                return step
            }
            break
        }
        return null
    }

    private fun isRegions(): Boolean {
        return _sliderMultiRegions || _sections.isEmpty()
    }

    private fun drawText(canvas: Canvas, text: String, x: Float, y: Float, paint: TextPaint, alignment: Layout.Alignment) {
        canvas.save()
        canvas.translate(x, y)
        val staticLayout = StaticLayout(text, paint, paint.measureText(text).toInt(), alignment, 1.0f, 0f, false)
        staticLayout.draw(canvas)
        canvas.restore()
    }

    private fun drawMultilineText(canvas: Canvas, text: String, x: Float, y: Float, paint: TextPaint, alignment: Layout.Alignment) {
        val lineHeight = paint.textSize
        var lineY = y
        for (line in text.split("\n").toTypedArray()) {
            canvas.save()
            run {
                val lineWidth = paint.measureText(line).toInt().toFloat()
                var lineX = x
                if (alignment == Layout.Alignment.ALIGN_CENTER) {
                    lineX -= lineWidth / 2f
                }
                if (lineX < 0) {
                    lineX = 0f
                }
                val right = lineX + lineWidth
                if (right > canvas.width) {
                    lineX = canvas.width - lineWidth - _sliderPaddingHorizontal
                }
                canvas.translate(lineX, lineY)
                val staticLayout = StaticLayout(line, paint, lineWidth.toInt(), alignment, 1.0f, 0f, false)
                staticLayout.draw(canvas)
                lineY += lineHeight
            }
            canvas.restore()
        }
    }

    fun updateValues() {
        if (_rodCurrentVal < _rodMinVal) _rodCurrentVal = _rodMinVal
        _sliderPaddingHorizontal = _sliderHeight.toFloat()
        _sliderWidth = _layoutKSlider.width - _sliderPaddingHorizontal * 2
        if (_bubbleIsShow) {
            updateBubbleWidth()
            _layoutKSliderBubble.setHeight(
                _bubbleTextSize.dp2px().toFloat() + BUBBLE_PADDING_VERTICAL.dp2px() * 2f + BUBBLE_ARROW_HEIGHT.dp2px()
            )
        } else {
            _layoutKSliderBubble.setHeight(0f)
        }
        _sliderY = 0f
        if (_labelTopIsShow) {
            _sliderY += DISTANCE_TEXT_BAR * 2
            if (isRegions()) {
                var topTextHeight = 0f
                val tmpTextLeft = formatRegionValue(0, 0f)
                val tmpTextRight = formatRegionValue(1, 0f)
                topTextHeight = Math.max(topTextHeight, calculateTextMultilineHeight(tmpTextLeft, _paintLabelTop))
                topTextHeight = Math.max(topTextHeight, calculateTextMultilineHeight(tmpTextRight, _paintLabelTop))
                _sliderY += topTextHeight + 3
            } else {
                var topTextHeight = 0f
                for (step in _sections) {
                    topTextHeight = Math.max(
                        topTextHeight,
                        calculateTextMultilineHeight(formatTextLabel(step.value), _paintLabelBottom)
                    )
                }
                _sliderY += topTextHeight
            }
        } else {
            if (_labelTopIsShow) {
                _sliderY -= UtilKDisplay.dp2px(BUBBLE_ARROW_HEIGHT) / 1.5f
            }
        }
        _sliderY += _layoutKSliderBubble.getHeight()
        _sliderCenterY = _sliderY + _sliderHeight / 2f
        if (_rodIsInside) {
            _rodRadius = _sliderHeight * .5f
        } else {
            _rodRadius = _sliderHeight * .9f
        }
        for (step in _sections) {
            val stoppoverPercent = step.value / (_rodMaxVal - _rodMinVal)
            step.startPos = stoppoverPercent * _sliderWidth
        }
        _rodX = (_rodCurrentVal - _rodMinVal) / (_rodMaxVal - _rodMinVal) * _sliderWidth
        _measureSpecHeight = (_sliderCenterY + _rodRadius).toInt()
        var bottomTextHeight = 0f
        if (!TextUtils.isEmpty(_labelTextMax)) {
            bottomTextHeight = max(
                calculateTextMultilineHeight(_labelTextMax, _paintLabelBottom),
                calculateTextMultilineHeight(_labelTextMin, _paintLabelBottom)
            )
        }
        for (step in _sections) {
            bottomTextHeight = Math.max(
                bottomTextHeight,
                calculateTextMultilineHeight(step.name, _paintLabelBottom)
            )
        }
        _measureSpecHeight += bottomTextHeight.toInt()
        _measureSpecHeight += 10 //padding bottom
    }

    fun update() {
        if (_sliderWidth > 0f) {
            val currentPercent = _rodX / _sliderWidth
            _rodCurrentVal = currentPercent * (_rodMaxVal - _rodMinVal) + _rodMinVal
            _rodCurrentVal = _rodCurrentVal.roundToInt().toFloat()
            if (_sliderListener != null && _rodOldVal != _rodCurrentVal) {
                _rodOldVal = _rodCurrentVal
                _sliderListener?.onScroll(_layoutKSlider, _rodCurrentVal)
            }
            updateBubbleWidth()
            editBubbleEditPosition()
        }
        _layoutKSlider.postInvalidate()
    }

    private fun drawIndicatorsTextAbove(
        canvas: Canvas,
        globalWidth: Int,
        text: String,
        paintText: TextPaint,
        globalX: Float,
        globalY: Float,
        alignment: Layout.Alignment
    ) {
        var x = globalX
        var y = globalY
        val textHeight = calculateTextMultilineHeight(text, paintText)
        y -= textHeight
        val width = paintText.measureText(text).toInt()
        x = if (x >= globalWidth - _sliderPaddingHorizontal) {
            globalWidth - width - _sliderPaddingHorizontal / 2f
        } else if (x <= 0) {
            width / 2f
        } else {
            x - width / 2f
        }
        if (x < 0) {
            x = 0f
        }
        if (x + width > globalWidth) {
            x = (globalWidth - width).toFloat()
        }
        drawText(canvas, text, x, y, paintText, alignment)
    }

    private fun drawBubblePath(canvas: Canvas, triangleCenterX: Float, height: Float, width: Float) {
        val path = Path()
        val padding = 3
        val rect = Rect(padding, padding, width.toInt() - padding, (height - UtilKDisplay.dp2px(BUBBLE_ARROW_HEIGHT)).toInt() - padding)
        val roundRectHeight = (height - UtilKDisplay.dp2px(BUBBLE_ARROW_HEIGHT)) / 2
        path.moveTo(rect.left + roundRectHeight, rect.top.toFloat())
        path.lineTo(rect.right - roundRectHeight, rect.top.toFloat())
        path.quadTo(rect.right.toFloat(), rect.top.toFloat(), rect.right.toFloat(), rect.top + roundRectHeight)
        path.lineTo(rect.right.toFloat(), rect.bottom - roundRectHeight)
        path.quadTo(rect.right.toFloat(), rect.bottom.toFloat(), rect.right - roundRectHeight, rect.bottom.toFloat())
        path.lineTo(
            triangleCenterX + UtilKDisplay.dp2px(BUBBLE_ARROW_WIDTH) / 2f,
            height - UtilKDisplay.dp2px(BUBBLE_ARROW_HEIGHT) - padding
        )
        path.lineTo(triangleCenterX, height - padding)
        path.lineTo(
            triangleCenterX - UtilKDisplay.dp2px(BUBBLE_ARROW_WIDTH) / 2f,
            height - UtilKDisplay.dp2px(BUBBLE_ARROW_HEIGHT) - padding
        )
        path.lineTo(rect.left + roundRectHeight, rect.bottom.toFloat())
        path.quadTo(rect.left.toFloat(), rect.bottom.toFloat(), rect.left.toFloat(), rect.bottom - roundRectHeight)
        path.lineTo(rect.left.toFloat(), rect.top + roundRectHeight)
        path.quadTo(rect.left.toFloat(), rect.top.toFloat(), rect.left + roundRectHeight, rect.top.toFloat())
        path.close()
        canvas.drawPath(path, _paintBubble)
    }

    private fun formatRegionValue(region: Int, value: Float): String {
        return if (_textRodFormatter != null) {
            _textRodFormatter!!.format(region, value)
        } else {
            formatTextLabel(value)
        }
    }

    private fun formatTextLabel(value: Float): String {
        return _textLabelFormatter.format(value)
    }

    fun handleTouch(event: MotionEvent): Boolean {
        if (getBubbleIsShow()) return false
        val handledByDetector = _gestureDetector.onTouchEvent(event)
        if (!handledByDetector) {
            when (event.actionMasked) {
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    _parentViewScrollable?.requestDisallowInterceptTouchEvent(false)
                    _isScrolling = false
                }
                MotionEvent.ACTION_DOWN -> {
                    _isScrolling = if (event.y <= _sliderY || event.y >= _sliderY + _sliderWidth) {
                        return true
                    } else {
                        true
                    }
                    _parentViewScrollable?.requestDisallowInterceptTouchEvent(true)
                    scrollSlider(event)
                }
                MotionEvent.ACTION_MOVE -> {
                    scrollSlider(event)
                }
            }
        }
        return true
    }

    private fun drawBubble(canvas: Canvas, centerX: Float, triangleCenterX: Float, y: Float) {
        var triangleCenterX = triangleCenterX
        val width = _layoutKSliderBubble.getWidth()
        val height = _layoutKSliderBubble.getHeight()
        canvas.save()
        run {
            canvas.translate(centerX - width / 2f, y)
            triangleCenterX -= centerX - width / 2f
            if (!this._bubbleIsEditing) {
                drawBubblePath(canvas, triangleCenterX, height, width)
            } else {
                val savedColor = _paintBubble.color
                _paintBubble.color = _bubbleColorEditing
                _paintBubble.style = Paint.Style.FILL
                drawBubblePath(canvas, triangleCenterX, height, width)
                _paintBubble.style = Paint.Style.STROKE
                _paintBubble.color = _paintRod.color
                drawBubblePath(canvas, triangleCenterX, height, width)
                _paintBubble.style = Paint.Style.FILL
                _paintBubble.color = savedColor
            }
            if (!this._bubbleIsEditing) {
                val bubbleText = formatTextLabel(_rodCurrentVal)
                drawText(
                    canvas,
                    bubbleText,
                    UtilKDisplay.dp2px(BUBBLE_PADDING_HORIZONTAL).toFloat(),
                    (UtilKDisplay.dp2px(BUBBLE_PADDING_VERTICAL) - 3).toFloat(),
                    _paintBubbleText,
                    Layout.Alignment.ALIGN_NORMAL
                )
            }
        }
        canvas.restore()
    }

    private fun calculateTextMultilineHeight(text: String, textPaint: TextPaint): Float {
        return text.split("\n").toTypedArray().size * textPaint.textSize
    }
}