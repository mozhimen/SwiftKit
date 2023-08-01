package com.mozhimen.basick.utilk.android.app

import android.app.Activity
import android.content.Context
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKContextStart
import com.mozhimen.basick.utilk.android.content.UtilKIntent
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
    @JvmStatic
    fun startInstall(context: Context, apkPathWithName: String) {
        UtilKContextStart.startContext(context, UtilKIntent.getInstall(apkPathWithName) ?: return)
    }

    /**
     * 打开包安装权限
     * @param context Context
     */
    @JvmStatic
    fun startManageInstallSource(context: Context) {
        if (UtilKBuildVersion.isAfterV_26_8_O())
            UtilKContextStart.startContext(context, UtilKIntent.getManageInstallSource(context))
    }

    /**
     * 设置申请权限 当系统在23及以上
     * @param context Context
     */
    @JvmStatic
    fun startManageOverlay(context: Context) {
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKContextStart.startContext(context, UtilKIntent.getManageOverlay(context))
    }

    /**
     * 设置申请权限 当系统在11及以上
     * @param context Context
     */
    @JvmStatic
    @RequiresPermission(CPermission.MANAGE_EXTERNAL_STORAGE)
    fun startManageAll(context: Context) {
        if (UtilKBuildVersion.isAfterV_30_11_R())
        //if (!Environment.isExternalStorageManager()) {// 没文件管理权限时申请权限
            UtilKContextStart.startContext(context, UtilKIntent.getManageAll(context))
        //}
    }

    /**
     * 设置申请app权限
     * @param context Context
     */
    @JvmStatic
    fun startSettingAppDetails(context: Context) {
        UtilKContextStart.startContext(context, UtilKIntent.getSettingAppDetails(context))
    }

    /**
     * 设置申请无障碍权限
     * @param context Context
     */
    @JvmStatic
    fun startSettingAccessibility(context: Context) {
        UtilKContextStart.startContext(context, UtilKIntent.getSettingAccessibility())
    }
}