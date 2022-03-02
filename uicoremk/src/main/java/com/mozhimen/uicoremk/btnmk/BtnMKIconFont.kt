package com.mozhimen.uicoremk.btnmk

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

/**
 * @ClassName BtnMKIconFont
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/2 16:28
 * @Version 1.0
 */
class BtnMKIconFont @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatButton(context, attrs, defStyleAttr) {
    init {
        val typeface = Typeface.createFromAsset(context.assets, "fonts/iconfont.ttf")
        setTypeface(typeface)
    }
}