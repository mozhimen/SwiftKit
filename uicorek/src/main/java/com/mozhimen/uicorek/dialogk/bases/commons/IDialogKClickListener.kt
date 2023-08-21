package com.mozhimen.uicorek.dialogk.bases.commons

import android.view.View
import com.mozhimen.uicorek.dialogk.bases.BaseDialogK

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
    fun <I : IDialogKClickListener> onClickPositive(view: View?, dialogK: BaseDialogK<I>) {}

    /**
     * 点击取消
     * @param view View?
     */
    fun <I : IDialogKClickListener> onClickNegative(view: View?, dialogK: BaseDialogK<I>) {}
}