package com.mozhimen.basick.utilk.android.app

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.provider.cons.CSettings
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKContextStart
import com.mozhimen.basick.utilk.android.content.UtilKIntent
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion

/**
 * @ClassName UtilKPermission
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/7 15:17
 * @Version 1.0
 */
object UtilKLaunchActivity {
    /**
     * 安装
     * if sdk >= 24 add provider
     * @param context Context
     * @param apkPathWithName String
     */
    @RequiresApi(CVersCode.V_23_6_M)
    @JvmStatic
    fun startInstall(context: Context, apkPathWithName: String) {
        context.startContext(UtilKIntent.getInstall(apkPathWithName) ?: return)
    }

    /**
     * 打开包安装权限
     * @param context Context
     */
    @JvmStatic
    fun startManageInstallSource(context: Context) {
        if (UtilKBuildVersion.isAfterV_26_8_O())
            context.startContext(UtilKIntent.getManageUnknownAppSources(context))
    }

    /**
     * 设置申请权限 当系统在23及以上
     * @param context Context
     */
    @JvmStatic
    fun startManageOverlay(context: Context) {
        if (UtilKBuildVersion.isAfterV_23_6_M())
            context.startContext(UtilKIntent.getManageOverlayPermission(context))
    }

    /**
     * 设置申请权限 当系统在11及以上
     * @param context Context
     */
    @JvmStatic
    @RequiresPermission(CPermission.MANAGE_EXTERNAL_STORAGE)
    fun startManageAll(context: Context) {
        if (UtilKBuildVersion.isAfterV_30_11_R()) {
            //if (!Environment.isExternalStorageManager()) {// 没文件管理权限时申请权限
            context.startContext(UtilKIntent.getManageAppAllFilesAccessPermission(context))
            //}
        }
    }

    /**
     * 设置申请权限 当系统在11及以上
     * @param context Context
     */
    @JvmStatic
    @RequiresPermission(CPermission.MANAGE_EXTERNAL_STORAGE)
    fun startManageAll2(context: Context) {
        if (UtilKBuildVersion.isAfterV_30_11_R()) {
            context.startContext(Intent().apply {
                data = Uri.parse(CSettings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
            })
        }
    }

    /**
     * 设置申请权限 当系统在11及以上
     * @param context Context
     */
    @JvmStatic
    @RequiresPermission(CPermission.MANAGE_EXTERNAL_STORAGE)
    fun startManageAll3(context: Context) {
        if (UtilKBuildVersion.isAfterV_30_11_R()) {
            //if (!Environment.isExternalStorageManager()) {// 没文件管理权限时申请权限
            context.startContext(UtilKIntent.getManageAppAllFilesAccessPermission(context).apply {
                addCategory("android.intent.category.DEFAULT")
            })
            //}
        }
    }

    /**
     * 设置申请app权限
     * @param context Context
     */
    @JvmStatic
    fun startSettingAppDetails(context: Context) {
        context.startContext(UtilKIntent.getApplicationDetailsSettings(context))
    }

    /**
     * 设置申请无障碍权限
     * @param context Context
     */
    @JvmStatic
    fun startSettingAccessibility(context: Context) {
        context.startContext(UtilKIntent.getAccessibilitySettings())
    }
}