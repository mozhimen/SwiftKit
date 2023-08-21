package com.mozhimen.uicorek.dialogk.bases.commons

import android.view.View
import androidx.databinding.ViewDataBinding
import com.mozhimen.uicorek.dialogk.bases.BaseDialogK
import com.mozhimen.uicorek.dialogk.bases.BaseDialogKVB

/**
 * @ClassName IDialogKClickListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/24 22:28
 * @Version 1.0
 */
interface IDialogKVBClickListener<VB : ViewDataBinding> : IDialogKClickListener {
    /**
     * 点击确定
     * @param view View?
     */
    fun onVBClickPositive(vb: VB, dialogK: BaseDialogKVB<VB, IDialogKVBClickListener<VB>>) {
        onClickPositive(vb.root, dialogK)
    }

    /**
     * 点击取消
     * @param view View?
     */
    fun onVBClickNegative(vb: VB, dialogK: BaseDialogKVB<VB, IDialogKVBClickListener<VB>>) {
        onClickNegative(vb.root, dialogK)
    }
}