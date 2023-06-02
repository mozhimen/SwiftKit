package com.mozhimen.uicorek.dialogk.bases

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StyleRes
import androidx.databinding.ViewDataBinding
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.jetpack.databinding.UtilKViewDataBinding
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.dialogk.bases.commons.IDialogKClickListener

/**
 * @ClassName BaseDialogKVB
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/2 17:12
 * @Version 1.0
 */
@AManifestKRequire(CPermission.SYSTEM_ALERT_WINDOW)
abstract class BaseDialogKVB<VB : ViewDataBinding, T : IDialogKClickListener>(context: Context, @StyleRes themeResId: Int = R.style.DialogK_Theme_Blur) : BaseDialogK<T>(context, themeResId) {

    private var _vb: VB? = null
    protected val vb get() = _vb!!

    override fun onCreateView(inflater: LayoutInflater): View? {
        _vb = UtilKViewDataBinding.get<VB>(this::class.java, inflater, 0).apply {
            lifecycleOwner = this@BaseDialogKVB
        }
        return vb.root
    }
}