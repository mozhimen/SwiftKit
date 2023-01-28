package com.mozhimen.uicorek.dialogk.commons

import android.view.View

/**
 * @ClassName IDialogKClickListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/24 22:28
 * @Version 1.0
 */
interface IDialogKClickListener {
    fun onClickPositive(view: View?)
    fun onClickNegative(view: View?)
}