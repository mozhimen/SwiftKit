package com.mozhimen.uicorek.commons

import android.content.Context
import android.util.AttributeSet


/**
 * @ClassName BaseAttrsParser
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 17:29
 * @Version 1.0
 */
interface IAttrsParser<T> {
    fun parseAttrs(context: Context, attrs: AttributeSet?): T
}