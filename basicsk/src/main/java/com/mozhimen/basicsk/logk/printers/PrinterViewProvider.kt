package com.mozhimen.basicsk.logk.printers

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basicsk.R
import com.mozhimen.basicsk.extsk.dp2px
import com.mozhimen.basicsk.utilk.UtilKDisplay
import com.mozhimen.basicsk.utilk.UtilKRes
import com.mozhimen.basicsk.utilk.UtilKScreen

/**
 * @ClassName ViewPrinterProvider
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 18:24
 * @Version 1.0
 */
class PrinterViewProvider(
    private val _rootView: FrameLayout,
    private val _recyclerView: RecyclerView
) {
    private var _floatingView: View? = null
    private var _logView: FrameLayout? = null
    private var _isShowing = false

    companion object {
        private const val TAG_LOGK_FLOATING_VIEW = "TAG_LOGK_FLOATING_VIEW"
        private const val TAG_LOGK_VIEW = "TAG_LOGK_VIEW"
    }

    /**
     * 显示悬浮面板
     */
    fun showFloatingView() {
        if (_rootView.findViewWithTag<View?>(TAG_LOGK_FLOATING_VIEW) != null) {
            return
        }
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.BOTTOM or Gravity.END
        val floatingView = getFloatingView()
        floatingView.tag = TAG_LOGK_FLOATING_VIEW
        floatingView.setBackgroundColor(Color.BLACK)
        floatingView.alpha = 0.8f
        _rootView.addView(getFloatingView(), params)
    }

    private fun closeFloatingView() {
        _rootView.removeView(getFloatingView())
    }

    private fun getFloatingView(): View {
        if (_floatingView != null) {
            return _floatingView!!
        }
        val textView = TextView(_rootView.context)
        textView.setOnClickListener {
            if (!_isShowing) {
                showLogKView()
            }
        }
        textView.text = UtilKRes.getString(R.string.logk_view_provider_title)
        textView.setTextColor(UtilKRes.getColor(android.R.color.white))
        return textView.also { _floatingView = it }
    }

    /**
     * 展示LogView
     */
    private fun showLogKView() {
        if (_rootView.findViewWithTag<View?>(TAG_LOGK_VIEW) != null) {
            return
        }
        val layoutParams =
            FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UtilKScreen.getScreenHeight() / 3)
        layoutParams.gravity = Gravity.BOTTOM
        val logView = getLogView()
        logView.tag = TAG_LOGK_VIEW
        _rootView.addView(getLogView(), layoutParams)
        _isShowing = true
    }

    /**
     * 关闭logView
     */
    private fun closeLogView() {
        _isShowing = false
        _rootView.removeView(getLogView())
    }

    private fun getLogView(): View {
        if (_logView != null) {
            return _logView!!
        }

        val logView = FrameLayout(_rootView.context)
        logView.setBackgroundColor(Color.BLACK)
        logView.addView(_recyclerView)
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.END

        val closeView = TextView(_rootView.context)
        closeView.setOnClickListener { closeLogView() }
        closeView.text = UtilKRes.getString(R.string.logk_view_provider_close)
        closeView.setTextColor(UtilKRes.getColor(android.R.color.white))
        logView.addView(closeView, params)
        return logView.also { this._logView = it }
    }
}