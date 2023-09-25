package com.mozhimen.basick.utilk.android.app

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.provider.cons.CSettings
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKIntent
import com.mozhimen.basick.utilk.android.content.startActivityForResult
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import java.io.File

/**
 * @ClassName UtilKPermission
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/7 15:17
 * @Version 1.0
 */
object UtilKLaunchActivity {
    /**
     * 安装 if sdk >= 24 add provider
     */
    @SuppressLint("InlinedApi")
    @RequiresPermission(allOf = [CPermission.REQUEST_INSTALL_PACKAGES])
    @JvmStatic
    fun startInstall(context: Context, apkPathWithName: String) {
        context.startContext(UtilKIntent.getInstall(apkPathWithName) ?: return)
    }

    @SuppressLint("InlinedApi")
    @RequiresPermission(allOf = [CPermission.REQUEST_INSTALL_PACKAGES])
    @JvmStatic
    fun startInstall(context: Context, apkFile: File) {
        context.startContext(UtilKIntent.getInstall(apkFile) ?: return)
    }

    @SuppressLint("InlinedApi")
    @RequiresPermission(allOf = [CPermission.REQUEST_INSTALL_PACKAGES])
    @JvmStatic
    fun startInstall(context: Context, apkUri: Uri) {
        context.startContext(UtilKIntent.getInstall(apkUri))
    }

    /**
     * 打开包安装权限
     */
    @JvmStatic
    fun startManageUnknownInstallSource(context: Context) {
        if (UtilKBuildVersion.isAfterV_26_8_O())
            context.startContext(UtilKIntent.getManageUnknownAppSources(context))
    }

    @JvmStatic
    fun startManageUnknownInstallSourceForResult(activity: Activity, requestCode: Int) {
        if (UtilKBuildVersion.isAfterV_26_8_O())
            activity.startActivityForResult(UtilKIntent.getManageUnknownAppSources(activity), requestCode)
    }

    /**
     * 设置申请权限 当系统在23及以上
     */
    @JvmStatic
    fun startManageOverlay(context: Context) {
        if (UtilKBuildVersion.isAfterV_23_6_M())
            context.startContext(UtilKIntent.getManageOverlayPermission(context))
    }

    /**
     * 设置申请权限 当系统在11及以上
     */
    @JvmStatic
    @RequiresPermission(CPermission.MANAGE_EXTERNAL_STORAGE)
    fun startManageAllFilesAccess(context: Context) {
        if (UtilKBuildVersion.isAfterV_30_11_R()) {
            //if (!Environment.isExternalStorageManager()) {// 没文件管理权限时申请权限
            context.startContext(UtilKIntent.getManageAppAllFilesAccessPermission(context))
            //}
        }
    }

    /**
     * 设置申请权限 当系统在11及以上
     */
    @JvmStatic
    @RequiresPermission(CPermission.MANAGE_EXTERNAL_STORAGE)
    fun startManageAllFilesAccess2(context: Context) {
        if (UtilKBuildVersion.isAfterV_30_11_R()) {
            context.startContext(Intent().apply {
                data = Uri.parse(CSettings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
            })
        }
    }

    /**
     * 设置申请权限 当系统在11及以上
     */
    @JvmStatic
    @RequiresPermission(CPermission.MANAGE_EXTERNAL_STORAGE)
    fun startManageAllFilesAccess3(context: Context) {
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
     */
    @JvmStatic
    fun startSettingAppDetails(context: Context) {
        context.startContext(UtilKIntent.getApplicationDetailsSettings(context))
    }

    /**
     * 设置申请无障碍权限
     */
    @JvmStatic
    fun startSettingAccessibility(context: Context) {
        context.startContext(UtilKIntent.getAccessibilitySettings())
    }

    /**
     * 设置申请定位
     */
    @JvmStatic
    fun startSettingLocation(context: Context) {
        context.startContext(UtilKIntent.getLocationSourceSettings())
    }
}