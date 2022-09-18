package com.mozhimen.uicorek.layoutk.slider

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.EditText
import com.mozhimen.basick.basek.BaseViewCallback
import com.mozhimen.uicorek.layoutk.slider.commons.IBubbleListener
import com.mozhimen.uicorek.layoutk.slider.commons.ILabelTextFormatter
import com.mozhimen.uicorek.layoutk.slider.commons.ISliderListener
import com.mozhimen.uicorek.layoutk.slider.helpers.LayoutKSliderParser
import com.mozhimen.uicorek.layoutk.slider.mos.LayoutKSliderAttrs
import com.mozhimen.uicorek.layoutk.slider.mos.Rod
import com.mozhimen.uicorek.layoutk.slider.mos.Section
import com.mozhimen.uicorek.layoutk.slider.mos.Slider
import com.mozhimen.uicorek.viewk.ViewKTouch

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
    private val _context: Context,
) :
    BaseViewCallback() {

    //region # variate
    private lateinit var _layoutKSlider: LayoutKSlider
    private lateinit var _attrs: LayoutKSliderAttrs
    private lateinit var _slider: Slider
    private lateinit var _rod: Rod
    private var _scrollableParentView: ViewGroup? = null
    private var _rodIsScrolling = false

    private lateinit var _paintSlider: Paint
    private lateinit var _paintRod: Paint

    private var _sliderListener: ISliderListener? = null
    //dynamic variate

    private var _rodX = 0f
    private var _rodCurrentVal = 0f
    private var _rodOldVal = _rodCurrentVal
    private var _rodMinVal = 0f
    private var _rodMaxVal = 100f

    private var _labelTextMin = ""
    private var _labelTextMax = ""

    private var _bubbleText = ""
    private var _bubbleIsEditing = false
    //dynamic variate

    //private lateinit var _paintSectionCrossLine: Paint
//    private lateinit var _paintLabelTop: TextPaint
//    private lateinit var _paintLabelBottom: TextPaint
    // private lateinit var _paintBubbleText: TextPaint
    // private lateinit var _paintBubble: Paint

    private var _viewKTouch: ViewKTouch? = null

    private lateinit var _editText: EditText
    private var _labelTextFormatter: ILabelTextFormatter? = null/*EurosLabelTextFormatter()*/
    private var _bubbleListener: IBubbleListener? = null
    private val _sections: MutableList<Section> = ArrayList()
    /*private var _gestureDetector: GestureDetectorCompat = GestureDetectorCompat(_context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            if (_bubble.isTapInBubbleArea(e)) {
                onBubbleClicked()
            }
            return super.onSingleTapConfirmed(e)
        }

        override fun onContextClick(e: MotionEvent): Boolean {
            return super.onContextClick(e)
        }
    })*/
    //endregion

    fun init(layoutKSlider: LayoutKSlider) {
        _layoutKSlider = layoutKSlider
    }

    fun getCurrentVal(): Float =
        _rod.currentVal

    fun setSliderListener(sliderListener: ISliderListener) {
        _sliderListener = sliderListener
    }

    //region # public func
    fun getHeightMeasureSpec(): Int {
        var height = 0
        height += if (_attrs.rodIsInside) _attrs.sliderHeight.toInt() else _attrs.rodRadius.toInt() * 2
        return height
    }

    fun attachScrollableParentView(scrollableParentView: ViewGroup?) {
        _scrollableParentView = scrollableParentView
    }
    //endregion

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs ?: return
        _attrs = LayoutKSliderParser.parseAttrs(_context, attrs)
    }

    override fun initPaint() {
        _paintRod = Paint()
        _paintRod.isAntiAlias = true
        _paintRod.strokeWidth = 2f
        _paintRod.color = _attrs.rodColor

        _paintSlider = Paint()
        _paintSlider.isAntiAlias = true
        _paintSlider.strokeWidth = 2f
        _paintSlider.color = _attrs.sliderRodRightColor

//        _paintSectionCrossLine = Paint()
//        _paintSectionCrossLine.isAntiAlias = true
//        _paintSectionCrossLine.strokeWidth = _attrs.sectionCrossLineWidth
//        _paintSectionCrossLine.color = _attrs.sectionCrossLineColor

//        _paintBubble = Paint()
//        _paintBubble.isAntiAlias = true
//        _paintBubble.strokeWidth = 3f
//        _paintBubble.color = _attrs.bubbleColor

//        _paintLabelTop = TextPaint()
//        _paintLabelTop.isAntiAlias = true
//        _paintLabelTop.style = Paint.Style.FILL
//        _paintLabelTop.color = _attrs.labelTextColor
//        _paintLabelTop.textSize = _attrs.labelTopTextSize
//
//        _paintLabelBottom = TextPaint()
//        _paintLabelBottom.isAntiAlias = true
//        _paintLabelBottom.style = Paint.Style.FILL
//        _paintLabelBottom.color = _attrs.labelTextColor
//        _paintLabelBottom.textSize = _attrs.labelBottomTextSize
//
//        _paintBubbleText = TextPaint()
//        _paintBubbleText.isAntiAlias = true
//        _paintBubbleText.style = Paint.Style.FILL
//        _paintBubbleText.color = _attrs.bubbleTextColor
//        _paintBubbleText.textSize = _attrs.bubbleTextSize
    }

    override fun initView() {
        initSlider()
        initRod()
        //setLabelTopTextSize(_attrs.labelTopTextSize)
        // setLabelBottomTextSize(_attrs.labelBottomTextSize)
        _layoutKSlider.postInvalidate()
    }

    private fun initRod() {
        val distance = if (!_attrs.rodIsInside) 0f else _slider.heightHalf - _attrs.rodRadius
        _rod = Rod(
            _layoutKSlider.paddingStart.toFloat() + _attrs.rodRadius + distance,
            _layoutKSlider.width - _layoutKSlider.paddingEnd.toFloat() - _attrs.rodRadius - distance,
            _slider.centerY,
            _attrs.rodIsInside,
            _attrs.rodRadius,
            _attrs.rodRadiusInside,
            _sliderListener
        )
    }

    private fun initSlider() {
        _slider = Slider(
            _layoutKSlider.width - _layoutKSlider.paddingStart.toFloat() - _layoutKSlider.paddingEnd - _attrs.sliderHeight,
            _attrs.sliderHeight,
            _layoutKSlider.paddingStart.toFloat() + _attrs.sliderHeight / 2f,
            if (_attrs.rodIsInside) 0f else _attrs.rodRadius - (_attrs.sliderHeight / 2f)
        )
        _slider.rodLeftColor = _attrs.sliderRodLeftColor
        _slider.rodRightColor = _attrs.sliderRodRightColor
    }

    fun onDraw(canvas: Canvas) {
        canvas.save()

        /*if (isRegions()) {
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
        _sliderVertexRadius = _sliderHeight / 2f*/

        //slider
        drawSlider(canvas)

        //rod
        drawRod(canvas)

        /*//labelTop
        kotlin.run { //texts top (values)
            if (_labelTopIsShow) {
                val textY = _sliderY - _labelSliderDistance
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
                        (rodCenterX - _sliderPaddingHorizontal) / 2f + _sliderPaddingHorizontal
                    } else {
                        _sliderPaddingHorizontal
                    }
                    drawIndicatorsTextAbove(
                        canvas, _layoutKSlider.width, formatLabelText(leftValue), _paintLabelTop,
                        textX,
                        textY,
                        Layout.Alignment.ALIGN_CENTER
                    )
                    if (_sliderRegionLabelColorIsFollowRegion) {
                        _paintLabelTop.color = _sliderRegionRightColor
                    }
                    textX = if (_sliderRegionLabelIsShow) {
                        rodCenterX + (_sliderWidth - rodCenterX - _sliderPaddingHorizontal) / 2f + _sliderPaddingHorizontal
                    } else {
                        _sliderPaddingHorizontal + _sliderWidth
                    }
                    drawIndicatorsTextAbove(
                        canvas,
                        _layoutKSlider.width,
                        formatLabelText(rightValue),
                        _paintLabelTop,
                        textX,
                        textY,
                        Layout.Alignment.ALIGN_CENTER
                    )
                } else {
                    drawIndicatorsTextAbove(
                        canvas,
                        _layoutKSlider.width,
                        formatLabelText(_rodMinVal),
                        _paintLabelTop,
                        0 + _sliderPaddingHorizontal,
                        textY,
                        Layout.Alignment.ALIGN_CENTER
                    )
                    for (step in _sections) {
                        drawIndicatorsTextAbove(
                            canvas,
                            _layoutKSlider.width,
                            formatLabelText(step.value),
                            _paintLabelTop,
                            step.startPos + _sliderPaddingHorizontal,
                            textY,
                            Layout.Alignment.ALIGN_CENTER
                        )
                    }
                    drawIndicatorsTextAbove(
                        canvas,
                        _layoutKSlider.width,
                        formatLabelText(_rodMaxVal),
                        _paintLabelTop,
                        canvas.width.toFloat(),
                        textY,
                        Layout.Alignment.ALIGN_CENTER
                    )
                }
            }
        }

        //sections
        kotlin.run { //steps + bottom text
            val bottomTextY = _sliderY + _sliderHeight + 15
            for (section in _sections) {
                if (_sectionCrossLineIsShow) {
                    canvas.drawLine(
                        section.startPos + _sliderPaddingHorizontal,
                        _sliderY - _sliderHeight / 4f,
                        section.startPos + _sliderPaddingHorizontal,
                        _sliderY + _sliderHeight + _sliderHeight / 4f,
                        _paintSectionCrossLine
                    )
                }
                if (_labelBottomIsShow) {
                    //drawMultilineText(canvas, maxText, canvas.getWidth() - settings.paintText.measureText(maxText), textY, settings.paintText, Layout.Alignment.ALIGN_OPPOSITE);
                    drawMultilineText(
                        canvas,
                        section.name,
                        section.startPos + _sliderPaddingHorizontal,
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
                        _layoutKSlider.width.toFloat(),
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

        //bubble
        kotlin.run {
            if (_bubbleIsShow) {
                var bubbleCenterX = rodCenterX
                _layoutKSliderBubble.setX(bubbleCenterX - _layoutKSliderBubble.getWidth() / 2f)
                _layoutKSliderBubble.setY(0f)
                if (bubbleCenterX > _layoutKSlider.width - _layoutKSliderBubble.getWidth() / 2f) {
                    bubbleCenterX = _layoutKSlider.width - _layoutKSliderBubble.getWidth() / 2f
                } else if (bubbleCenterX - _layoutKSliderBubble.getWidth() / 2f < 0) {
                    bubbleCenterX = _layoutKSliderBubble.getWidth() / 2f
                }
                val trangleCenterX: Float = (bubbleCenterX + rodCenterX) / 2f
                drawBubble(canvas, bubbleCenterX, trangleCenterX, 0f)
            }
        }*/

        canvas.restore()
    }

    private fun drawSlider(canvas: Canvas) {
        //val vertexLeft = _attrs.sliderPaddingHorizontal
        //val vertexRight = _layoutKSlider.width - _attrs.sliderPaddingHorizontal

        //rightSlider
        _paintSlider.color = _slider.rodRightColor
        canvas.drawCircle(
            _slider.leftX,
            _slider.centerY,
            _slider.heightHalf,
            _paintSlider
        )
        canvas.drawCircle(
            _slider.rightX,
            _slider.centerY,
            _slider.heightHalf,
            _paintSlider
        )
        canvas.drawRect(
            _slider.leftX,
            _slider.topY,
            _slider.rightX,
            _slider.bottomY,
            _paintSlider
        )

        //leftSlider
        _paintSlider.color = _slider.rodLeftColor
        if (_rod.currentVal > _rod.minVal) {
            val distance = if (!_rod.isInsideSlider) 0f else _slider.heightHalf - _rod.radius
            canvas.drawCircle(
                _slider.leftX,
                _slider.centerY,
                if (!_rod.isInsideSlider) _slider.heightHalf else _rod.radius,
                _paintSlider
            )
            canvas.drawRect(
                _slider.leftX,
                _slider.topY + distance,
                _rod.currentX,
                _slider.bottomY - distance,
                _paintSlider
            )
        }


        /*val rodCurrentX = _rodX + _sliderPaddingHorizontal

        if (isRegions()) {
            _paintSlider.color = _sliderRegionLeftColor
            canvas.drawCircle(_sliderPaddingHorizontal, _sliderCenterY, _sliderVertexRadius, _paintSlider)
            canvas.drawRect(_sliderPaddingHorizontal, _sliderY, rodCurrentX, _sliderY + _sliderHeight, _paintSlider)
        } else {
            var lastX = _sliderPaddingHorizontal
            var first = true
            for (step in _sections) {
                _paintSlider.color = step.colorBefore
                if (first) {
                    canvas.drawCircle(_sliderPaddingHorizontal, _sliderCenterY, _sliderVertexRadius, _paintSlider)
                }
                val x = step.startPos + _sliderPaddingHorizontal
                if (!_sectionColorBeforeRod) {
                    canvas.drawRect(lastX, _sliderY, x, _sliderY + _sliderHeight, _paintSlider)
                } else {
                    canvas.drawRect(
                        lastX,
                        _sliderY,
                        x.coerceAtMost(rodCurrentX),
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
                            step.startPos + _sliderPaddingHorizontal,
                            _sliderY,
                            rodCurrentX,
                            _sliderY + _sliderHeight,
                            _paintSlider
                        )
                        break
                    }
                }
            }
        }*/
    }

    private fun drawRod(canvas: Canvas) {
        val color = _attrs.rodColor
        canvas.drawCircle(_rod.currentX, _rod.centerY, _rod.radius, _paintRod)//外圆
        _paintRod.color = _attrs.rodColorInside
        canvas.drawCircle(_rod.currentX, _rod.centerY, _rod.radiusInside, _paintRod)//内圆
        _paintRod.color = color
    }

    /*private fun onBubbleClicked() {
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
                UtilKDisplay.dp2px(_bubbleTextSize).toFloat()
            )
            _bubbleText = _rodCurrentVal.toInt().toString()
            _editText.setText(_bubbleText)
            val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.width = _bubble.getWidth().toInt()
            params.height = _bubble.getBubbleHeight().toInt()
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
            _bubbleListener?.onEdit(_editText)
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
    }*/

    /*private fun updateBubbleWidth() {
        _bubble.setWidth(calculateBubbleTextWidth() + _bubblePaddingHorizontal * 2f)
        _bubble.setWidth(max(150f, _bubble.getWidth()))
    }*/

    /*private fun calculateBubbleTextWidth(): Float {
        var bubbleText = formatLabelText(_rodCurrentVal)
        if (_bubbleIsEditing) {
            bubbleText = _bubbleText
        }
        return _paintBubbleText.measureText(bubbleText)
    }*/

    /*private fun editBubbleEditPosition() {
        if (_bubbleIsEditing) {
            _editText.x = min(_bubble.getX(), _layoutKSlider.width.toFloat() - _editText.width)
            _editText.y = _bubble.getY()
            val params = _editText.layoutParams
            params.width = _bubble.getWidth().toInt()
            params.height = _bubble.getBubbleHeight().toInt()
            _editText.layoutParams = params
            _editText.animate().alpha(1f)
        }
    }*/

    /*fun closeEditText() {
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
    }*/

    /*private fun setCurrentValueNoUpdate(value: Float) {
        _rodCurrentVal = value
        _sliderListener?.onScroll(_layoutKSlider, _rodCurrentVal)
        updateValues()
    }*/

    /*private fun getActivityDecorView(): ViewGroup {
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
        return _sections.isEmpty()
    }*/

    /*private fun drawMultilineText(canvas: Canvas, text: String, x: Float, y: Float, paint: TextPaint, alignment: Layout.Alignment) {
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
*/
    /*fun updateValues() {
        if (_rodCurrentVal < _rodMinVal) _rodCurrentVal = _rodMinVal
        _sliderPaddingHorizontal = _sliderHeight
        _sliderWidth = _layoutKSlider.width - _sliderPaddingHorizontal * 2
        if (_bubbleIsShow) {
            updateBubbleWidth()
            _bubble.setHeight(
                _bubbleTextSize + _bubblePaddingVertical * 2f + _bubbleArrowHeight
            )
        } else {
            _bubble.setHeight(0f)
        }
        _sliderY = 0f
        if (_labelTopIsShow) {
            _sliderY += _labelSliderDistance * 2
            if (isRegions()) {
                var topTextHeight = 0f
                val tmpTextLeft = formatLabelText(0f)
                val tmpTextRight = formatLabelText(0f)
                topTextHeight = max(topTextHeight, UtilKCanvas.getMultiLineTextHeight(_paintLabelTop, tmpTextLeft))
                topTextHeight = max(topTextHeight, UtilKCanvas.getMultiLineTextHeight(_paintLabelTop, tmpTextRight))
                _sliderY += topTextHeight + 3
            } else {
                var topTextHeight = 0f
                for (step in _sections) {
                    topTextHeight = max(
                        topTextHeight,
                        UtilKCanvas.getMultiLineTextHeight(_paintLabelBottom, formatLabelText(step.value))
                    )
                }
                _sliderY += topTextHeight
            }
        } else {
            if (_labelTopIsShow) {
                _sliderY -= _bubbleArrowHeight / 1.5f
            }
        }
        _sliderY += _bubble.getHeight()
        _sliderCenterY = _sliderY + _sliderHeight / 2f
        if (_rodIsInside) {
            _rodRadius = _sliderHeight * 0.5f
        } else {
            _rodRadius = _sliderHeight * 0.9f
        }
        for (section in _sections) {
            val stoppoverPercent = section.value / (_rodMaxVal - _rodMinVal)
            section.startPos = stoppoverPercent * _sliderWidth
        }
        _rodX = (_rodCurrentVal - _rodMinVal) / (_rodMaxVal - _rodMinVal) * _sliderWidth
        _layoutHeightSpec = (_sliderCenterY + _rodRadius).toInt()
        var bottomTextHeight = 0f
        if (!TextUtils.isEmpty(_labelTextMax)) {
            bottomTextHeight = max(
                UtilKCanvas.getMultiLineTextHeight(_paintLabelBottom, _labelTextMax),
                UtilKCanvas.getMultiLineTextHeight(_paintLabelBottom, _labelTextMin)
            )
        }
        for (step in _sections) {
            bottomTextHeight = max(
                bottomTextHeight,
                UtilKCanvas.getMultiLineTextHeight(_paintLabelBottom, step.name)
            )
        }
        _layoutHeightSpec += bottomTextHeight.toInt()
        _layoutHeightSpec += 10 //padding bottom
    }*/

    /*fun update() {
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
    }*/

    /*private fun drawIndicatorsTextAbove(
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
        val textHeight = UtilKCanvas.getMultiLineTextHeight(paintText, text)
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
        UtilKCanvas.drawText(canvas, paintText, text, x, y, alignment)
    }*/

    /*private fun drawBubblePath(canvas: Canvas, triangleCenterX: Float, height: Float, width: Float) {
        val path = Path()
        val padding = 3
        val rect = Rect(padding, padding, width.toInt() - padding, (height - _bubbleArrowHeight).toInt() - padding)
        val roundRectHeight = (height - _bubbleArrowHeight) / 2
        path.moveTo(rect.left + roundRectHeight, rect.top.toFloat())
        path.lineTo(rect.right - roundRectHeight, rect.top.toFloat())
        path.quadTo(rect.right.toFloat(), rect.top.toFloat(), rect.right.toFloat(), rect.top + roundRectHeight)
        path.lineTo(rect.right.toFloat(), rect.bottom - roundRectHeight)
        path.quadTo(rect.right.toFloat(), rect.bottom.toFloat(), rect.right - roundRectHeight, rect.bottom.toFloat())
        path.lineTo(triangleCenterX + _bubbleArrowWidth / 2f, height - _bubbleArrowHeight - padding)
        path.lineTo(triangleCenterX, height - padding)
        path.lineTo(triangleCenterX - _bubbleArrowWidth / 2f, height - _bubbleArrowHeight - padding)
        path.lineTo(rect.left + roundRectHeight, rect.bottom.toFloat())
        path.quadTo(rect.left.toFloat(), rect.bottom.toFloat(), rect.left.toFloat(), rect.bottom - roundRectHeight)
        path.lineTo(rect.left.toFloat(), rect.top + roundRectHeight)
        path.quadTo(rect.left.toFloat(), rect.top.toFloat(), rect.left + roundRectHeight, rect.top.toFloat())
        path.close()
        canvas.drawPath(path, _paintBubble)
    }*/

    /*private fun formatRodText(region: Int, value: Float): String {
        return _textRodFormatter?.format(region, value) ?: value.toString()
    }*/

    /*private fun formatLabelText(value: Float): String {
        return _labelTextFormatter?.format(value) ?: value.toString()
    }*/

    fun onTouchEvent(event: MotionEvent): Boolean {
        //if (_bubbleIsEditing) return false
        /*val handledByDetector = _gestureDetector.onTouchEvent(event)
        if (!handledByDetector) {*/
        when (event.actionMasked) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                _scrollableParentView?.requestDisallowInterceptTouchEvent(false)
                _sliderListener?.onScrollEnd(_rod.currentVal)
                _rodIsScrolling = false
            }
            MotionEvent.ACTION_DOWN -> {
                _rodIsScrolling = if (event.y <= _slider.leftX || event.y >= _slider.rightX) {
                    return true
                } else {
                    _sliderListener?.onScrollStart()
                    true
                }
                _scrollableParentView?.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                if (_rodIsScrolling) {
                    _rod.currentX = event.x
                    _layoutKSlider.postInvalidate()
                    //update()
                }
            }
        }
        //}
        return true
    }

    /*private fun drawBubble(canvas: Canvas, centerX: Float, triangleCenterX: Float, y: Float) {
        var triangleCenterX = triangleCenterX
        val width = _bubble.getWidth()
        val height = _bubble.getHeight()
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
                val bubbleText = formatLabelText(_rodCurrentVal)
                UtilKCanvas.drawText(canvas, _paintBubbleText, bubbleText, _bubblePaddingHorizontal, (_bubblePaddingVertical - 3), Layout.Alignment.ALIGN_NORMAL)
            }
        }
        canvas.restore()
    }*/

    /*

    fun setBubbleListener(bubbleListener: IBubbleListener) {
        _bubbleListener = bubbleListener
    }

    *//*fun setEditListener(editListener: IEditListener) {
        _editListener = editListener
    }*//*

    fun setTextLabelFormatter(labelTextFormatter: ILabelTextFormatter) {
        _labelTextFormatter = labelTextFormatter
        update()
    }

    *//*fun setTextRodFormatter(textRodFormatter: ITextRodFormatter) {
        _textRodFormatter = textRodFormatter
        update()
    }*//*

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

    fun setSliderRegionLeftColor(sliderRegionLeftColor: Int) {
        this._sliderRegionLeftColor = sliderRegionLeftColor
        update()
    }

    fun setSliderRegionRightColor(sliderRegionRightColor: Int) {
        this._sliderRegionRightColor = sliderRegionRightColor
        update()
    }

    fun setLabelTopTextSize(textSize: Float) {
        _labelTopTextSize = textSize
        _paintLabelTop.textSize = textSize
        update()
    }

    fun setLabelBottomTextSize(textSize: Float) {
        _labelBottomTextSize = textSize
        _paintLabelBottom.textSize = textSize
        update()
    }*/
}