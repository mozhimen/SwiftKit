package com.mozhimen.uicorek.toastk.builder

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.mozhimen.basick.lintk.annors.AToastDuration
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiDeprecated_Official_AfterV_30_11_R
import com.mozhimen.uicorek.R

@ALintKOptIn_ApiDeprecated_Official_AfterV_30_11_R
class ToastKBuilder(activity: Activity) {
    private val _toast: Toast by lazy { Toast(activity) }
    private var _toastTextView: TextView

    init {
        val toastRootView: View = activity.layoutInflater.inflate(R.layout.toastk_view, null)
        _toastTextView = toastRootView.findViewById<View>(R.id.toast_text) as TextView
        _toast.view = toastRootView
    }

    fun setGravity(gravity: Int = Gravity.BOTTOM, xOffset: Int = 0, yOffset: Int = 260/*位置会比原来的Toast偏上一些*/): ToastKBuilder {
        _toast.setGravity(gravity, xOffset, yOffset)
        return this
    }

    fun setDuration(@AToastDuration duration: Int): ToastKBuilder {
        _toast.duration = duration
        return this
    }

    fun setText(str: String): ToastKBuilder {
        _toastTextView.text = str
        return this
    }

    fun show() {
        _toast.show()
    }

    companion object {
        fun makeText(activity: Activity, str: String,@AToastDuration duration: Int = Toast.LENGTH_SHORT): ToastKBuilder =
            ToastKBuilder(activity).setText(str).setDuration(duration)
    }
}

