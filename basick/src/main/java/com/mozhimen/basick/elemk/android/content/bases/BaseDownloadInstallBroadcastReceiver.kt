package com.mozhimen.basick.elemk.android.content.bases

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.mozhimen.basick.elemk.android.app.cons.CDownloadManager
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.android.content.UtilKAppInstall


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
 * @Date 2022/6/13 12:04
 * @Version 1.0
 */
@AManifestKRequire(/*CPermission.INSTALL_PACKAGES,*/ CPermission.REQUEST_INSTALL_PACKAGES, CPermission.READ_INSTALL_SESSIONS, CPermission.REPLACE_EXISTING_PACKAGE)
open class BaseDownloadInstallBroadcastReceiver(private val _strPathNameApk: String) : BaseBroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            CDownloadManager.ACTION_DOWNLOAD_COMPLETE ->  UtilKAppInstall.installHand(_strPathNameApk)
        }
    }
}