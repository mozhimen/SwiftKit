package com.mozhimen.swiftmk.widget.text

import android.content.Context
import android.content.res.TypedArray
import android.text.TextPaint
import android.util.AttributeSet
import com.mozhimen.swiftmk.R

/**
 * @ClassName UnderLineTextView
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/6/10 20:46
 * @Version 1.0
 */
open class UnderLineTextView : androidx.appcompat.widget.AppCompatTextView {
    //TextPaint对象，继承自Paint
    private lateinit var mTextPaint: TextPaint

    //是否加下划线
    private var mUnderLine = false

    constructor(context: Context) : super(context!!) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        //Load attributes 加载属性列表R.styleable.UnderLineTextView
        val typedArray: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.UnderLineTextView,
            defStyle,
            0
        )

        //获取自定义属性,默认是false
        mUnderLine = typedArray.getBoolean(R.styleable.UnderLineTextView_underline, false)
        typedArray.recycle()//回收TypedArray，以供以后的调用者重用

        mTextPaint = paint
        if (mUnderLine) {
            //设置下划线
            mTextPaint.isUnderlineText = true
        }
    }
}