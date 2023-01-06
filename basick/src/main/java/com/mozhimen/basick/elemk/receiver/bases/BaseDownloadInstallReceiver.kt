package com.mozhimen.basick.elemk.receiver.bases

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.CallSuper
import com.mozhimen.basick.elemk.annors.ADescription
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.basick.utilk.UtilKInstall


/**
 * @ClassName LoadKReceiverInstall
 * @Description if you use it, you must register in manifest first
 *

 * 权限
<uses-permission android:name="android.permission.REPLACE_EXISTING_PACKAGE"/>
<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>

 * 继承
class ElemKDownloadInstallReceiver : BaseDownloadInstallReceiver("")

 * 静态注册
<receiver android:name=".InstallReceiver"
android:exported="false">
<intent-filter>
<action android:name="android.intent.action.DOWNLOAD_COMPLETE" />
<action android:name="android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED"/>
</intent-filter>
</receiver>

 * AndroidManifest.xml sdk>24
<provider
android:name="androidx.core.content.FileProvider"
android:authorities="包名.fileprovider"
android:exported="false"
android:grantUriPermissions="true">
<meta-data
android:name="android.support.FILE_PROVIDER_PATHS"
android:resource="@xml/file_paths"  />
</provider>

 * file_paths.xml sdk>24
<paths>
<files-path
name="files-path"
path="." />
</paths>

 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/13 12:04
 * @Version 1.0
 */
@APermissionK(Manifest.permission.REQUEST_INSTALL_PACKAGES)
@ADescription(
    "    <uses-permission android:name=\"android.permission.REPLACE_EXISTING_PACKAGE\"/>\n",
    "    <uses-permission android:name=\"android.permission.REQUEST_INSTALL_PACKAGES\"/><!-- 8.0必要权限  -->\n"
)
open class BaseDownloadInstallReceiver(private val _apkPathWithName: String) : BroadcastReceiver() {
    companion object {
        private val TAG = "${this::class.java.simpleName}>>>>>"
    }

    override fun onReceive(context: Context, intent: Intent) {
        onReceiveInstall(context)
    }

    @CallSuper
    fun onReceiveInstall(context: Context) {
        UtilKInstall.installSmart(context, _apkPathWithName)
    }
}