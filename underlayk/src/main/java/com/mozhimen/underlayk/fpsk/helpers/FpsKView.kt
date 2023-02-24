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
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.exts.decimal2String
import com.mozhimen.basick.stackk.StackK
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.content.UtilKPermission
import com.mozhimen.basick.utilk.bar.UtilKDialog
import com.mozhimen.underlayk.R
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
class FpsKView {
    private val TAG = "FpsKView>>>>>"

    private val _context = UtilKApplication.instance.get()
    private var _params = WindowManager.LayoutParams()
    private var _isShow = false
    private var _fpsView =
        LayoutInflater.from(_context).inflate(R.layout.fpsk_view, null, false) as TextView
    private val _frameMonitor = FrameMonitor()
    private var _windowManager: WindowManager = _context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    fun toggle() {
        if (_isShow) {
            stop()
        } else {
            play()
        }
    }

    fun addListener(listener: IFpsKListener) {
        _frameMonitor.addListener(listener)
    }

    init {
        initParams()
        initView()
    }

    private fun initView() {
        _frameMonitor.addListener(object : IFpsKListener {
            @SuppressLint("SetTextI18n")
            override fun onFrame(fps: Double) {
                _fpsView.text = "${fps.decimal2String()} fps"
            }
        })

        StackK.addFrontBackListener(object : IStackKListener {
            override fun onChanged(isFront: Boolean) {
                if (isFront) {
                    LogK.dt(TAG, "FpsKView onChanged fpsk start")
                    play()
                } else {
                    LogK.wt(TAG, "FpsKView onChanged fpsk stop")
                    stop()
                }
            }
        })
    }

    private fun initParams() {
        _params.width = WindowManager.LayoutParams.WRAP_CONTENT
        _params.height = WindowManager.LayoutParams.WRAP_CONTENT
        _params.flags =
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        _params.format = PixelFormat.TRANSLUCENT
        _params.gravity = Gravity.END or Gravity.BOTTOM
        if (Build.VERSION.SDK_INT >= CVersionCode.V_26_8_O) {
            _params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            _params.type = WindowManager.LayoutParams.TYPE_TOAST
        }
    }

    private fun stop() {
        _frameMonitor.stop()
        if (_isShow) {
            _isShow = false
            _windowManager.removeView(_fpsView)
        }
    }

    private fun play() {
        if (!UtilKDialog.isOverlayPermissionEnable(_context)) {
            UtilKPermission.openSettingOverlay(_context)
            LogK.et(TAG, "FpsKView play app has no overlay permission")
            return
        }

        _frameMonitor.start()
        if (!_isShow) {
            _isShow = true
            _windowManager.addView(_fpsView, _params)
        }
    }
}