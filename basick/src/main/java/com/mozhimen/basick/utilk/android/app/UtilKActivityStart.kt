package com.mozhimen.basick.utilk.android.app

import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.optins.permission.OPermission_MANAGE_EXTERNAL_STORAGE
import com.mozhimen.basick.lintk.optins.permission.OPermission_REQUEST_INSTALL_PACKAGES
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKIntentWrapper
import com.mozhimen.basick.utilk.android.content.createChooser
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.java.lang.UtilKRuntimeWrapper
import java.io.File

/**
 * @ClassName UtilKPermission
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/7 15:17
 * @Version 1.0
 */
object UtilKActivityStart {
    //分享文本
    @JvmStatic
    fun startSendTextChooser(context: Context, title: String, str: String) {
        context.startContext(UtilKIntentWrapper.getSendText(str).createChooser(title))
    }

    ///////////////////////////////////////////////////////////////////////

    //打开外部浏览器
    @JvmStatic
    fun startViewStrUrl(context: Context, strUrl: String) {
        context.startContext(UtilKIntentWrapper.getViewStrUrl(strUrl)/*Intent(Intent.ACTION_VIEW, Uri.parse(strUrl)*/)
    }

    //安装 if sdk >= 24 add provider
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    @OPermission_REQUEST_INSTALL_PACKAGES
    @JvmStatic
    fun startViewInstall(context: Context, strPathNameApk: String) {
        context.startContext(
            UtilKIntentWrapper.getViewInstall(strPathNameApk.apply {
                if (UtilKBuildVersion.isBeforeVersion(CVersCode.V_24_7_N))
                    UtilKRuntimeWrapper.exec_chmod_777(this)
            }) ?: return
        )
    }

    //安装 if sdk >= 24 add provider
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    @OPermission_REQUEST_INSTALL_PACKAGES
    @JvmStatic
    fun startViewInstall(context: Context, fileApk: File) {
        context.startContext(UtilKIntentWrapper.getViewInstall(fileApk) ?: return)
    }

    //安装 if sdk >= 24 add provider
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    @OPermission_REQUEST_INSTALL_PACKAGES
    @JvmStatic
    fun startViewInstall(context: Context, uriApk: Uri) {
        context.startContext(UtilKIntentWrapper.getViewInstall(uriApk))
    }

    ///////////////////////////////////////////////////////////////////////

    //打开包安装权限
    @JvmStatic
    fun startSettingManageUnknownInstallSource(context: Context) {
        if (UtilKBuildVersion.isAfterV_26_8_O())
            context.startContext(UtilKIntentWrapper.getSettingManageUnknownAppSources(context))
    }

    //打开包安装权限
    @JvmStatic
    fun startSettingManageUnknownInstallSource_ofResult(activity: Activity, requestCode: Int) {
        if (UtilKBuildVersion.isAfterV_26_8_O())
            activity.startActivityForResult(UtilKIntentWrapper.getSettingManageUnknownAppSources(activity), requestCode)
    }

    //设置申请权限 当系统在23及以上
    @JvmStatic
    fun startSettingManageOverlayPermission(context: Context) {
        if (UtilKBuildVersion.isAfterV_23_6_M())
            context.startContext(UtilKIntentWrapper.getSettingManageOverlayPermission(context))
    }

    //设置申请权限 当系统在11及以上
    @JvmStatic
    @RequiresPermission(CPermission.MANAGE_EXTERNAL_STORAGE)
    @OPermission_MANAGE_EXTERNAL_STORAGE
    fun startSettingManageAllFilesAccessPermission(context: Context) {
        if (UtilKBuildVersion.isAfterV_30_11_R()) {
            context.startContext(UtilKIntentWrapper.getSettingManageAppAllFilesAccessPermission(context))
        } else startSettingApplicationDetailsSettings(context)
    }

    ///////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun startSettingAppNotificationSettings(context: Context) {
        if (UtilKBuildVersion.isAfterV_26_8_O())
            context.startContext(UtilKIntentWrapper.getSettingAppNotificationSettings(context))
        else
            startSettingApplicationDetailsSettings(context)
    }

    //设置申请app权限
    @JvmStatic
    fun startSettingApplicationDetailsSettings(context: Context) {
        context.startContext(UtilKIntentWrapper.getSettingApplicationDetailsSettings(context))
    }

    @JvmStatic
    fun startSettingApplicationDetailsSettings_ofDownloads(context: Context) {
        context.startContext(UtilKIntentWrapper.getSettingApplicationDetailsSettings_ofDownloads(context))
    }

    //设置申请无障碍权限
    @JvmStatic
    fun startSettingAccessibilitySettings(context: Context) {
        context.startContext(UtilKIntentWrapper.getSettingAccessibilitySettings())
    }

    //设置申请定位
    @JvmStatic
    fun startSettingLocationSourceSettings(context: Context) {
        context.startContext(UtilKIntentWrapper.getSettingLocationSourceSettings())
    }

    //设置生物识别
    @RequiresApi(CVersCode.V_30_11_R)
    @JvmStatic
    fun startSettingBiometricEnroll_ofResult(activity: Activity, allowedAuthenticators: Int, requestCode: Int) {
        activity.startActivityForResult(UtilKIntentWrapper.getSettingBiometricEnroll(allowedAuthenticators), requestCode)
    }
}