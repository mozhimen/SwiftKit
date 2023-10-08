package com.mozhimen.uicorek.dialogk.bases

import android.content.Context
import androidx.annotation.StyleRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.elemk.androidx.lifecycle.commons.IDefaultLifecycleObserver
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.utilk.androidx.lifecycle.runOnMainThread
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.dialogk.bases.commons.IDialogKVBClickListener

/**
 * @ClassName BaseLifecycleDialogKVB
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/26 21:14
 * @Version 1.0
 */
@OptInApiCall_BindLifecycle
@OptInApiInit_ByLazy
abstract class BaseLifecycleDialogKVB<VB : ViewDataBinding, T : IDialogKVBClickListener<VB>>(context: Context, @StyleRes themeResId: Int = R.style.ThemeK_Dialog_Blur) :
    BaseDialogKVB<VB, T>(context, themeResId), IDefaultLifecycleObserver {

    override fun bindLifecycle(owner: LifecycleOwner) {
        owner.runOnMainThread {
            owner.lifecycle.removeObserver(this)
            owner.lifecycle.addObserver(this)
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        dismissSelf()
        owner.lifecycle.removeObserver(this)
    }

    private fun dismissSelf() {
        if (this.isShowing)
            dismiss()
    }
}