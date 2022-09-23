package com.mozhimen.underlayk.logk.temps

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.LinearLayout
import com.mozhimen.basick.stackk.StackK
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.underlayk.R
import com.mozhimen.underlayk.logk.commons.IPrinter
import com.mozhimen.underlayk.logk.mos.LogKConfig
import com.mozhimen.basick.utilk.UtilKGlobal
import com.mozhimen.basick.utilk.UtilKOverlay
import com.mozhimen.underlayk.logk.LogK

/**
 * @ClassName PrinterMonitor
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/22 15:52
 * @Version 1.0
 */
class PrinterMonitor : IPrinter {

    private val _context = UtilKGlobal.instance.getApp()!!
    private var _layoutParams = WindowManager.LayoutParams()
    private var _rootView =
        LayoutInflater.from(_context).inflate(R.layout.logk_monitor_view, null, false) as LinearLayout
    private var _windowManager: WindowManager = _context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var _isShow = false

    init {
        initParams()
        initView()
    }

    fun toggleMonitor() {
        if (_isShow) {
            hide()
        } else {
            show()
        }
    }

    override fun print(config: LogKConfig, level: Int, tag: String, printString: String) {

    }

    private fun initParams() {
        _layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        _layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        _layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        _layoutParams.format = PixelFormat.TRANSLUCENT
        _layoutParams.gravity = Gravity.END or Gravity.BOTTOM
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            _layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            _layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST
        }
    }

    private fun initView() {
        StackK.addFrontBackListener(object : IStackKListener {
            override fun onChanged(isFront: Boolean) {
                if (isFront) {
                    LogK.dt(TAG, "PrinterMonitor onChanged log start")
                    show()
                } else {
                    LogK.wt(TAG, "PrinterMonitor onChanged log stop")
                    hide()
                }
            }
        })
    }

    private fun show() {
        if (!UtilKOverlay.hasOverlayPermission()) {
            UtilKOverlay.startOverlaySettingActivity()
            LogK.et(TAG, "PrinterMonitor play app has no overlay permission")
            return
        }

        if (!_isShow) {
            _isShow = true
            _windowManager.addView(_rootView, _layoutParams)
        }
    }

    private fun hide() {
        if (_isShow) {
            _isShow = false
            _windowManager.removeView(_rootView)
        }
    }
}