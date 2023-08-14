package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.elemk.android.media.cons.CMediaFormat
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.provider.cons.CSettings
import com.mozhimen.basick.elemk.commons.IExtension_Listener
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.app.UtilKActivity
import com.mozhimen.basick.utilk.android.net.UtilKUri
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.kotlin.UtilKString

/**
 * @ClassName UtilKIntent
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/26 23:03
 * @Version 1.0
 */
fun Intent.createChooser(title: CharSequence): Intent =
    UtilKIntent.createChooser(this, title)

fun Context.createIntent(clazz: Class<*>): Intent =
    UtilKIntent.createIntent(this, clazz)

inline fun <reified T> Context.createIntent(): Intent =
    UtilKIntent.createIntent<T>(this)

inline fun <reified T> Context.createIntent(block: IExtension_Listener<Intent>): Intent =
    UtilKIntent.createIntent<T>(this, block)

object UtilKIntent {
    fun createIntent(context: Context, clazz: Class<*>): Intent =
        Intent(context, clazz)

    inline fun <reified T> createIntent(context: Context): Intent =
        Intent(context, T::class.java)

    inline fun <reified T> createIntent(context: Context, block: IExtension_Listener<Intent>): Intent =
        Intent(context, T::class.java).apply(block)

    /**
     * 选择系统文件
     * @return Intent
     */
    @JvmStatic
    fun getPick(): Intent =
        Intent(CIntent.ACTION_PICK)

    /**
     * 选择系统图像
     * @return Intent
     */
    @JvmStatic
    fun getPickImage(): Intent =
        getPick().apply { setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, CMediaFormat.MIMETYPE_IMAGE_ALL) }

    /**
     * 获取设置无障碍
     * @return Intent
     */
    @JvmStatic
    fun getAccessibilitySettings(): Intent =
        Intent(CSettings.ACTION_ACCESSIBILITY_SETTINGS)

    /**
     * 管理APP设置
     * @param context Context
     * @return Intent
     */
    @JvmStatic
    fun getApplicationDetailsSettings(context: Context): Intent =
        Intent(CSettings.ACTION_APPLICATION_DETAILS_SETTINGS, UtilKUri.getPackageUri2(context))

    /**
     * 管理通知
     * @param context Context
     * @return Intent
     */
    @RequiresApi(CVersCode.V_26_8_O)
    @JvmStatic
    fun getAppNotificationSettings(context: Context): Intent =
        Intent(CSettings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
            putExtra(CSettings.EXTRA_APP_PACKAGE, context.packageName)
        }

    /**
     * 获取管理所有APP
     * @param context Context
     * @return Intent
     */
    @RequiresApi(CVersCode.V_30_11_R)
    @JvmStatic
    @RequiresPermission(CPermission.MANAGE_EXTERNAL_STORAGE)
    fun getManageAppAllFilesAccessPermission(context: Context): Intent =
        Intent(CSettings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, UtilKUri.getPackageUri(context))

    /**
     * 获取管理悬浮窗
     * @param context Context
     * @return Intent
     */
    @RequiresApi(CVersCode.V_23_6_M)
    @JvmStatic
    fun getManageOverlayPermission(context: Context): Intent =
        Intent(CSettings.ACTION_MANAGE_OVERLAY_PERMISSION, UtilKUri.getPackageUri(context))

    /**
     * 获取管理安装
     * @param context Context
     * @return Intent
     */
    @RequiresApi(CVersCode.V_26_8_O)
    @JvmStatic
    fun getManageUnknownAppSources(context: Context): Intent =
        Intent(CSettings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, UtilKUri.getPackageUri(context))

    /**
     * 获取mainLauncher
     * @param packageName String
     * @param launcherActivityName String
     * @return Intent
     */
    @JvmStatic
    fun getMainLauncher(packageName: String, launcherActivityName: String): Intent =
        Intent(CIntent.ACTION_MAIN).apply {
            addCategory(CIntent.CATEGORY_LAUNCHER)
            setClassName(packageName, launcherActivityName)
        }

    /**
     * 获取mainLauncher
     * @param packageName String
     * @param uri Uri?
     * @return Intent
     */
    @JvmStatic
    fun getMainLauncher(packageName: String, uri: Uri? = null): Intent =
        Intent(CIntent.ACTION_MAIN, uri).apply {
            addCategory(CIntent.CATEGORY_LAUNCHER)
            setPackage(packageName)
        }

    /**
     * 获取启动App的Intent
     * @param packageName String
     * @return Intent?
     */
    @JvmStatic
    fun getLauncherActivity(context: Context, packageName: String): Intent? {
        val launcherActivityName: String = UtilKActivity.getLauncherActivityName(context, packageName)
        if (UtilKString.hasSpace(launcherActivityName) || launcherActivityName.isEmpty()) return getLauncherForPackage(context)
        return getMainLauncher(packageName, launcherActivityName)
    }

    @JvmStatic
    fun getLauncherForPackage(context: Context): Intent? =
        UtilKPackageManager.get(context).getLaunchIntentForPackage(UtilKPackage.getPackageName())

    /**
     * 获取安装app的intent
     * @param filePathWithName String
     * @return Intent?
     */
    @RequiresApi(CVersCode.V_23_6_M)
    @JvmStatic
    @RequiresPermission(allOf = [CPermission.REQUEST_INSTALL_PACKAGES])
    fun getInstall(filePathWithName: String): Intent? {
        val intent = Intent(CIntent.ACTION_VIEW)
        if (UtilKBuildVersion.isAfterV_24_7_N()) {//判断安卓系统是否大于7.0  大于7.0使用以下方法
            intent.addFlags(CIntent.FLAG_GRANT_READ_URI_PERMISSION) //增加读写权限//添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intent.setDataAndType(UtilKUri.strFilePath2uri(filePathWithName) ?: return null, "application/vnd.android.package-archive")
        return intent
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun createChooser(target: Intent, title: CharSequence): Intent =
        Intent.createChooser(target, title)
}