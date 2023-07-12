package com.mozhimen.uicorek.toastk.builder

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.uicorek.R


class ToastKBuilder(view: View?) : BaseUtilK() {
    private val _toast: Toast by lazy { Toast(_context) }
    private var _toastRoot
    private var _toastTextView: TextView? = null

    init {
        _toast.setGravity(Gravity.BOTTOM, 0, 260) // 位置会比原来的Toast偏上一些
        view?.let { _toast.setView(it.also { _toastTextView = it }) }
    }

    constructor(activity: Activity) : this(activity.) {
        val root: View = activity.layoutInflater.inflate(R.layout.toastk_view, null)
        _toastTextView = root.findViewById<View>(R.id.toast_text) as TextView
    }

    fun setDuration(d: Int) {
        _toast.duration = d
    }

    fun setText(t: String?) {
        _toastTextView.text = t
    }

    fun show() {
        _toast.show()
    }

    companion object {
        const val LENGTH_SHORT = Toast.LENGTH_SHORT
        const val LENGTH_LONG = Toast.LENGTH_LONG
        fun makeText(context: Context, activity: Activity, text: String?, duration: Int): ToastCustom {
            val toastCustom = ToastCustom(context, activity)
            toastCustom.setText(text)
            toastCustom.setDuration(duration)
            return toastCustom
        }
    }
}