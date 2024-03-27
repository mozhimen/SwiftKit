package com.mozhimen.basick.utilk.android.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.text.TextUtils
import android.view.Gravity
import android.widget.TextView
import androidx.annotation.IntRange
import androidx.core.content.ContextCompat
import com.mozhimen.basick.elemk.android.view.annors.AGravity
import com.mozhimen.basick.elemk.android.view.cons.CGravity
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.android.text.UtilKInputFilter
import com.mozhimen.basick.utilk.kotlin.obj2stringTrim
import com.mozhimen.basick.utilk.kotlin.whether

/**
 * @ClassName UtilKViewText
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 0:27
 * @Version 1.0
 */

val TextView.value: String get() = UtilKTextViewWrapper.getValue(this)

fun TextView.getValueIfNotEmpty(invoke: IA_Listener<String>/*(value: String) -> Unit*/) {
    UtilKTextViewWrapper.getValueIfNotEmpty(this, invoke)
}

//////////////////////////////////////////////////////////////////////////////////////

fun TextView.applySingleLine() {
    UtilKTextViewWrapper.applySingleLine(this)
}

fun TextView.applyTypeface(@IntRange(from = 0, to = 3) style: Int = Typeface.NORMAL) {
    UtilKTextViewWrapper.applyTypeface(this, style)
}

fun TextView.applyTypeface_ofBold() {
    UtilKTextViewWrapper.applyTypeface_ofBold(this)
}

fun TextView.applyTypeface_ofNormal() {
    UtilKTextViewWrapper.applyTypeface_ofNormal(this)
}

fun TextView.applyTypeface_ofDefault() {
    UtilKTextViewWrapper.applyTypeface_ofDefault(this)
}

fun TextView.applyTypeface_ofAsset(assetFontPathWithName: String) {
    UtilKTextViewWrapper.applyTypeface_ofAsset(this, assetFontPathWithName)
}

fun TextView.applyValueIfNotEmpty(str: String?) {
    UtilKTextViewWrapper.applyValueIfNotEmpty(this, str)
}

fun TextView.applyFilter_ofLength(inputLength: Int) {
    UtilKTextViewWrapper.applyFilter_ofLength(this, inputLength)
}

fun TextView.applyCompoundDrawable(drawable: Drawable, @AGravity gravity: Int, boundsSize: Int = 0) {
    UtilKTextViewWrapper.applyCompoundDrawable(this, drawable, gravity, boundsSize)
}

//////////////////////////////////////////////////////////////////////////////////////

object UtilKTextViewWrapper {
    @JvmStatic
    fun get(
        context: Context,
        singleLine: Boolean = false,
        ems: Int = 10,
        truncateAt: TextUtils.TruncateAt? = TextUtils.TruncateAt.END,
        gravity: Int? = Gravity.CENTER,
        intResColor: Int? = android.R.color.black,
        textSize: Float? = 16f
    ): TextView {
        val textView = TextView(context)
        singleLine.whether { textView.setSingleLine() }//设置显示为1行
        if (ems > 0) textView.setEms(ems)//设置最多显示多少个字
        truncateAt?.let { textView.ellipsize = it }//设置省略号在尾部
        gravity?.let { textView.gravity = it }
        intResColor?.let { textView.setTextColor(ContextCompat.getColor(context, it)) }
        textSize?.let { textView.textSize = it }
        return textView
    }

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getValue(textView: TextView): String =
        textView.text.obj2stringTrim()

    @JvmStatic
    fun getValueIfNotEmpty(textView: TextView, invoke: IA_Listener<String>/*(value: String) -> Unit*/) {
        val value = getValue(textView)
        if (value.isNotEmpty()) invoke.invoke(value)
    }

    @JvmStatic
    @SuppressLint("SetTextI18n")
    fun applyValue_ofLog(textView: TextView, log: String) {
        textView.text = textView.text.toString() + log + "\n"
        //let text view to move to the last line.
        val offset = textView.lineCount * textView.lineHeight
        if (offset > textView.height) {
            textView.scrollTo(0, offset - textView.height)
        }
    }

    @JvmStatic
    fun applyValueIfNotEmpty(textView: TextView, str: String?): Boolean =
        if (!str.isNullOrEmpty()) {
            textView.text = str;true
        } else false

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyCompoundDrawable(textView: TextView, drawable: Drawable, @AGravity gravity: Int, boundsSize: Int = 0) {
        if (boundsSize <= 0) {
            when (gravity) {
                CGravity.START, CGravity.LEFT ->
                    textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)

                CGravity.TOP ->
                    textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)

                CGravity.END, CGravity.RIGHT ->
                    textView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)

                else ->
                    textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable)
            }
        } else {
            drawable.setBounds(0, 0, boundsSize, boundsSize)
            when (gravity) {
                CGravity.START, CGravity.LEFT ->
                    textView.setCompoundDrawables(drawable, null, null, null)

                CGravity.TOP ->
                    textView.setCompoundDrawables(null, drawable, null, null)

                CGravity.END, CGravity.RIGHT ->
                    textView.setCompoundDrawables(null, null, drawable, null)

                else ->
                    textView.setCompoundDrawables(null, null, null, drawable)
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applySingleLine(textView: TextView) {
        textView.setSingleLine()
        textView.maxLines = 1
    }

    ////////////////////////////////////////////////////////////////////////////
    //typeface

    //设置字体的细或粗
    @JvmStatic
    fun applyTypeface(textView: TextView, @IntRange(from = 0, to = 3) style: Int = Typeface.NORMAL) {
        UtilKTextView.setTypeface(textView, Typeface.defaultFromStyle(style))
    }

    @JvmStatic
    fun applyTypeface_ofBold(textView: TextView) {
        applyTypeface(textView, Typeface.BOLD)
    }

    @JvmStatic
    fun applyTypeface_ofNormal(textView: TextView) {
        applyTypeface(textView, Typeface.NORMAL)
    }

    @JvmStatic
    fun applyTypeface_ofDefault(textView: TextView) {
        UtilKTextView.setTypeface(textView, Typeface.DEFAULT)
    }

    /**
     * 设置字体
     * assetFontPathWithName "fonts/iconfont.ttf"
     */
    @JvmStatic
    fun applyTypeface_ofAsset(textView: TextView, assetFontPathWithName: String) {
        UtilKTextView.setTypeface(textView, Typeface.createFromAsset(UtilKContext.getAssets(textView.context), assetFontPathWithName))
    }

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyFilter_ofLength(textView: TextView, inputLength: Int) {
        if (inputLength > 0) {
            UtilKTextView.setFilters(textView, arrayOf(UtilKInputFilter.getLengthFilter(inputLength)))
        }
    }
}