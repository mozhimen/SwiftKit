package com.mozhimen.componentk.netk.file.download.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import com.mozhimen.basick.elemk.cons.CPackage
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.UtilKLog.et
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.componentk.BuildConfig
import com.mozhimen.componentk.netk.file.download.utils.Utils.getRealPathFromURI
import java.io.File

object InstallUtils : BaseUtilK() {
    fun hasInstallPermission(context: Context): Boolean {
        if (UtilKBuildVersion.isAfterV_26_8_O()) {
            return context.packageManager.canRequestPackageInstalls()
        }
        return true
    }

    fun settingPackageInstall(activity: Activity, requestCode: Int) {
        if (UtilKBuildVersion.isAfterV_26_8_O()) {
            val intentSetting = Intent(
                Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                Uri.parse("package:" + activity.packageName)
            )
            //intentSetting.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivityForResult(intentSetting, requestCode)
        }
    }

//fun createInstallIntent(context: Context, apkFile: File): Intent {
//    val uri: Uri = if (Build.VERSION.SDK_INT >= CVersionCode.V_24_7_N) {
//        FileProvider.getUriForFile(
//            context,
//            "${context.packageName}.fileProvider",
//            apkFile
//        )
//    } else {
//        Uri.fromFile(apkFile)
//    }
//    return createInstallIntent(context, uri)
//}

//fun createInstallIntent(context: Context, uri: Uri): Intent {
//    if (!hasInstallPermission(context)) {
//        return UpgradePermissionDialogActivity.createIntent(context, uri.toString())
//    }
//    val intent = Intent(Intent.ACTION_VIEW)
//    if (context !is Activity) {
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//    }
//    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//    intent.setDataAndType(uri, "application/vnd.android.package-archive")
//    return intent
//}

//fun startInstall(context: Context, apkFile: File) {
//    context.startActivity(createInstallIntent(context, apkFile))
//}
//
//fun startInstall(context: Context, uri: Uri) {
//    context.startActivity(createInstallIntent(context, uri))
//}


    /**
     * 下载的 apk 和当前程序版本比较
     *
     * - 首先会判断包名，程序的包名和apk包名是否一致
     * -
     * @param context Context 当前运行程序的Context
     * @param uri     apk file's location
     * @return true 可以安装；false 不需安装
     */
    private fun compare(context: Context, uri: Uri): Boolean {
        val realFilePath = getRealPathFromURI(context, uri) ?: return false
        val apkFileInfo = getApkFileSignature(context, realFilePath) ?: return false
        try {
            val packageInfo = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_CONFIGURATIONS
            )
            if (BuildConfig.DEBUG) {
                et(TAG, "apk file package=${apkFileInfo.packageName},versionCode=${apkFileInfo.versionCode}")
                et(TAG, "current package=${packageInfo.packageName},versionCode=${packageInfo.versionCode}")
            }
            //String appName = pm.getApplicationLabel(appInfo).toString();
            //Drawable icon = pm.getApplicationIcon(appInfo);//得到图标信息

            //如果下载的apk包名和当前应用不同，则不执行更新操作
            if (apkFileInfo.packageName == packageInfo.packageName
                && apkFileInfo.versionCode > packageInfo.versionCode
            ) {
                return true
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            return true
        }
        return false
    }

    /**
     * 获取apk程序信息[packageName,versionName...]
     *
     * @param context Context
     * @param path    apk path
     */
    private fun getApkFileSignature(context: Context, path: String): PackageInfo? {
        val file = File(path)
        if (!file.exists()) {
            return null
        }
        val pm = context.packageManager
        return if (UtilKBuildVersion.isAfterV_28_9_P()) {
            pm.getPackageArchiveInfo(
                path,
                PackageManager.GET_SIGNING_CERTIFICATES
            )

        } else {
            pm.getPackageArchiveInfo(
                path,
                PackageManager.GET_SIGNATURES
            )
        }
    }

    /**
     * 要启动的intent是否可用
     *
     * @return boolean
     */
    private fun intentAvailable(context: Context, intent: Intent): Boolean {
        return intent.resolveActivity(context.packageManager) != null
    }


    fun showDownloadComponentSetting(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:${CPackage.COM_ANDROID_PROVIDERS_DOWNLOADS}")
        if (intentAvailable(context, intent)) {
            context.startActivity(intent)
        }
    }


    /**
     * 系统的下载组件是否可用
     *
     * @return boolean
     */
    fun checkDownloadComponentEnable(context: Context): Boolean {
        try {
            val state =
                context.packageManager.getApplicationEnabledSetting(CPackage.COM_ANDROID_PROVIDERS_DOWNLOADS)
            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }
}
