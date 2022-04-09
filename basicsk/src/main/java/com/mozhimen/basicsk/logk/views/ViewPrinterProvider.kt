package com.mozhimen.basicsk.logk.views

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basicsk.utilk.dp2px

/**
 * @ClassName ViewPrinterProvider
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 18:24
 * @Version 1.0
 */
class ViewPrinterProvider(
    private val rootView: FrameLayout,
    private val recyclerView: RecyclerView
) {
    private var floatingView: View? = null
    private var logView: FrameLayout? = null

    private var isOpen = false

    companion object {
        private const val TAG_FLOATING_VIEW = "TAG_FLOATING_VIEW"
        private const val TAG_LOG_VIEW = "TAG_LOG_VIEW"
    }

    fun showFloatingView() {
        if (rootView.findViewWithTag<View?>(TAG_FLOATING_VIEW) != null) {
            return
        }
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.BOTTOM or Gravity.END
        val floatingView = getFloatingView()
        floatingView.tag = TAG_FLOATING_VIEW
        floatingView.setBackgroundColor(Color.BLACK)
        floatingView.alpha = 0.8f
        params.bottomMargin =100f.dp2px()
        rootView.addView(getFloatingView(), params)
    }

    private fun closeFloatingView() {
        rootView.removeView(getFloatingView())
    }

    @SuppressLint("SetTextI18n")
    private fun getFloatingView(): View {
        if (floatingView != null) {
            return floatingView!!
        }
        val textView = TextView(rootView.context)
        textView.setOnClickListener {
            if (!isOpen) {
                showLogView()
            }
        }
        textView.text = "LogK"
        return textView.also { floatingView = it }
    }

    /**
     * 展示LogView
     */
    private fun showLogView() {
        if (rootView.findViewWithTag<View?>(TAG_LOG_VIEW) != null) {
            return
        }
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            160f.dp2px()
        )
        params.gravity = Gravity.BOTTOM
        val logView = getLogView()
        logView.tag = TAG_LOG_VIEW
        rootView.addView(getLogView(), params)
        isOpen = true
    }

    /**
     * 关闭logView
     */
    private fun closeLogView() {
        isOpen = false
        rootView.removeView(getLogView())
    }

    private fun getLogView(): View {
        if (logView != null) {
            return logView!!
        }
        val logView = FrameLayout(rootView.context)
        logView.setBackgroundColor(Color.BLACK)
        logView.addView(recyclerView)
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.END
        val closeView = TextView(rootView.context)
        closeView.setOnClickListener { closeLogView() }
        closeView.text = "close"
        logView.addView(closeView, params)
        return logView.also { this.logView = it }
    }
}