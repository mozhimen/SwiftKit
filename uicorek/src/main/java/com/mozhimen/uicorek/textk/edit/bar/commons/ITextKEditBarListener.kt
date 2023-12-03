package com.mozhimen.uicorek.textk.edit.bar.commons

import android.view.ViewGroup

/**
 * @ClassName ITextKEditBarListener
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/3 16:03
 * @Version 1.0
 */
interface ITextKEditBarListener {
    fun onCancel()
    fun onIllegal()
    fun onSubmit(content: String?)
    fun onAttached(rootView: ViewGroup?)
}
