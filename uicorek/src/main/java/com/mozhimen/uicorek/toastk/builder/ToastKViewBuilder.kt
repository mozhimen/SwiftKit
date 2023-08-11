package com.mozhimen.uicorek.toastk.builder

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.mozhimen.basick.elemk.android.view.annors.AGravity
import com.mozhimen.basick.elemk.android.view.cons.CGravity
import com.mozhimen.basick.elemk.android.widget.annors.AToastDuration
import com.mozhimen.basick.elemk.android.widget.cons.CToast
import com.mozhimen.basick.lintk.optin.OptInApiDeprecated_Official_AfterV_30_11_R
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.uicorek.R

@OptInApiDeprecated_Official_AfterV_30_11_R
class ToastKViewBuilder : BaseUtilK() {
    private val _toast: Toast by lazy { Toast(_context) }
    private var _toastTextView: TextView

    init {
        val toastRootView: View = LayoutInflater.from(_context).inflate(R.layout.toastk_view, null)
        _toastTextView = toastRootView.findViewById<View>(R.id.toast_text) as TextView
        _toast.view = toastRootView
    }

    fun setGravity(@AGravity gravity: Int = CGravity.BOTTOM, xOffset: Int = 0, yOffset: Int = 260/*位置会比原来的Toast偏上一些*/): ToastKViewBuilder {
        _toast.setGravity(gravity, xOffset, yOffset)
        return this
    }

    fun setDuration(@AToastDuration duration: Int): ToastKViewBuilder {
        _toast.duration = duration
        return this
    }

    fun setText(str: String): ToastKViewBuilder {
        _toastTextView.text = str
        return this
    }

    fun show() {
        _toast.show()
    }

    fun hide() {
        _toast.cancel()
    }

    companion object {
        fun makeText(str: String, @AToastDuration duration: Int = CToast.LENGTH_SHORT): ToastKViewBuilder =
            ToastKViewBuilder().setText(str).setDuration(duration)
    }
}

