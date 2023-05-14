package com.mozhimen.basick.utilk.exts

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.mozhimen.basick.utilk.view.UtilKEditText

/**
 * @ClassName ExtsViewTextEdit
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 15:00
 * @Version 1.0
 */
fun EditText.setInputMaxLength(inputMaxLength: Int) {
    UtilKEditText.setInputMaxLength(this, inputMaxLength)
}

val EditText.value: String
    get() = UtilKEditText.getValue(this)

fun EditText.setOnTextChangeObserver(onTextChangedAction: (newText: String) -> Unit) {
    UtilKEditText.setOnTextChangeObserver(this, onTextChangedAction)
}