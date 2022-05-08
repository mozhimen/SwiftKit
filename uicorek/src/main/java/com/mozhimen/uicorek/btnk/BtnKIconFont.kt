package com.mozhimen.uicorek.btnk

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.mozhimen.basicsk.extsk.font

/**
 * @ClassName BtnKIconFont
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/2 16:28
 * @Version 1.0
 */
class BtnKIconFont @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatButton(context, attrs, defStyleAttr) {
    init {
        this.font()
    }
}