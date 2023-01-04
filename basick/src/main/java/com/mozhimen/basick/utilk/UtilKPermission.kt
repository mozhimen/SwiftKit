package com.mozhimen.basick.utilk

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import com.mozhimen.basick.utilk.context.UtilKApplication


/**
 * @ClassName UtilKPermission
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/7 15:17
 * @Version 1.0
 */
object UtilKPermission {
    private val _context = UtilKApplication.instance.get()
    /**
     * 设置申请权限
     */
    @JvmStatic
    fun openSettingAll() {
        if (Build.VERSION.SDK_INT >= UtilKBuild.VersionCode.R) {//当系统在11及以上
            if (!Environment.isExternalStorageManager()) {// 没文件管理权限时申请权限
                val intent = Intent()
                intent.apply {
                    action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                    data = Uri.parse("package:${_context.packageName}")
                }
                _context.startActivity(intent)
            }
        }
    }

    /**
     * 设置申请权限
     */
    @JvmStatic
    fun openSettingSelf() {
        val intent = Intent()
        intent.apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", _context.packageName, null)
        }
        _context.startActivity(intent)
    }

    /**
     *
     */
    @JvmStatic
    fun openSettingAccessibility() {
        _context.startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
    }
}