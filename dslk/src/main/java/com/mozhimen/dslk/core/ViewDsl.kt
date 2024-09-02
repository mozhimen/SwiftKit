@file:OptIn(ExperimentalContracts::class)

package com.mozhimen.dslk.core

/**
 * @ClassName ViewDsl
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/2
 * @Version 1.0
 */
import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.core.view.get
import com.mozhimen.kotlin.utilk.android.content.UtilKContext
//import splitties.experimental.InternalSplittiesApi
//import splitties.views.inflate
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/** Called so to remind that function references (that are inlined) are recommended for [view]. */
typealias NewViewRef<V> = (Context) -> V

const val NO_THEME = 0

@Suppress("NOTHING_TO_INLINE")
inline fun Context.withTheme(theme: Int) = ContextThemeWrapper(this, theme)

fun Context.wrapCtxIfNeeded(theme: Int): Context {
    return if (theme == NO_THEME) this else withTheme(theme)
}

inline fun <V : View> Context.view(
    createView: NewViewRef<V>,
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return createView(wrapCtxIfNeeded(theme)).also { it.id = id }.apply(initView)
}

inline fun <V : View> View.view(
    createView: NewViewRef<V>,
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.view(createView, id, theme, initView)
}

inline fun <V : View> Ui.view(
    createView: NewViewRef<V>,
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.view(createView, id, theme, initView)
}

private const val VIEW_FACTORY = "splitties:views.dsl:viewfactory"

//@InternalSplittiesApi
val Context.viewFactory: ViewFactory
    @SuppressLint("WrongConstant")
    get() = try {
        getSystemService(VIEW_FACTORY) as ViewFactory? ?: ViewFactory.appInstance
    } catch (t: Throwable) {
        ViewFactory.appInstance
    }

//@InternalSplittiesApi
fun Context.withViewFactory(viewFactory: ViewFactory): Context = object : ContextWrapper(this) {
    override fun getSystemService(name: String): Any? = when (name) {
        VIEW_FACTORY -> viewFactory
        else -> super.getSystemService(name)
    }
}

/**
 * Most of the time, you should use a non [InternalSplittiesApi] overload of this function that
 * takes a function of type `(Context) -> V` where V is a `View` or one of its subtypes, where
 * using a reference to the constructor (e.g. `v(::MapView)`) is possible.
 *
 * This function is meant to be used when the type of View [V] is supported by an installed
 * [ViewFactory]. This inline function is usually hidden under other inline functions such as
 * the function [textView], which define the type.
 */
//@InternalSplittiesApi
inline fun <reified V : View> Context.view(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return viewFactory(V::class.java, wrapCtxIfNeeded(theme)).also {
        it.id = id
    }.apply(initView)
}

/**
 * Most of the time, you should use a non [InternalSplittiesApi] overload of this function that
 * takes a function of type `(Context) -> V` where V is a `View` or one of its subtypes, where
 * using a reference to the constructor (e.g. `v(::MapView)`) is possible.
 *
 * This function is meant to be used when the type of View [V] is supported by an installed
 * [ViewFactory]. This inline function is usually hidden under other inline functions such as
 * the function [textView], which define the type.
 */
//@InternalSplittiesApi
inline fun <reified V : View> View.view(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.view(id, theme, initView)
}

/**
 * Most of the time, you should use a non [InternalSplittiesApi] overload of this function that
 * takes a function of type `(Context) -> V` where V is a `View` or one of its subtypes, where
 * using a reference to the constructor (e.g. `v(::MapView)`) is possible.
 *
 * This function is meant to be used when the type of View [V] is supported by an installed
 * [ViewFactory]. This inline function is usually hidden under other inline functions such as
 * the function [textView], which define the type.
 */
//@InternalSplittiesApi
inline fun <reified V : View> Ui.view(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.view(id, theme, initView)
}

@PublishedApi
internal const val XML_DEFINED_ID = -1

inline fun <reified V : View> Context.inflate(
    @LayoutRes layoutResId: Int,
    @IdRes id: Int = XML_DEFINED_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return( UtilKContext.getLayoutInflater(wrapCtxIfNeeded(theme)).inflate(layoutResId,null,false) as V).also { inflatedView ->
        if (id != XML_DEFINED_ID) inflatedView.id = id
    }.apply(initView)
}

inline fun <reified V : View> View.inflate(
    @LayoutRes layoutResId: Int,
    @IdRes id: Int = XML_DEFINED_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.inflate(layoutResId, id, theme, initView)
}

inline fun <reified V : View> Ui.inflate(
    @LayoutRes layoutResId: Int,
    @IdRes id: Int = XML_DEFINED_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.inflate(layoutResId, id, theme, initView)
}

@Suppress("NOTHING_TO_INLINE")
inline fun <V : View> ViewGroup.add(
    view: V,
    lp: ViewGroup.LayoutParams
): V = view.also { addView(it, lp) }

inline val ViewGroup.lastChild: View get() = this[childCount - 1]

@Suppress("CONFLICTING_OVERLOADS")
@JvmName("addBefore")
fun <V : View> ViewGroup.add(
    view: V,
    lp: ViewGroup.LayoutParams,
    beforeChild: View
): V {
    val index = indexOfChild(beforeChild).also {
        check(it != -1) { "Value passed in beforeChild is not a child of the ViewGroup!" }
    }
    return view.also { addView(it, index, lp) }
}

@Suppress("CONFLICTING_OVERLOADS")
@JvmName("addAfter")
fun <V : View> ViewGroup.add(
    view: V,
    lp: ViewGroup.LayoutParams,
    afterChild: View
): V {
    val index = indexOfChild(afterChild).also {
        check(it != -1) { "Value passed in beforeChild is not a child of the ViewGroup!" }
    } + 1
    return view.also { addView(it, index, lp) }
}