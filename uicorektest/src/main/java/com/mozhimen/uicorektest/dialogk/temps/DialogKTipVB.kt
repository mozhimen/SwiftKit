package com.mozhimen.uicorektest.dialogk.temps

import android.content.Context
import android.view.View
import com.mozhimen.basick.utilk.android.view.UtilKScreen
import com.mozhimen.basick.utilk.android.widget.applyValueIfNotEmpty
import com.mozhimen.uicorek.dialogk.bases.BaseDialogKVB
import com.mozhimen.uicorek.dialogk.bases.commons.IBaseDialogK
import com.mozhimen.uicorek.dialogk.bases.commons.IDialogKClickListener
import com.mozhimen.uicorek.dialogk.bases.commons.IDialogKVBClickListener
import com.mozhimen.uicorektest.databinding.DialogkTipBinding
import kotlin.math.roundToInt

/**
 * @ClassName DialogKTipVB
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/2 18:54
 * @Version 1.0
 */
class DialogKTipVB(context: Context, private val _txt: String, private var _onSure: IDialogKTipListener) :
        BaseDialogKVB<DialogkTipBinding, IDialogKVBClickListener<DialogkTipBinding>>(context) {

    companion object {
        @JvmStatic
        fun create(context: Context, txt: String, onSure: IDialogKTipListener): DialogKTipVB {
            return DialogKTipVB(context, txt, onSure)
        }
    }

    init {
        setDialogCancelable(true)
        setDialogClickListener(object : IDialogKVBClickListener<DialogkTipBinding> {

            override fun onVBClickPositive(vb: DialogkTipBinding, dialogK: BaseDialogKVB<DialogkTipBinding, IDialogKVBClickListener<DialogkTipBinding>>) {
                _onSure.invoke()
                this@DialogKTipVB.dismiss()
            }

            override fun onVBClickNegative(vb: DialogkTipBinding, dialogK: BaseDialogKVB<DialogkTipBinding, IDialogKVBClickListener<DialogkTipBinding>>) {
                this@DialogKTipVB.dismiss()
            }
        })
    }

    fun setTxt(txt: String) {
        vb.dialogkTipTxt.applyValueIfNotEmpty(txt)
    }

    fun setOnSureListener(onSure: IDialogKTipListener) {
        _onSure = onSure
    }

    override fun onViewCreated(view: View) {
        vb.dialogkTipBtnSure.setOnClickListener { getDialogClickListener()?.onClickPositive(view, this) }
        vb.dialogkTipBtnCancel.setOnClickListener { getDialogClickListener()?.onClickNegative(view, this) }
        setTxt(_txt)
    }

    override fun onInitWindowWidth(): Int {
        return (UtilKScreen.getCurrentWidth() * 0.25f).roundToInt()
    }
}