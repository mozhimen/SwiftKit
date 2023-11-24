package com.mozhimen.underlayk.logk.temps.printer

import android.annotation.SuppressLint
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.elemk.android.view.cons.CWinMgr
import com.mozhimen.basick.elemk.kotlin.properties.VarProperty_SetVaryNonnull
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.app.UtilKPermission
import com.mozhimen.basick.utilk.android.app.UtilKLaunchActivity
import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.os.UtilKLooper
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.android.view.UtilKScreen
import com.mozhimen.basick.utilk.android.view.UtilKWindowManager
import com.mozhimen.basick.utilk.android.widget.showToastOnMain
import com.mozhimen.uicorek.adapterk.item.AdapterKItemRecycler
import com.mozhimen.underlayk.logk.LogK
import com.mozhimen.underlayk.logk.bases.BaseLogKConfig
import com.mozhimen.underlayk.logk.bases.BaseLogKRecord
import com.mozhimen.underlayk.logk.commons.ILogKPrinter
import com.mozhimen.underlayk.logk.commons.ILogKPrinterMonitor
import com.mozhimen.underlayk.logk.cons.CLogKCons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @ClassName PrinterMonitorProvider
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/23 18:52
 * @Version 1.0
 */
@AManifestKRequire(CPermission.SYSTEM_ALERT_WINDOW)
class LogKPrinterMonitorDelegate : ILogKPrinter, ILogKPrinterMonitor, BaseUtilK(), LifecycleOwner {
    private val TITLE_OPEN_PANEL by lazy { UtilKRes.getString(com.mozhimen.underlayk.R.string.logk_view_provider_title_open) }
    private val TITLE_CLOSE_PANEL by lazy { UtilKRes.getString(com.mozhimen.underlayk.R.string.logk_view_provider_title_close) }

    private val _layoutParams: WindowManager.LayoutParams by lazy { WindowManager.LayoutParams() }
    private var _rootView: FrameLayout? = null
        @SuppressLint("InflateParams")
        get() {
            if (field != null) return field
            val frameLayout = LayoutInflater.from(_context).inflate(com.mozhimen.underlayk.R.layout.logk_monitor_view, null, false) as FrameLayout
            frameLayout.tag = CLogKCons.TAG_LOGK_MONITOR_VIEW
            return frameLayout.also { field = it }
        }
    private val _windowManager: WindowManager by lazy { UtilKWindowManager.get(_context) }
    private val _adapterKItemRecycler by lazy { AdapterKItemRecycler() }

    private var _recyclerView: RecyclerView? = null
        get() {
            if (field != null) return field
            val recyclerView = _rootView!!.findViewById<RecyclerView>(com.mozhimen.underlayk.R.id.logk_monitor_view_msg)
            recyclerView.layoutManager = LinearLayoutManager(_context)
            recyclerView.adapter = _adapterKItemRecycler
            return recyclerView.also { field = it }
        }

    private var _titleView: TextView? = null
        get() {
            if (field != null) return field
            val textView = _rootView!!.findViewById<TextView>(com.mozhimen.underlayk.R.id.logk_monitor_view_title)
            textView.setOnClickListener { if (_isFold) unfold() else fold() }
            return textView.also { field = it }
        }

    private var _isFold = false
        set(value) {
            _titleView!!.text = if (value) TITLE_OPEN_PANEL else TITLE_CLOSE_PANEL
            field = value
        }

    private var _isOpen by VarProperty_SetVaryNonnull(false) { _, value ->
        if (value) lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START) else lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        true
    }

    private var _lifecycleRegistry: LifecycleRegistry? = null
    protected val lifecycleRegistry: LifecycleRegistry
        get() = _lifecycleRegistry ?: LifecycleRegistry(this).also {
            _lifecycleRegistry = it
        }
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    init {
        _layoutParams.flags = (CWinMgr.Lpf.NOT_TOUCH_MODAL or CWinMgr.Lpf.NOT_FOCUSABLE) or CWinMgr.Lpf.FULLSCREEN
        _layoutParams.format = PixelFormat.TRANSLUCENT
        _layoutParams.gravity = Gravity.END or Gravity.BOTTOM
        _layoutParams.type = if (UtilKBuildVersion.isAfterV_26_8_O()) CWinMgr.Lpt.APPLICATION_OVERLAY else CWinMgr.Lpt.TOAST
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun getLifecycle(): Lifecycle =
        lifecycleRegistry

    override fun print(config: BaseLogKConfig, priority: Int, tag: String, msg: String) {
        if (_isOpen) {
            if (UtilKLooper.isMainThread()) {
                printInView(priority, tag, msg)
            } else {
                lifecycleScope.launch(Dispatchers.Main) {
                    printInView(priority, tag, msg)
                }
            }
        }
    }

    override fun isOpen(): Boolean =
        _isOpen

    override fun toggle() {
        toggle(true)
    }

    override fun toggle(isFold: Boolean) {
        if (isOpen())
            close()
        else
            open(isFold)
    }

    override fun open() {
        open(true)
    }

    override fun open(isFold: Boolean) {
        if (_isOpen) return
        if (!UtilKPermission.hasOverlay()) {
            LogK.etk(TAG, "PrinterMonitor play app has no overlay permission")
            "请打开悬浮窗权限".showToastOnMain()
            UtilKLaunchActivity.startManageOverlay(_context)
            return
        }
        try {
            _windowManager.addView(_rootView, getWindowLayoutParams(isFold))
            if (isFold)
                fold()
            else unfold()
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        _isOpen = true
    }

    override fun close() {
        if (!_isOpen) return
        try {
            if (_rootView!!.findViewWithTag<View?>(CLogKCons.TAG_LOGK_MONITOR_VIEW) == null) return
            _windowManager.removeView(_rootView)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        _isOpen = false
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    private fun printInView(level: Int, tag: String, msg: String) {
        _adapterKItemRecycler.addItem(LogKPrinterItem(BaseLogKRecord(System.currentTimeMillis(), level, tag, msg)), true)
        _recyclerView!!.smoothScrollToPosition(_adapterKItemRecycler.itemCount - 1)
    }

    @Throws(Exception::class)
    private fun fold() {
        if (_isFold) return
        _isFold = true
        _titleView!!.text = TITLE_OPEN_PANEL
        _recyclerView!!.visibility = View.GONE
        _rootView!!.layoutParams = getLayoutParams(true)
        _windowManager.updateViewLayout(_rootView, getWindowLayoutParams(true))
    }

    @Throws(Exception::class)
    private fun unfold() {
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
            layoutParams.width = UtilKScreen.getRealWidth()
            layoutParams.height = UtilKScreen.getRealHeight() / 3
        }
        return layoutParams
    }

    private fun getWindowLayoutParams(isFold: Boolean): WindowManager.LayoutParams {
        _layoutParams.width = if (isFold) CWinMgr.Lp.WRAP_CONTENT else CWinMgr.Lp.MATCH_PARENT
        _layoutParams.height = if (isFold) CWinMgr.Lp.WRAP_CONTENT else (UtilKScreen.getRealHeight() / 3)
        return _layoutParams
    }


}