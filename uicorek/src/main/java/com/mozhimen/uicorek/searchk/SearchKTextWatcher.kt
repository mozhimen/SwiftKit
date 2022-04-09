package com.mozhimen.uicorek.searchk

import android.text.Editable
import android.text.TextWatcher

/**
 * @ClassName SearchKTextWatcher
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/7 14:18
 * @Version 1.0
 */
open class SearchKTextWatcher : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(p0: Editable?) {}
}