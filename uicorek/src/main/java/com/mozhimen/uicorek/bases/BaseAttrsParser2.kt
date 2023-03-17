package com.mozhimen.uicorek.bases

import android.content.Context
import android.util.AttributeSet


/**
 * @ClassName BaseAttrsParser2
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/17 18:27
 * @Version 1.0
 */
interface BaseAttrsParser2<T> {
    fun parseAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int): T
}