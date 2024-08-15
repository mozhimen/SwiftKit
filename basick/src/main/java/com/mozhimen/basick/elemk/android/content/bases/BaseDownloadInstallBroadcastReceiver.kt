package com.mozhimen.basick.elemk.android.content.bases

import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.app.cons.CDownloadManager
import com.mozhimen.basick.lintk.optins.permission.OPermission_READ_INSTALL_SESSIONS
import com.mozhimen.basick.lintk.optins.permission.OPermission_REPLACE_EXISTING_PACKAGE
import com.mozhimen.basick.lintk.optins.permission.OPermission_REQUEST_INSTALL_PACKAGES
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.wrapper.UtilKAppInstall


/**
 * @ClassName LoadKReceiverInstall
 * @Description if you use it, you must register in manifest first
 *

 * 权限:
/*Permission.INSTALL_PACKAGES,*/
Permission.REQUEST_INSTALL_PACKAGES,
Permission.READ_INSTALL_SESSIONS,
Permission.REPLACE_EXISTING_PACKAGE

 * 继承:
class ElemKDownloadInstallReceiver : BaseDownloadInstallReceiver("你的包名")

 * 静态注册(AndroidManifest.xml):
<receiver android:name=".elemk.receiver.ElemKDownloadInstallReceiver"
android:enabled="true"
android:exported="true">
<intent-filter>
<action android:name="android.intent.action.DOWNLOAD_COMPLETE" />
<action android:name="android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED"/>
</intent-filter>
</receiver>

 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
@OPermission_REQUEST_INSTALL_PACKAGES
@OPermission_READ_INSTALL_SESSIONS
@OPermission_REPLACE_EXISTING_PACKAGE
open class BaseDownloadInstallBroadcastReceiver(private val _strPathNameApk: String) : BaseBroadcastReceiver() {

    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            CDownloadManager.ACTION_DOWNLOAD_COMPLETE ->  UtilKAppInstall.install_ofView(_strPathNameApk)
        }
    }
}