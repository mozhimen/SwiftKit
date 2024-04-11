package com.mozhimen.xmlk.commons

import android.content.Context
import android.util.AttributeSet


/**
 * @ClassName BaseAttrsParser
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
interface IAttrsParser<T> {
    fun parseAttrs(context: Context, attrs: AttributeSet?): T
}