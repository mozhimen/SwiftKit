package com.mozhimen.underlayk.logk.temps.printer

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.UtilKPermission
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.basick.utilk.UtilKScreen
import com.mozhimen.basick.utilk.bar.UtilKDialog
import com.mozhimen.basick.utilk.exts.showToastOnMain
import com.mozhimen.uicorek.recyclerk.RecyclerKAdapter
import com.mozhimen.underlayk.R
import com.mozhimen.underlayk.logk.LogK
import com.mozhimen.underlayk.logk.commons.ILogKPrinter
import com.mozhimen.underlayk.logk.bases.BaseLogKConfig
import com.mozhimen.underlayk.logk.mos.MLogK

/**
 * @ClassName PrinterMonitorProvider
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/23 18:52
 * @Version 1.0
 */
@AManifestKRequire(CPermission.SYSTEM_ALERT_WINDOW)
class LogKPrinterMonitorProvider(private val _context: Context) : ILogKPrinter {
    private companion object {
        private const val TAG_LOGK_MONITOR_VIEW = "TAG_LOGK_CONTAINER_VIEW"
        private val TITLE_OPEN_PANEL = UtilKRes.getString(R.string.logk_view_provider_title_open)
        private val TITLE_CLOSE_PANEL = UtilKRes.getString(R.string.logk_view_provider_title_close)
    }

    private val _layoutParams: WindowManager.LayoutParams = WindowManager.LayoutParams()
    private var _rootView: FrameLayout? = null
        get() {
            if (field != null) return field
            val frameLayout = LayoutInflater.from(_context).inflate(R.layout.logk_monitor_view, null, false) as FrameLayout
            frameLayout.tag = TAG_LOGK_MONITOR_VIEW
            return frameLayout.also { field = it }
        }
    private var _windowManager: WindowManager = _context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private var _adapter: RecyclerKAdapter = RecyclerKAdapter(_context)

    private var _recyclerView: RecyclerView? = null
        get() {
            if (field != null) return field
            val recyclerView = _rootView!!.findViewById<RecyclerView>(R.id.logk_monitor_view_msg)
            recyclerView.layoutManager = LinearLayoutManager(_context)
            recyclerView.adapter = _adapter
            return recyclerView.also { field = it }
        }

    private var _titleView: TextView? = null
        get() {
            if (field != null) return field
            val textView = _rootView!!.findViewById<TextView>(R.id.logk_monitor_view_title)
            textView.setOnClickListener { if (_isFold) unfoldMonitor() else foldMonitor() }
            return textView.also { field = it }
        }

    private var _isFold = false
        set(value) {
            _titleView!!.text = if (value) TITLE_OPEN_PANEL else TITLE_CLOSE_PANEL
            field = value
        }

    private var _isOpen = false

    init {
        _layoutParams.flags = (WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE) or WindowManager.LayoutParams.FLAG_FULLSCREEN
        _layoutParams.format = PixelFormat.TRANSLUCENT
        _layoutParams.gravity = Gravity.END or Gravity.BOTTOM
        if (Build.VERSION.SDK_INT >= CVersionCode.V_26_8_O) {
            _layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            _layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST
        }
    }

    override fun print(config: BaseLogKConfig, level: Int, tag: String, printString: String) {
        _adapter.addItem(LogKPrinterItem(MLogK(System.currentTimeMillis(), level, tag, printString)), true)
        _recyclerView!!.smoothScrollToPosition(_adapter.itemCount - 1)
    }

    fun isOpen(): Boolean =
        _isOpen

    fun openMonitor(isFold: Boolean) {
        if (!UtilKDialog.isOverlayPermissionEnable(_context)) {
            LogK.et(TAG, "PrinterMonitor play app has no overlay permission")
            "请打开悬浮窗权限".showToastOnMain()
            UtilKPermission.openSettingOverlay(_context)
            return
        }
        try {
            _windowManager.addView(_rootView, getWindowLayoutParams(isFold))
            if (isFold) foldMonitor() else unfoldMonitor()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        _isOpen = true
    }

    /**
     * 关闭Monitor
     */
    fun closeMonitor() {
        try {
            if (_rootView!!.findViewWithTag<View?>(TAG_LOGK_MONITOR_VIEW) == null) return
            _windowManager.removeView(_rootView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        _isOpen = false
    }

    @Throws(Exception::class)
    fun foldMonitor() {
        if (_isFold) return
        _isFold = true
        _titleView!!.text = TITLE_OPEN_PANEL
        _recyclerView!!.visibility = View.GONE
        _rootView!!.layoutParams = getLayoutParams(true)
        _windowManager.updateViewLayout(_rootView, getWindowLayoutParams(true))
    }

    @Throws(Exception::class)
    fun unfoldMonitor() {
        if (!_isFold) return
        _isFold = false
        _titleView!!.text = TITLE_CLOSE_PANEL
        _recyclerView!!.visibility = View.VISIBLE
        _rootView!!.layoutParams = getLayoutParams(false)
        _windowManager.updateViewLayout(_rootView, getWindowLayoutParams(false))
    }

    private fun getLayoutParams(isFold: Boolean): FrameLayout.LayoutParams {
        val layoutParams = (_rootView!!.layoutParams as? FrameLayout.LayoutParams?) ?: FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        if (isFold) {
            if (_titleView!!.width == 0 || _titleView!!.height == 0) {
                _isFold = true
                return layoutParams
            }
            layoutParams.width = _titleView!!.width
            layoutParams.height = _titleView!!.height
        } else {
            layoutParams.width = UtilKScreen.getRealScreenWidth()
            layoutParams.height = UtilKScreen.getRealScreenHeight() / 3
        }
        return layoutParams
    }

    private fun getWindowLayoutParams(isFold: Boolean): WindowManager.LayoutParams {
        _layoutParams.width = if (isFold) WindowManager.LayoutParams.WRAP_CONTENT else WindowManager.LayoutParams.MATCH_PARENT
        _layoutParams.height = if (isFold) WindowManager.LayoutParams.WRAP_CONTENT else (UtilKScreen.getRealScreenHeight() / 3)
        return _layoutParams
    }
}