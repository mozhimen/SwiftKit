package com.mozhimen.abilityk.installk

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import com.mozhimen.basick.elemk.annors.ADescription
import com.mozhimen.basick.utilk.UtilKFile
import com.mozhimen.basick.utilk.UtilKInstall
import com.mozhimen.basick.utilk.UtilKPermission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @ClassName AccessibilityInstall
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/4 23:01
 * @Version 1.0
 */
object InstallKSmart {
    private const val TAG = "InstallKSmart>>>>>"

    /**
     * 打开设置
     * @param activity Activity
     */
    @JvmStatic
    fun openInstallSmartSetting(activity: Activity) {
        UtilKPermission.openSettingAccessibility(activity)
    }

    /**
     * if build sdk > N you also add provider and @xml/file_paths

     * AndroidManifest.xml
    <provider
    android:name="androidx.core.content.FileProvider"
    android:authorities="包名.fileprovider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
    android:name="android.support.FILE_PROVIDER_PATHS"
    android:resource="@xml/file_paths"  />
    </provider>

     * file_paths.xml
    <paths>
    <files-path
    name="files-path"
    path="." />
    </paths>

     * @see [InstallKSmartService] this service
     * @param apkPathWithName String
     * @param activity Activity
     */
    @JvmStatic
    @ADescription(
        ">> you must register SmartInstallService first",
        ">> 你必须先在工程app的Manifest下注册SmartInstallService,才有效"
    )
    suspend fun installSmart(apkPathWithName: String, activity: Activity) {
        withContext(Dispatchers.Main) {
            UtilKInstall.installSmart(activity, apkPathWithName)
        }
    }
}