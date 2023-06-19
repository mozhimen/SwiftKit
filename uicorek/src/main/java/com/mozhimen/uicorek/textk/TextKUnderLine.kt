package com.mozhimen.uicorek.textk

import android.content.Context
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.layoutk.commons.ILayoutK
import com.mozhimen.uicorek.viewk.commons.IViewK

/**
 * @ClassName TextKUnderLine
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/6/10 20:46
 * @Version 1.0
 */
class TextKUnderLine @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr), IViewK {

    private lateinit var _textPaint: TextPaint//TextPaint对象，继承自Paint
    private var _isUnderLine = false//是否加下划线

    init {
        initAttrs(attrs, defStyleAttr)
        initPaint()
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextKUnderLine, defStyleAttr, 0)//Load attributes 加载属性列表R.styleable.UnderLineTextView
        _isUnderLine =
            typedArray.getBoolean(R.styleable.TextKUnderLine_textKUnderLine_underline, false)        //获取自定义属性,默认是false
        typedArray.recycle()
    }

    override fun initPaint() {
        _textPaint = paint
        if (_isUnderLine) {
            //设置下划线
            _textPaint.isUnderlineText = true
        }
    }
}