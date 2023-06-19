package com.mozhimen.uicorek.dialogk.bases.commons

import android.view.View

/**
 * @ClassName IDialogKClickListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/24 22:28
 * @Version 1.0
 */
interface IDialogKClickListener {
    /**
     * 点击确定
     * @param view View?
     */
    fun onClickPositive(view: View?)

    /**
     * 点击取消
     * @param view View?
     */
    fun onClickNegative(view: View?)
}