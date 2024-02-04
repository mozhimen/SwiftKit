package com.mozhimen.basick.utilk.android.app

import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.optins.permission.OPermission_MANAGE_EXTERNAL_STORAGE
import com.mozhimen.basick.lintk.optins.permission.OPermission_REQUEST_INSTALL_PACKAGES
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKIntentWrapper
import com.mozhimen.basick.utilk.android.content.createChooser
import com.mozhimen.basick.utilk.android.content.isIntentAvailable
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.java.lang.UtilKRuntime
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
     * 分享文本
     */
    @JvmStatic
    fun startShareText(context: Context, title: String, str: String) {
        context.startContext(UtilKIntentWrapper.getShareText(str).createChooser(title))
    }

    /**
     * 打开外部浏览器
     */
    @JvmStatic
    fun startWebOutSide(context: Context, strUrl: String) {
        context.startContext(UtilKIntentWrapper.getStrUrl(strUrl)/*Intent(Intent.ACTION_VIEW, Uri.parse(strUrl)*/)
    }

    /**
     * 安装 if sdk >= 24 add provider
     */
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    @OPermission_REQUEST_INSTALL_PACKAGES
    @JvmStatic
    fun startInstall(context: Context, strPathNameApk: String) {
        context.startContext(
            UtilKIntentWrapper.getInstall(strPathNameApk.apply {
                if (UtilKBuildVersion.isBeforeVersion(CVersCode.V_24_7_N))
                    UtilKRuntime.chmod777(this)
            }) ?: return
        )
    }

    /**
     * 安装 if sdk >= 24 add provider
     */
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    @OPermission_REQUEST_INSTALL_PACKAGES
    @JvmStatic
    fun startInstall(context: Context, fileApk: File) {
        context.startContext(UtilKIntentWrapper.getInstall(fileApk) ?: return)
    }

    /**
     * 安装 if sdk >= 24 add provider
     */
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    @OPermission_REQUEST_INSTALL_PACKAGES
    @JvmStatic
    fun startInstall(context: Context, apkUri: Uri) {
        context.startContext(UtilKIntentWrapper.getInstall(apkUri))
    }

    /**
     * 打开包安装权限
     */
    @JvmStatic
    fun startManageUnknownInstallSource(context: Context) {
        if (UtilKBuildVersion.isAfterV_26_8_O())
            context.startContext(UtilKIntentWrapper.getManageUnknownAppSources(context))
    }

    @JvmStatic
    fun startAppNotificationSettings(context: Context) {
        if (UtilKBuildVersion.isAfterV_26_8_O())
            context.startContext(UtilKIntentWrapper.getAppNotificationSettings(context))
        else
            startSettingAppDetails(context)
    }

    /**
     * 打开包安装权限
     */
    @JvmStatic
    fun startManageUnknownInstallSourceForResult(activity: Activity, requestCode: Int) {
        if (UtilKBuildVersion.isAfterV_26_8_O())
            activity.startActivityForResult(UtilKIntentWrapper.getManageUnknownAppSources(activity), requestCode)
    }

    /**
     * 设置申请权限 当系统在23及以上
     */
    @JvmStatic
    fun startManageOverlay(context: Context) {
        if (UtilKBuildVersion.isAfterV_23_6_M())
            context.startContext(UtilKIntentWrapper.getManageOverlayPermission(context))
    }

    /**
     * 设置申请权限 当系统在11及以上
     */
    @JvmStatic
    @RequiresPermission(CPermission.MANAGE_EXTERNAL_STORAGE)
    @OPermission_MANAGE_EXTERNAL_STORAGE
    fun startManageAllFilesAccess(context: Context) {
        if (UtilKBuildVersion.isAfterV_30_11_R()) {
            //if (!Environment.isExternalStorageManager()) {// 没文件管理权限时申请权限
            context.startContext(UtilKIntentWrapper.getManageAppAllFilesAccessPermission(context))
            //}
        } else startSettingAppDetails(context)
    }

    /**
     * 设置申请权限 当系统在11及以上
     */
//    @JvmStatic
//    @RequiresPermission(CPermission.MANAGE_EXTERNAL_STORAGE)
//    fun startManageAllFilesAccess2(context: Context) {
//        if (UtilKBuildVersion.isAfterV_30_11_R()) {
//            context.startContext(Intent().apply {
//                data = Uri.parse(CSettings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
//            })
//        }
//    }

    /**
     * 设置申请权限 当系统在11及以上
     */
    @JvmStatic
    @RequiresPermission(CPermission.MANAGE_EXTERNAL_STORAGE)
    @OPermission_MANAGE_EXTERNAL_STORAGE
    fun startManageAllFilesAccess2(context: Context) {
        if (UtilKBuildVersion.isAfterV_30_11_R()) {
            //if (!Environment.isExternalStorageManager()) {// 没文件管理权限时申请权限
            context.startContext(UtilKIntentWrapper.getManageAppAllFilesAccessPermission(context).apply {
                addCategory("android.intent.category.DEFAULT")
            })
            //}
        } else startSettingAppDetails(context)
    }

    /**
     * 设置申请app权限
     */
    @JvmStatic
    fun startSettingAppDetails(context: Context) {
        context.startContext(UtilKIntentWrapper.getApplicationDetailsSettings(context))
    }

    @JvmStatic
    fun startSettingAppDetailsDownloads(context: Context) {
        val intent = UtilKIntentWrapper.getApplicationDetailsSettings(context)
        if (intent.isIntentAvailable(context))
            context.startContext(intent)
    }

    /**
     * 设置申请无障碍权限
     */
    @JvmStatic
    fun startSettingAccessibility(context: Context) {
        context.startContext(UtilKIntentWrapper.getAccessibilitySettings())
    }

    /**
     * 设置申请定位
     */
    @JvmStatic
    fun startSettingLocation(context: Context) {
        context.startContext(UtilKIntentWrapper.getLocationSourceSettings())
    }
}