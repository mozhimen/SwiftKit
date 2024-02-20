package com.mozhimen.basick.utilk.android.content

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.elemk.android.media.cons.CMediaFormat
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.provider.cons.CMediaStore
import com.mozhimen.basick.elemk.android.provider.cons.CSettings
import com.mozhimen.basick.elemk.cons.CStrPackage
import com.mozhimen.basick.lintk.optins.permission.OPermission_MANAGE_EXTERNAL_STORAGE
import com.mozhimen.basick.lintk.optins.permission.OPermission_QUERY_ALL_PACKAGES
import com.mozhimen.basick.lintk.optins.permission.OPermission_REQUEST_INSTALL_PACKAGES
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.app.UtilKActivity
import com.mozhimen.basick.utilk.android.net.UtilKUri
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.java.io.file2uri
import com.mozhimen.basick.utilk.kotlin.UtilKStrFile
import com.mozhimen.basick.utilk.kotlin.UtilKString
import com.mozhimen.basick.utilk.kotlin.strFilePath2uri
import com.mozhimen.basick.utilk.kotlin.strUri2uri
import java.io.File

/**
 * @ClassName UtilKIntentWrapper
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/26 22:12
 * @Version 1.0
 */
object UtilKIntentWrapper {

    ///////////////////////////////////////////////////////////////////////////////////////
    //CIntent
    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * 分享文字
     */
    @JvmStatic
    fun getShareText(str: String): Intent =
        Intent(CIntent.ACTION_SEND).apply {
            putExtra(CIntent.EXTRA_TEXT, str)
            type = CMediaFormat.MIMETYPE_TEXT_PLAIN
        }

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * 选择系统文件
     */
    @JvmStatic
    fun getPick(): Intent =
        Intent(CIntent.ACTION_PICK)

    @JvmStatic
    fun getPickImage(): Intent =
        Intent(CIntent.ACTION_PICK).apply { type = CMediaFormat.MIMETYPE_IMAGE_ALL }

    /**
     * 选择系统图像
     */
    @JvmStatic
    fun getPickUriImage(): Intent =
        getPick().apply { setDataAndType(CMediaStore.Images.Media.EXTERNAL_CONTENT_URI, CMediaFormat.MIMETYPE_IMAGE_ALL) }

    ///////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getContent(): Intent =
        Intent(CIntent.ACTION_GET_CONTENT)

    @JvmStatic
    fun getContentAudio(): Intent =
        getContent().apply { type = CMediaFormat.MIMETYPE_AUDIO_ALL }

    @JvmStatic
    fun getContentVideo(): Intent =
        getContent().apply { type = CMediaFormat.MIMETYPE_VIDEO_ALL }

    @JvmStatic
    fun getContentAudioVideo(): Intent =
        getContent().apply { type = "${CMediaFormat.MIMETYPE_AUDIO_ALL};${CMediaFormat.MIMETYPE_VIDEO_ALL}" }

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * 获取mainLauncher
     */
    @JvmStatic
    fun getMainLauncher(strPackageName: String, strActivityName: String): Intent =
        Intent(CIntent.ACTION_MAIN).apply {
            addCategory(CIntent.CATEGORY_LAUNCHER)
            setClassName(strPackageName, strActivityName)
        }

    /**
     * 获取mainLauncher
     */
    @JvmStatic
    fun getMainLauncher(strPackageName: String, uri: Uri? = null): Intent =
        Intent(CIntent.ACTION_MAIN, uri).apply {
            addCategory(CIntent.CATEGORY_LAUNCHER)
            setPackage(strPackageName)
        }

    /**
     * 获取mainLauncher
     */
    @JvmStatic
    fun getMainLauncher(strPackageName: String): Intent =
        Intent(CIntent.ACTION_MAIN).apply {
            addCategory(CIntent.CATEGORY_LAUNCHER)
            setPackage(strPackageName)
            addFlags(CIntent.FLAG_ACTIVITY_NEW_TASK)
        }

    /**
     * 获取启动App的Intent
     */
    @JvmStatic
    @OPermission_QUERY_ALL_PACKAGES
    @RequiresPermission(CPermission.QUERY_ALL_PACKAGES)
    fun getLauncherForPackageOfQuery(context: Context, strPackageName: String): Intent? {
        val strLauncherActivityName: String = UtilKActivity.getLauncherActivityName(context, strPackageName)
        if (UtilKString.hasSpace(strLauncherActivityName) || strLauncherActivityName.isEmpty()) return getLauncherForPackage(context, strPackageName)
        return getMainLauncher(strPackageName, strLauncherActivityName)
    }

    @JvmStatic
    fun getLauncherForPackage(context: Context, strPackageName: String): Intent? =
        UtilKPackageManager.getLaunchIntentForPackage(context, strPackageName)

    @JvmStatic
    @OPermission_QUERY_ALL_PACKAGES
    @RequiresPermission(CPermission.QUERY_ALL_PACKAGES)
    fun getLauncherForPackageOfComponent(context: Context, strPackageName: String): Intent? {
        val intent = getMainLauncher(strPackageName)
        val resolveInfos = UtilKPackageManager.queryIntentActivities(context, intent, 0) //查询要启动的Activity
        return if (resolveInfos.isNotEmpty()) { //如果包名存在
            val resolveInfo = resolveInfos[0]
            intent.component = ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name)//组装包名和类名//设置给Intent
            intent//根据包名类型打开Activity
        } else null
    }

    @JvmStatic
    fun getLauncherForPackageOfComponent(strPackageName: String, strActivityName: String): Intent =
        Intent().apply {
            component = ComponentName(strPackageName, "$strPackageName.$strActivityName")
        }

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * 获取安装app的intent
     */
    @JvmStatic
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    @OPermission_REQUEST_INSTALL_PACKAGES
    fun getInstall(apkUri: Uri): Intent {
        val intent = Intent(CIntent.ACTION_VIEW)
        if (UtilKBuildVersion.isAfterV_24_7_N()) //判断安卓系统是否大于7.0  大于7.0使用以下方法
            intent.addFlags(CIntent.FLAG_GRANT_READ_URI_PERMISSION) //增加读写权限//添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        return intent
    }

    /**
     * 获取安装app的intent
     */
    @JvmStatic
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    @OPermission_REQUEST_INSTALL_PACKAGES
    fun getInstall(strFilePathName: String): Intent? =
        UtilKStrFile.strFilePath2uri(strFilePathName)?.let { getInstall(it) }

    /**
     * 获取安装app的intent
     */
    @JvmStatic
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    @OPermission_REQUEST_INSTALL_PACKAGES
    fun getInstall(fileApk: File): Intent? =
        fileApk.file2uri()?.let { getInstall(it) }

    ///////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getUri(uri: Uri): Intent =
        Intent(CIntent.ACTION_VIEW, uri)

    @JvmStatic
    fun getStrUrl(strUrl: String): Intent =
        getUri(Uri.parse(strUrl))

    ///////////////////////////////////////////////////////////////////////////////////////
    //CSettings
    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Get location source settings
     *  定位服务
     */
    @JvmStatic
    fun getLocationSourceSettings(): Intent =
        Intent(CSettings.ACTION_LOCATION_SOURCE_SETTINGS)

    /**
     * 获取设置无障碍
     */
    @JvmStatic
    fun getAccessibilitySettings(): Intent =
        Intent(CSettings.ACTION_ACCESSIBILITY_SETTINGS)

    /**
     * 管理APP设置
     */
    @JvmStatic
    fun getApplicationDetailsSettings(context: Context): Intent =
        Intent(CSettings.ACTION_APPLICATION_DETAILS_SETTINGS, UtilKUri.getPackageUriOfParts(context))

    /**
     * 管理APP下载
     */
    fun getApplicationDetailsSettingsDownloads(context: Context): Intent =
        Intent(CSettings.ACTION_APPLICATION_DETAILS_SETTINGS, "package:${CStrPackage.COM_ANDROID_PROVIDERS_DOWNLOADS}".strUri2uri())

    /**
     * 管理通知
     */
    @RequiresApi(CVersCode.V_26_8_O)
    @JvmStatic
    fun getAppNotificationSettings(context: Context): Intent =
        Intent(CSettings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
            putExtra(CSettings.EXTRA_APP_PACKAGE, context.packageName)
        }

    /**
     * 管理通知
     */
    @RequiresApi(CVersCode.V_26_8_O)
    @JvmStatic
    private fun startNotificationSetting2(context: Context): Intent =
        getAppNotificationSettings(context).apply {
            putExtra("app_package", context.packageName)
            putExtra("app_uid", context.applicationInfo.uid)
        }

    /**
     * 获取管理所有APP
     */
    @RequiresApi(CVersCode.V_30_11_R)
    @JvmStatic
    @RequiresPermission(CPermission.MANAGE_EXTERNAL_STORAGE)
    @OPermission_MANAGE_EXTERNAL_STORAGE
    fun getManageAppAllFilesAccessPermission(context: Context): Intent =
        Intent(CSettings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, UtilKUri.getPackageUri(context))

    /**
     * 获取管理悬浮窗
     */
    @RequiresApi(CVersCode.V_23_6_M)
    @JvmStatic
    fun getManageOverlayPermission(context: Context): Intent =
        Intent(CSettings.ACTION_MANAGE_OVERLAY_PERMISSION, UtilKUri.getPackageUri(context))

    /**
     * 获取管理安装
     */
    @RequiresApi(CVersCode.V_26_8_O)
    @JvmStatic
    fun getManageUnknownAppSources(context: Context): Intent =
        Intent(CSettings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, UtilKUri.getPackageUri(context))

    ///////////////////////////////////////////////////////////////////////////////////////
    //MediaStore
    ///////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getImageCapture(): Intent =
        Intent(CMediaStore.ACTION_IMAGE_CAPTURE)

    @JvmStatic
    fun getImageCaptureOutput(strFilePathName: String): Intent? =
        strFilePathName.strFilePath2uri()?.let { getImageCaptureOutput(it) }

    @JvmStatic
    fun getImageCaptureOutput(file: File): Intent? =
        file.file2uri()?.let { getImageCaptureOutput(it) }

    @JvmStatic
    fun getImageCaptureOutput(uri: Uri): Intent =
        getImageCapture().apply {
            putExtra(CMediaStore.EXTRA_OUTPUT, uri)
        }

}