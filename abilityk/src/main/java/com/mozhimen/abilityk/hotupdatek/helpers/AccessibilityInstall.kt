package com.mozhimen.abilityk.hotupdatek.helpers

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.content.FileProvider
import com.mozhimen.basick.elemk.annors.ADescription
import com.mozhimen.basick.utilk.UtilKPermission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * @ClassName AccessibilityInstall
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/4 23:01
 * @Version 1.0
 */
object AccessibilityInstall {
    private const val TAG = "AccessibilityInstall>>>>>"

    @JvmStatic
    fun openInstallSmartSetting(activity: Activity) {
        UtilKPermission.openSettingAccessibility(activity)
    }

    /**
     * @see [SmartInstallService] this service
     * @param apkPathWithName String
     */
    @SuppressLint("LongLogTag")
    @JvmStatic
    @ADescription(
        "you must register SmartInstallService first",
        "你必须先在工程app的Manifest下注册SmartInstallService,才有效"
    )
    suspend fun installSmart(apkPathWithName: String, activity: Activity) {
        withContext(Dispatchers.Main) {
            val uri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Log.d(TAG, "installSmart: packageName ${activity.packageName}")
                FileProvider.getUriForFile(activity, "${activity.packageName}.fileprovider", File(apkPathWithName))
            } else {
                Uri.fromFile(File(apkPathWithName))
            }
            val localIntent = Intent(Intent.ACTION_VIEW)
            localIntent.setDataAndType(uri, "application/vnd.android.package-archive")
            activity.startActivity(localIntent)
        }
    }
}