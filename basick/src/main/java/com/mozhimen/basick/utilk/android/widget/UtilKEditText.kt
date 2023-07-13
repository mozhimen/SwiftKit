package com.mozhimen.basick.utilk.android.widget

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.EditText
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.elemk.android.view.bases.BaseTextWatcher
import com.mozhimen.basick.utilk.kotlin.toStringTrim
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

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

fun EditText.addTextChangeWatcher(onTextChanged: IA_Listener<String>/*(newText: String) -> Unit*/) {
    UtilKEditText.addTextChangeWatcher(this, onTextChanged)
}

// 构建输入框文字变化流
fun EditText.textChangeFlow(): Flow<Editable> = callbackFlow {

}


object UtilKEditText {

    @JvmStatic
    fun applyDebounceTextChangeListener(editText: EditText,scope:) {

    }

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
     * @param onTextChanged Function1<[@kotlin.ParameterName] String, Unit>
     */
    @JvmStatic
    fun addTextChangeWatcher(editText: EditText, onTextChanged: IA_Listener<String>/*(newTextStr: String) -> Unit*/) {
        editText.addTextChangedListener(object : BaseTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                onTextChanged(s.toString())
            }
        })
    }
}