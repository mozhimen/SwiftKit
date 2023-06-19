package com.mozhimen.uicorek.layoutk.search.commons

import android.text.Editable
import android.text.TextWatcher

/**
 * @ClassName SearchTextWatcher
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/7 14:18
 * @Version 1.0
 */
open class SearchTextWatcher : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(p0: Editable?) {}
}