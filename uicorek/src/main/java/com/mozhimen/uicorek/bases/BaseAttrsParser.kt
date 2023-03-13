package com.mozhimen.uicorek.bases

import android.content.Context
import android.util.AttributeSet


/**
 * @ClassName BaseAttrsParser
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 17:29
 * @Version 1.0
 */
interface BaseAttrsParser<T> {
    fun parseAttrs(context: Context, attrs: AttributeSet?): T
}