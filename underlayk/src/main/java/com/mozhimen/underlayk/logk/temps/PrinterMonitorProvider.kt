package com.mozhimen.underlayk.logk.temps

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.*
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.extsk.showToastOnMain
import com.mozhimen.basick.stackk.StackK
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.utilk.UtilKGlobal
import com.mozhimen.basick.utilk.UtilKOverlay
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.basick.utilk.UtilKScreen
import com.mozhimen.underlayk.R
import com.mozhimen.underlayk.logk.LogK
import com.mozhimen.underlayk.logk.commons.IPrinter
import com.mozhimen.underlayk.logk.mos.LogKConfig

/**
 * @ClassName PrinterMonitorProvider
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/23 18:52
 * @Version 1.0
 */
class PrinterMonitorProvider(context: Context) : IPrinter {
    private companion object {
        private val TITLE_OPEN_PANEL = UtilKRes.getString(R.string.logk_view_provider_title_open)
        private val TITLE_CLOSE_PANEL = UtilKRes.getString(R.string.logk_view_provider_title_close)
    }

    private var _layoutParams = WindowManager.LayoutParams()
    private var _windowManager: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var _rootView = LayoutInflater.from(context).inflate(R.l ayout.logk_monitor_view, null, false) as FrameLayout
    private val _titleView = _rootView.findViewById<TextView>(R.id.logk_monitor_view_title)
    private val _recyclerView = _rootView.findViewById<RecyclerView>(R.id.logk_monitor_view_title)
    private var _isFold = false
        set(value) {
            _titleView!!.text = if (value) TITLE_OPEN_PANEL else TITLE_CLOSE_PANEL
            field = value
        }

    init {
        _layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        _layoutParams.format = PixelFormat.TRANSLUCENT
        _layoutParams.gravity = Gravity.END or Gravity.BOTTOM
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            _layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            _layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST
        }
    }

    override fun print(config: LogKConfig, level: Int, tag: String, printString: String) {

    }

    fun openMonitor(isFold: Boolean) {
        if (!UtilKOverlay.hasOverlayPermission()) {
            LogK.et(TAG, "PrinterMonitor play app has no overlay permission")
            "请打开悬浮窗权限".showToastOnMain()
            UtilKOverlay.startOverlaySettingActivity()
            return
        }
        _windowManager.addView(_rootView, getLayoutParams(isFold))
    }

    fun closeMonitor() {
        _windowManager.removeView(_rootView)
    }

    fun foldView() {
        if (_isFold) return
        _isFold = true
        _titleView.text = TITLE_OPEN_PANEL
        _recyclerView.visibility = View.GONE
        _rootView.layoutParams = getLayoutParams(true)
    }

    fun unfoldView() {
        if (!_isFold) return
        _isFold = false
        _titleView.text = TITLE_CLOSE_PANEL
        _recyclerView.visibility = View.VISIBLE
        _rootView.layoutParams = getLayoutParams(false)
    }

    private fun getLayoutParams(isFold: Boolean): WindowManager.LayoutParams {
        if (isFold) {
            _layoutParams.width = _titleView.width
            _layoutParams.height = _titleView.height
        } else {
            _layoutParams.width = UtilKScreen.getScreenWidth()
            _layoutParams.height = UtilKScreen.getScreenHeight() / 3
        }
        return _layoutParams
    }
}