package com.mozhimen.basick.utilk

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings

/**
 * @ClassName UtilKOverlay
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/23 11:46
 * @Version 1.0
 */
object UtilKOverlay {
    private val _context = UtilKGlobal.instance.getApp()!!

    fun startOverlaySettingActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            _context.startActivity(
                Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + _context.packageName)
                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    fun hasOverlayPermission(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(_context)
    }
}