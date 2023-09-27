package com.mozhimen.componentk.netk.file.download

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import com.mozhimen.basick.elemk.cons.CStrPackage
import com.mozhimen.basick.utilk.android.content.UtilKPackageInfo
import com.mozhimen.basick.utilk.android.content.isIntentAvailable
import com.mozhimen.basick.utilk.android.net.uri2strFilePath
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.UtilKLog.et
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.componentk.BuildConfig
import java.io.File

object InstallUtils : BaseUtilK() {

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
        val realFilePath = uri.uri2strFilePath() ?: return false
        val apkFileInfo = getApkFileSignature(context, realFilePath) ?: return false
        try {
            val packageInfo = UtilKPackageInfo.getOfConfigurations(context)!!
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
}
