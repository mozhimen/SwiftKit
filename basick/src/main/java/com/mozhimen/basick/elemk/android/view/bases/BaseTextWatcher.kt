package com.mozhimen.basick.elemk.android.view.bases

import android.text.Editable
import android.text.TextWatcher

/**
 * @ClassName BaseTextChangedObserver
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/25 14:54
 * @Version 1.0
 */
open class BaseTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {}
}