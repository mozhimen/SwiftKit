package com.mozhimen.uicorektest.dialogk.temps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.utilk.android.view.UtilKScreen
import com.mozhimen.basick.utilk.android.widget.applyValueIfNotEmpty
import com.mozhimen.uicorek.dialogk.bases.BaseDialogK
import com.mozhimen.uicorek.dialogk.bases.commons.IDialogKClickListener
import com.mozhimen.uicorektest.R
import kotlin.math.roundToInt

typealias IDialogKTipListener = I_Listener

class DialogKTip(context: Context, private val _txt: String, private var _onSure: IDialogKTipListener) : BaseDialogK<IDialogKClickListener>(context) {

    companion object {
        @JvmStatic
        fun create(context: Context, txt: String, onSure: IDialogKTipListener): DialogKTip {
            return DialogKTip(context, txt, onSure)
        }
    }

    private var _btnSure: Button? = null
    private var _btnCancel: Button? = null
    private var _txtView: TextView? = null

    init {
        setDialogCancelable(true)
        setDialogClickListener(object : IDialogKClickListener {
            override fun onClickPositive(view: View?) {
                _onSure.invoke()
                this@DialogKTip.dismiss()
            }

            override fun onClickNegative(view: View?) {
                this@DialogKTip.dismiss()
            }
        })
    }

    fun setTxt(txt: String) {
        _txtView?.applyValueIfNotEmpty(txt)
    }

    fun setSureListener(onSure: IDialogKTipListener) {
        _onSure = onSure
    }

    override fun onCreateView(inflater: LayoutInflater): View? {
        return inflater.inflate(R.layout.dialogk_tip, null)
    }

    override fun onViewCreated(view: View) {
        _txtView = view.findViewById(R.id.dialogk_tip_txt)
        _btnSure = view.findViewById(R.id.dialogk_tip_btn_sure)
        _btnCancel = view.findViewById(R.id.dialogk_tip_btn_cancel)

        _btnSure?.setOnClickListener { getDialogClickListener()?.onClickPositive(view) }
        _btnCancel?.setOnClickListener { getDialogClickListener()?.onClickNegative(view) }
        setTxt(_txt)
    }

    override fun onInitWindowWidth(): Int {
        return (UtilKScreen.getCurrentWidth() * 0.25f).roundToInt()
    }
}