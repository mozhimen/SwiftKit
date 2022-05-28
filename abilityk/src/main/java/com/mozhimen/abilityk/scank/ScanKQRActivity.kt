package com.mozhimen.abilityk.scank

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.TextUtils
import android.widget.FrameLayout
import com.huawei.hms.hmsscankit.RemoteView
import com.huawei.hms.ml.scan.HmsScan
import com.mozhimen.abilityk.R
import com.mozhimen.abilityk.databinding.ScankQrActivityBinding
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.toJson
import com.mozhimen.basick.utilk.UtilKScreen
import com.mozhimen.componentk.permissionk.PermissionK
import com.mozhimen.componentk.permissionk.annors.PermissionKAnnor

@PermissionKAnnor(permissions = [Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE])
class ScanKQRActivity : BaseKActivity<ScankQrActivityBinding, BaseKViewModel>(R.layout.scank_qr_activity) {
    companion object {
        const val SCANK_ACTIVITY_RESULT_PARAM = "SCANK_ACTIVITY_RESULT_PARAM"
    }

    private lateinit var _remoteView: RemoteView
    private val _detectRect = Rect()

    override fun initData(savedInstanceState: Bundle?) {
        PermissionK.initPermissions(this) {
            if (it) {
                initView(savedInstanceState)
            } else {
                PermissionK.applySetting(this)
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        initRect()
        initRemoteView(savedInstanceState)
    }

    private fun initRemoteView(savedInstanceState: Bundle?) {
        _remoteView = RemoteView.Builder().setContext(this).setBoundingBox(_detectRect)
            .setFormat(HmsScan.QRCODE_SCAN_TYPE).build()
        _remoteView.setOnResultCallback { results ->
            if (results != null && results.isNotEmpty() && results[0] != null && !TextUtils.isEmpty(results[0].originalValue)) {
                onScanResult(results[0])
            }
        }
        _remoteView.onCreate(savedInstanceState)
        vb.scankQrContainer.addView(
            _remoteView, FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )
    }

    private fun initRect() {
        val screenWidth = UtilKScreen.getScreenWidth()
        val screenHeight = UtilKScreen.getScreenHeight()
        val rectSize = vb.scankQrScan.getRectSize()

        _detectRect.apply {
            left = (screenWidth - rectSize) / 2
            right = (screenWidth + rectSize) / 2
            top = (screenHeight - rectSize) / 2
            bottom = (screenHeight + rectSize) / 2
        }
    }

    private fun onScanResult(hmsScan: HmsScan) {
        val intent = Intent()
        intent.putExtra(SCANK_ACTIVITY_RESULT_PARAM, hmsScan.toJson())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        _remoteView.onStart()
    }

    override fun onResume() {
        super.onResume()
        _remoteView.onResume()
    }

    override fun onPause() {
        super.onPause()
        _remoteView.onPause()
    }

    override fun onStop() {
        super.onStop()
        _remoteView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        _remoteView.onDestroy()
    }
}