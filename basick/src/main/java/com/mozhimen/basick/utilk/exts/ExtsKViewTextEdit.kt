package com.mozhimen.basick.utilk.exts

import android.widget.EditText
import com.mozhimen.basick.utilk.view.UtilKViewTextEdit

/**
 * @ClassName ExtsViewTextEdit
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 15:00
 * @Version 1.0
 */
fun EditText.setInputMaxLength(inputMaxLength: Int) {
    UtilKViewTextEdit.setInputMaxLength(this, inputMaxLength)
}