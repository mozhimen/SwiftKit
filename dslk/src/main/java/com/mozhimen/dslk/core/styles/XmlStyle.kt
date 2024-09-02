package com.mozhimen.dslk.core.styles

/**
 * @ClassName XmlStyle
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/2
 * @Version 1.0
 */
import android.view.View
import androidx.annotation.AttrRes

@Suppress(
    "unused", // Type parameter is used externally for type inference. TODO: Move it on type parameter when supported in Kotlin.
)
@JvmInline
value class XmlStyle<in V : View>(@AttrRes val styleAttr: Int)