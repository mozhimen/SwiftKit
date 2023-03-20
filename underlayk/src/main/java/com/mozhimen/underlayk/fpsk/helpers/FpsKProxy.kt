package com.mozhimen.underlayk.fpsk.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.elemk.delegate.VarNullableInitDelegate
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.exts.decimal2Str
import com.mozhimen.basick.stackk.StackK
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.content.UtilKPermission
import com.mozhimen.basick.utilk.content.activity.UtilKLaunchActivity
import com.mozhimen.basick.utilk.view.bar.UtilKDialog
import com.mozhimen.basick.utilk.view.window.UtilKWindowManager
import com.mozhimen.underlayk.R
import com.mozhimen.underlayk.fpsk.commons.IFpsK
import com.mozhimen.underlayk.fpsk.commons.IFpsKListener
import com.mozhimen.underlayk.logk.LogK

/**
 * @ClassName FpsViewer
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/22 16:04
 * @Version 1.0
 */
@AManifestKRequire(CPermission.SYSTEM_ALERT_WINDOW)
class FpsKProxy : IFpsK {
    private val TAG = "FpsKView>>>>>"

    private val _context = UtilKApplication.instance.get()
    private val _params by lazy {
        WindowManager.LayoutParams().apply {
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            format = PixelFormat.TRANSLUCENT
            gravity = Gravity.END or Gravity.BOTTOM
            type = if (Build.VERSION.SDK_INT >= CVersionCode.V_26_8_O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY else WindowManager.LayoutParams.TYPE_TOAST
        }
    }
    private var _isShow = false
    private var _internalListener = object : IFpsKListener {
        @SuppressLint("SetTextI18n")
        override fun onFrame(fps: Double) {
            _fpsView?.text = "${fps.decimal2Str()} fps"
        }
    }

    //    private var _fpsView: TextView = LayoutInflater.from(_context).inflate(R.layout.fpsk_view, null, false) as TextView
    private var _fpsView: TextView? by VarNullableInitDelegate { LayoutInflater.from(_context).inflate(R.layout.fpsk_view, null, false) as TextView }
    private val _frameMonitor = FrameMonitor()
    private var _windowManager: WindowManager = UtilKWindowManager.get()

    init {
        StackK.addFrontBackListener(object : IStackKListener {
            override fun onChanged(isFront: Boolean) {
                if (isFront) {
                    LogK.dt(TAG, "FpsKView onChanged fpsk start")
                    start()
                } else {
                    LogK.wt(TAG, "FpsKView onChanged fpsk stop")
                    stop()
                }
            }
        })
    }

    override fun isShow(): Boolean {
        return _isShow
    }

    override fun toggle() {
        if (_isShow) stop() else start()
    }

    override fun addListener(listener: IFpsKListener) {
        _frameMonitor.addListener(listener)
    }

    override fun removeListeners() {
        _frameMonitor.removeListeners()
    }

    private fun stop() {
        if (!_isShow) return
        _isShow = false
        _frameMonitor.stop()
        _frameMonitor.removeListeners()
        _windowManager.removeView(_fpsView)
        _fpsView = null
    }

    private fun start() {
        if (_isShow) return
        if (!UtilKPermission.isOverlayPermissionEnable(_context)) {
            UtilKLaunchActivity.startManageOverlay(_context)
            LogK.et(TAG, "FpsKView play app has no overlay permission")
            return
        }

        _isShow = true
        _windowManager.addView(_fpsView, _params)
        _frameMonitor.addListener(_internalListener)
        _frameMonitor.start()
    }
}