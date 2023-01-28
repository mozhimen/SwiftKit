package com.mozhimen.uicorek.btnk

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.mozhimen.basick.utilk.exts.font
import com.mozhimen.uicorek.R

/**
 * @ClassName BtnKIconFont
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/2 16:28
 * @Version 1.0
 */
class BtnKIconFont @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatButton(context, attrs, defStyleAttr) {

    private var _fontPath = "icons/iconfont.ttf"

    init {
        initAttrs(attrs)
        initView()
    }

    fun initAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BtnKIconFont)
        _fontPath = typedArray.getString(R.styleable.BtnKIconFont_btnKIconFont_fontPath) ?: _fontPath
        typedArray.recycle()
    }

    fun initView() {
        font(_fontPath)
    }
}