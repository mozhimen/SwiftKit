package com.mozhimen.uicorek.textk.edit

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.mozhimen.basick.elemk.android.view.cons.CInputMethodManager
import com.mozhimen.basick.elemk.commons.IAB_Listener
import com.mozhimen.basick.utilk.android.util.dp2px
import com.mozhimen.basick.utilk.android.view.UtilKInputMethodManager
import com.mozhimen.basick.utilk.android.widget.applyLengthFilter
import com.mozhimen.basick.utilk.kotlin.normalize
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.commons.IUicoreK
import kotlin.math.max

/**
 * @ClassName TextKEditRect
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/11/4 16:29
 * @Version 1.0
 */
class TextKEditRect @JvmOverloads constructor(context: Context, attrs: AttributeSet?=null,defStyleAttr:Int = 0) :
    AppCompatEditText(context, attrs,defStyleAttr),IUicoreK {

    private var _rectNum: Int = 6// 输入的最大长度
    private var _rectWidth: Int = 30f.dp2px().toInt()// 边框宽度
    private var _rectHeight: Int = 30f.dp2px().toInt()// 边框高度
    private var _rectGap: Int = 10f.dp2px().toInt()// 边框之间的距离
    private var _rectStateDrawable: Drawable? = null // 方框的背景

    private var _cursorIsShow = true
    private var _cursorFlashIntervalMillis = 800
    private var _cursorWidth: Int = 2f.dp2px.toInt()// 光标宽度
    private var _cursorHeight: Int = 15.dp2px.toInt()// 光标高度
    private var _cursorStateDrawable: Drawable? = null// 光标的背景

    /////////////////////////////////////////////////////////////////////////////

    private var _onInputFinishListener: IAB_Listener<CharSequence?,Int>? = null// 输入结束监听 onTextFinish(text: CharSequence?, length: Int)

    /////////////////////////////////////////////////////////////////////////////

    private var _textColor = 0
    private var _cursorLastFocusedTimeMillis = System.currentTimeMillis()// 记录上次光标获取焦点时间
    private var _cursorIsFocused = true// 是否光标获取焦点
    private var _rectRect = Rect()
    private var _cursorRect = Rect()
    private var _textRect = Rect()

    /////////////////////////////////////////////////////////////////////////////

    init {
        initAttrs(attrs)
        initView()
    }

    /////////////////////////////////////////////////////////////////////////////

    //region # override func
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun initAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.TextKEditRect)

        _rectNum =
            typedArray.getInt(R.styleable.TextKEditRect_textKEditRect_rectNum,_rectNum)
        _rectGap =
            typedArray.getDimensionPixelOffset(R.styleable.TextKEditRect_textKEditRect_rectGap,_rectGap)
        _rectWidth =
            typedArray.getDimensionPixelOffset(R.styleable.TextKEditRect_textKEditRect_rectWidth,_rectWidth)
        _rectHeight =
            typedArray.getDimensionPixelOffset(R.styleable.TextKEditRect_textKEditRect_rectHeight,_rectHeight)
        _rectStateDrawable =
            typedArray.getDrawable(R.styleable.TextKEditRect_textKEditRect_rectStateDrawable)?: context.getDrawable(R.drawable.textk_edit_rect_bg)

        _cursorIsShow =
            typedArray.getBoolean(R.styleable.TextKEditRect_textKEditRect_cursorIsShow,_cursorIsShow)
        _cursorFlashIntervalMillis =
            typedArray.getInt(R.styleable.TextKEditRect_textKEditRect_cursorFlashIntervalMillis,_cursorFlashIntervalMillis)
        _cursorWidth =
            typedArray.getDimensionPixelOffset(R.styleable.TextKEditRect_textKEditRect_cursorWidth,_cursorWidth)
        _cursorHeight =
            typedArray.getDimensionPixelOffset(R.styleable.TextKEditRect_textKEditRect_cursorHeight,_cursorHeight)
        _cursorStateDrawable =
            typedArray.getDrawable(R.styleable.TextKEditRect_textKEditRect_cursorStateDrawable)?:context.getDrawable(R.drawable.textk_edit_rect_cursor)
        typedArray.recycle()
    }

    override fun initView() {
        isLongClickable = false
        // 去掉背景颜色
//        setBackgroundColor(Color.TRANSPARENT)
        // 不显示光标
        isCursorVisible = false
        isFocusable = true
        isFocusableInTouchMode = true
        setRectNum(_rectNum)
    }
    override fun onTextContextMenuItem(id: Int): Boolean =
        false

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var width = measuredWidth
        var height = measuredHeight

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        // 判断高度是否小于推荐高度
        if (height < _rectHeight) {
            height = _rectHeight
        }

        // 判断宽度是否小于推荐宽度
        val recommendWidth = _rectWidth * _rectNum + _rectGap * (_rectNum - 1)
        if (width < recommendWidth) {
            width = recommendWidth
        }
        val newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, widthMode)
        val newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, heightMode)
        setMeasuredDimension(newWidthMeasureSpec, newHeightMeasureSpec)
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)

        // 当前文本长度
        val textLength = editableText.length
        if (textLength == _rectNum) {
            clearFocus()
            if (windowToken!=null){
                UtilKInputMethodManager.hideSoftInputFromWindow(context,windowToken,CInputMethodManager.HIDE_NOT_ALWAYS)
            }
            if (_onInputFinishListener != null) {
                _onInputFinishListener!!.invoke(editableText.toString(), _rectNum)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        _textColor = currentTextColor
        setTextColor(Color.TRANSPARENT)
        super.onDraw(canvas)
        setTextColor(_textColor)
        // 重绘背景颜色
        drawRect(canvas)
        drawCursor(canvas)
        // 重绘文本
        drawText(canvas)
    }
    //endregion

    /////////////////////////////////////////////////////////////////////////////

    //region # public func
    /**
     * 设置输入完成监听
     */
    fun setOnInputFinishListener(onInputFinishListener: IAB_Listener<CharSequence?,Int>) {
        _onInputFinishListener = onInputFinishListener
    }

    /**
     * 设置最大长度
     */
    fun setRectNum(max: Int) {
        _rectNum = max
        applyLengthFilter(max)
    }

    fun setRectWidth(rectWidth: Int) {
        _rectWidth = rectWidth
    }

    fun setRectHeight(rectHeight: Int) {
        _rectHeight = rectHeight
    }

    fun setRectGap(rectGap: Int) {
        _rectGap = rectGap
    }

    fun setRectStateDrawable(rectStateDrawable: Drawable) {
        _rectStateDrawable = rectStateDrawable
    }

    fun isShowCursor(isShowCursor:Boolean){
        _cursorIsShow = isShowCursor
    }

    fun setCursorFlashIntervalMillis(cursorFlashIntervalMillis:Int){
        _cursorFlashIntervalMillis = cursorFlashIntervalMillis.normalize(100,10000)
    }

    fun setCursorWidth(cursorWidth: Int) {
        _cursorWidth = cursorWidth
    }

    fun setCursorHeight(cursorHeight: Int) {
        _cursorHeight = cursorHeight
    }

    fun setCursorStateDrawable(cursorStateDrawable: Drawable) {
        _cursorStateDrawable = cursorStateDrawable
    }
    //endregion

    /////////////////////////////////////////////////////////////////////////////

    private fun drawRect(canvas: Canvas) {
        if (_rectStateDrawable != null) {
            // 绘制方框背景
            _rectRect.left = 0
            _rectRect.top = 0
            _rectRect.right = _rectWidth
            _rectRect.bottom = _rectHeight
            
            val count = canvas.saveCount
            canvas.save()
            for (i in 0 until _rectNum) {
                _rectStateDrawable!!.bounds = _rectRect
                _rectStateDrawable!!.state = intArrayOf(android.R.attr.state_enabled)
                _rectStateDrawable!!.draw(canvas)
                val dx = (_rectRect.right + _rectGap).toFloat()
                // 移动画布
                canvas.save()
                canvas.translate(dx, 0f)
            }
            canvas.restoreToCount(count)
            canvas.translate(0f, 0f)

            // 绘制激活状态
            // 当前激活的索引
            val activatedIndex = max(0, editableText.length)
            if (activatedIndex < _rectNum) {
                _rectRect.left = _rectWidth * activatedIndex + _rectGap * activatedIndex
                _rectRect.right = _rectRect.left + _rectWidth
                _rectStateDrawable!!.state = intArrayOf(android.R.attr.state_focused)
                _rectStateDrawable!!.bounds = _rectRect
                _rectStateDrawable!!.draw(canvas)
            }
        }
    }

    private fun drawCursor(canvas: Canvas) {
        if (_cursorStateDrawable != null) {
            // 绘制光标
            _cursorRect.left = (_rectWidth - _cursorWidth) / 2
            _cursorRect.top = (_rectHeight - _cursorHeight) / 2
            _cursorRect.right = _cursorRect.left + _cursorWidth
            _cursorRect.bottom = _cursorRect.top + _cursorHeight
            val count = canvas.saveCount
            canvas.save()
            for (i in 0 until _rectNum) {
                _cursorStateDrawable!!.bounds = _cursorRect
                _cursorStateDrawable!!.state = intArrayOf(android.R.attr.state_enabled)
                _cursorStateDrawable!!.draw(canvas)
                val dx = (_cursorRect.right + _rectGap).toFloat()
                // 移动画布
                canvas.save()
                canvas.translate(dx, 0f)
            }
            canvas.restoreToCount(count)
            canvas.translate(0f, 0f)

            // 绘制激活状态
            // 当前激活的索引
            val activatedIndex = max(0, editableText.length)
            if (activatedIndex < _rectNum) {
                _cursorRect.left = _rectWidth * activatedIndex + _rectGap * activatedIndex + (_rectWidth - _cursorWidth) / 2
                _cursorRect.right = _cursorRect.left + _cursorWidth
                val state = intArrayOf(if (isFocusable && isFocusableInTouchMode && _cursorIsFocused) android.R.attr.state_focused else android.R.attr.state_enabled)
                _cursorStateDrawable!!.state = state
                _cursorStateDrawable!!.bounds = _cursorRect
                _cursorStateDrawable!!.draw(canvas)
                if (System.currentTimeMillis() - _cursorLastFocusedTimeMillis >= _cursorFlashIntervalMillis) {
                    _cursorIsFocused = !_cursorIsFocused
                    _cursorLastFocusedTimeMillis = System.currentTimeMillis()
                }
            }
        }
    }

    private fun drawText(canvas: Canvas) {
        val count = canvas.saveCount
        canvas.translate(0f, 0f)
        val length = editableText.length
        for (i in 0 until length) {
            val text = editableText[i].toString()
            val textPaint = paint
            textPaint.color = _textColor
            textPaint.getTextBounds(text, 0, 1, _textRect)// 获取文本大小
            val x = _rectWidth / 2 + (_rectWidth + _rectGap) * i - _textRect.centerX()// 计算(x,y) 坐标
            val y = (canvas.height + _textRect.height()) / 2
            canvas.drawText(text, x.toFloat(), y.toFloat(), textPaint)
        }
        canvas.restoreToCount(count)
    }
}
