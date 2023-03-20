package com.mozhimen.basick.utilk.view

import android.text.InputFilter
import android.widget.EditText

/**
 * @ClassName UtilKViewTextEdit
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 0:28
 * @Version 1.0
 */
object UtilKEditText {
    /**
     * 最多可输入的字符数
     * @param editText EditText
     * @param inputMaxLength Int
     */
    @JvmStatic
    fun setInputMaxLength(
        editText: EditText,
        inputMaxLength: Int
    ) {
        if (inputMaxLength > 0) editText.filters = arrayOf(InputFilter.LengthFilter(inputMaxLength))
    }
}