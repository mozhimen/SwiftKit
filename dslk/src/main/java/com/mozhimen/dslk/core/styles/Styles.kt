package com.mozhimen.dslk.core.styles

/**
 * @ClassName Styles
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/2
 * @Version 1.0
 */
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import com.mozhimen.dslk.core.NO_THEME
import com.mozhimen.dslk.core.getThemeAttrStyledView
import com.mozhimen.dslk.core.viewFactory
import com.mozhimen.dslk.core.wrapCtxIfNeeded

typealias NewViewWithStyleAttrRef<V> = (Context, AttributeSet?, Int) -> V

inline operator fun <reified V : View> XmlStyle<V>.invoke(
    ctx: Context,
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V = ctx.viewFactory.getThemeAttrStyledView<V>(ctx.wrapCtxIfNeeded(theme), null, styleAttr).also {
    it.id = id
}.apply(initView)

inline fun <V : View> Context.styledView(
    newViewRef: NewViewWithStyleAttrRef<V>,
    @AttrRes styleAttr: Int,
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V = newViewRef(this.wrapCtxIfNeeded(theme), null, styleAttr).also {
    it.id = id
}.apply(initView)