package com.mozhimen.underlayk.fpsk.helpers

import android.annotation.SuppressLint
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.elemk.android.view.cons.CWinMgr
import com.mozhimen.basick.elemk.kotlin.properties.VarProperty_GetNonnull
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.stackk.cb.StackKCb
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.app.UtilKPermission
import com.mozhimen.basick.utilk.android.app.UtilKLaunchActivity
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.kotlin.doubleDecimal2str
import com.mozhimen.basick.utilk.android.view.UtilKWindowManager
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
@OptInApiInit_InApplication
@AManifestKRequire(CPermission.SYSTEM_ALERT_WINDOW)
class FpsKDelegate : IFpsK, BaseUtilK() {
    private val _params by lazy {
        WindowManager.LayoutParams().apply {
            width = CWinMgr.Lp.WRAP_CONTENT
            height = CWinMgr.Lp.WRAP_CONTENT
            flags = CWinMgr.Lpf.NOT_FOCUSABLE or CWinMgr.Lpf.NOT_TOUCHABLE or CWinMgr.Lpf.NOT_TOUCH_MODAL
            format = PixelFormat.TRANSLUCENT
            gravity = Gravity.END or Gravity.BOTTOM
            type = if (UtilKBuildVersion.isAfterV_26_8_O()) CWinMgr.Lpt.APPLICATION_OVERLAY else CWinMgr.Lpt.TOAST
        }
    }
    private var _isOpen = false
    private var _internalListener = object : IFpsKListener {
        @SuppressLint("SetTextI18n")
        override fun onFrame(fps: Double) {
            _fpsView?.text = "${fps.doubleDecimal2str()} fps"
        }
    }

    //    private var _fpsView: TextView = LayoutInflater.from(_context).inflate(R.layout.fpsk_view, null, false) as TextView
    private var _fpsView: TextView? by VarProperty_GetNonnull { LayoutInflater.from(_context).inflate(R.layout.fpsk_view, null, false) as TextView }
    private val _frameMonitor by lazy { FrameMonitor() }
    private val _windowManager: WindowManager by lazy { UtilKWindowManager.get(_context) }

    init {
        StackKCb.instance.addFrontBackListener(object : IStackKListener {
            override fun onChanged(isFront: Boolean) {
//                if (isFront) {
//                    LogK.dt(TAG, "FpsKView onChanged fpsk start")
//                    start()
//                } else {
//                    LogK.wt(TAG, "FpsKView onChanged fpsk stop")
//                    stop()
//                }
                if (!isFront && isOpen()) {
                    LogK.wtk(TAG, "FpsKView onChanged fpsk stop")
                    stop()
                }
            }
        })
    }

    override fun isOpen(): Boolean = _isOpen

    override fun toggle() {
        if (_isOpen) stop() else start()
    }

    override fun addListener(listener: IFpsKListener) {
        _frameMonitor.addListener(listener)
    }

    override fun removeListeners() {
        _frameMonitor.removeListeners()
    }

    private fun stop() {
        if (!_isOpen) return
        _isOpen = false
        _frameMonitor.stop()
        _frameMonitor.removeListeners()
        _windowManager.removeView(_fpsView)
        _fpsView = null
    }

    private fun start() {
        if (_isOpen) return
        if (!UtilKPermission.hasOverlay()) {
            UtilKLaunchActivity.startManageOverlay(_context)
            LogK.etk(TAG, "FpsKView play app has no overlay permission")
            return
        }

        _isOpen = true
        _windowManager.addView(_fpsView, _params)
        _frameMonitor.addListener(_internalListener)
        _frameMonitor.start()
    }
}