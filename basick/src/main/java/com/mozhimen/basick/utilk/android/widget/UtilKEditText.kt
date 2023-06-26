package com.mozhimen.basick.utilk.android.widget

import android.text.Editable
import android.text.InputFilter
import android.widget.EditText
import com.mozhimen.basick.elemk.view.commons.BaseTextChangedObserver
import com.mozhimen.basick.utilk.kotlin.toStringTrim

/**
 * @ClassName UtilKViewTextEdit
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 0:28
 * @Version 1.0
 */
val EditText.value: String
    get() = value()

fun EditText.value(): String =
    UtilKEditText.value(this)

fun EditText.setInputMaxLength(inputMaxLength: Int) {
    UtilKEditText.setInputMaxLength(this, inputMaxLength)
}

fun EditText.setOnTextChangedObserver(onTextChangedInvoke: (newText: String) -> Unit) {
    UtilKEditText.setOnTextChangedObserver(this, onTextChangedInvoke)
}

object UtilKEditText {
    /**
     * 最多可输入的字符数
     * @param editText EditText
     * @param inputMaxLength Int
     */
    @JvmStatic
    fun setInputMaxLength(editText: EditText, inputMaxLength: Int) {
        if (inputMaxLength > 0) editText.filters = arrayOf(InputFilter.LengthFilter(inputMaxLength))
    }

    /**
     * 获取text
     * @param editText EditText
     * @return String
     */
    @JvmStatic
    fun value(editText: EditText): String =
        editText.text.toStringTrim()

    /**
     * 变化观察
     * @param editText EditText
     * @param onTextChangedInvoke Function1<[@kotlin.ParameterName] String, Unit>
     */
    @JvmStatic
    fun setOnTextChangedObserver(editText: EditText, onTextChangedInvoke: (newTextStr: String) -> Unit) {
        editText.addTextChangedListener(object : BaseTextChangedObserver() {
            override fun afterTextChanged(s: Editable) {
                onTextChangedInvoke(s.toString())
            }
        })
    }
}