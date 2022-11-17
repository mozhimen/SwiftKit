package com.mozhimen.uicorek.textk

import android.content.Context
import android.content.res.TypedArray
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.mozhimen.uicorek.viewk.commons.IViewK
import com.mozhimen.uicorek.R

/**
 * @ClassName TextKUnderLine
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/6/10 20:46
 * @Version 1.0
 */
class TextKUnderLine @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) :
    AppCompatTextView(context, attrs, defStyleAttr), IViewK {
    //TextPaint对象，继承自Paint
    private lateinit var _textPaint: TextPaint

    //是否加下划线
    private var _underLine = false

    init {
        initAttrs(attrs, defStyleAttr)
        initPaint()
    }

    override fun initFlag() {

    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        //Load attributes 加载属性列表R.styleable.UnderLineTextView
        val typedArray: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.TextKUnderLine,
            defStyleAttr,
            0
        )

        //获取自定义属性,默认是false
        _underLine = typedArray.getBoolean(R.styleable.TextKUnderLine_textKUnderLine_underline, false)
        typedArray.recycle()//回收TypedArray，以供以后的调用者重用
    }

    override fun initPaint() {
        _textPaint = paint
        if (_underLine) {
            //设置下划线
            _textPaint.isUnderlineText = true
        }
    }

    override fun initData() {
    }

    override fun initView() {
    }
}