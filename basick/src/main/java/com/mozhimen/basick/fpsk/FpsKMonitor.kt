package com.mozhimen.basick.fpsk

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import com.mozhimen.basick.R
import com.mozhimen.basick.extsk.decimal2String
import com.mozhimen.basick.fpsk.helpers.FrameMonitor
import com.mozhimen.basick.logk.LogK
import com.mozhimen.basick.stackk.StackK
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.utilk.UtilKGlobal
import com.mozhimen.basick.utilk.UtilKString
import java.text.DecimalFormat

/**
 * @ClassName FpsKMonitor
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/31 17:12
 * @Version 1.0
 */
object FpsKMonitor {

    private val TAG = "FpsKMonitor>>>>>"

    private val _fpsKViewer by lazy { FpsKViewer() }

    fun toggleView() {
        _fpsKViewer.toggle()
    }

    fun listener(listener: IFpsKListener) {
        _fpsKViewer.addListener(listener)
    }

    interface IFpsKListener {
        fun onFrame(fps: Double)
    }

    class FpsKViewer {
        private var _params = WindowManager.LayoutParams()
        private var _isShow = false
        private val _context = UtilKGlobal.instance.getApp()!!
        private var _fpsView =
            LayoutInflater.from(_context).inflate(R.layout.fpsk_layout, null, false) as TextView
        private val _frameMonitor = FrameMonitor()
        private var _windowManager: WindowManager = _context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        init {
            _params.width = WindowManager.LayoutParams.WRAP_CONTENT
            _params.height = WindowManager.LayoutParams.WRAP_CONTENT
            _params.flags =
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            _params.format = PixelFormat.TRANSLUCENT
            _params.gravity = Gravity.RIGHT or Gravity.BOTTOM
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                _params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                _params.type = WindowManager.LayoutParams.TYPE_TOAST
            }

            _frameMonitor.addListener(object : IFpsKListener {
                @SuppressLint("SetTextI18n")
                override fun onFrame(fps: Double) {
                    _fpsView.text = "${fps.decimal2String()} fps"
                }
            })

            StackK.addFrontBackListener(object : IStackKListener {
                override fun onChanged(isFront: Boolean) {
                    if (isFront) {
                        Log.d(TAG, "FpsKViewer onChanged fpsk start")
                        play()
                    } else {
                        Log.w(TAG, "FpsKViewer onChanged fpsk stop")
                        stop()
                    }
                }
            })
        }

        private fun stop() {
            _frameMonitor.stop()
            if (_isShow) {
                _isShow = false
                _windowManager.removeView(_fpsView)
            }
        }

        private fun play() {
            if (!hasOverlayPermission()) {
                startOverlaySettingActivity()
                LogK.et(TAG, "FpsKViewer play app has no overlay permission")
                return
            }

            _frameMonitor.start()
            if (!_isShow) {
                _isShow = true
                _windowManager.addView(_fpsView, _params)
            }

        }

        private fun startOverlaySettingActivity() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                _context.startActivity(
                    Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + _context.packageName)
                    ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
        }

        private fun hasOverlayPermission(): Boolean {
            return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(_context)
        }

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
    }
}