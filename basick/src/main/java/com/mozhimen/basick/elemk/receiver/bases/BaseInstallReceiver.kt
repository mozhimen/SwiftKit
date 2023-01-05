package com.mozhimen.basick.elemk.receiver.bases

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.CallSuper
import androidx.core.content.FileProvider
import com.mozhimen.basick.elemk.annors.ADescription
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.basick.utilk.UtilKFile
import java.io.File


/**
 * @ClassName LoadKReceiverInstall
 * @Description if you use it, you must register in manifest first
 *

<receiver android:name=".InstallReceiver"
android:exported="false">	<!-- 进入InsatllApkBroadcast.java -->
<intent-filter>
<action android:name="android.intent.action.DOWNLOAD_COMPLETE" />
<action android:name="android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED"/>
</intent-filter>
</receiver>

<provider
android:name="androidx.core.content.FileProvider"
android:authorities="包名.fileprovider"
android:exported="false"
android:grantUriPermissions="true">
<meta-data
android:name="android.support.FILE_PROVIDER_PATHS"
android:resource="@xml/file_paths"  />
</provider>

 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/13 12:04
 * @Version 1.0
 */
@APermissionK(Manifest.permission.REQUEST_INSTALL_PACKAGES)
@ADescription(
    "    <uses-permission android:name=\"android.permission.REPLACE_EXISTING_PACKAGE\"/>\n",
    "    <uses-permission android:name=\"android.permission.REQUEST_INSTALL_PACKAGES\"/><!-- 8.0必要权限  -->\n"
)
open class BaseInstallReceiver(private val _filePathWithName: String, private val _packageName: String) : BroadcastReceiver() {
    private val TAG = "${this::class.java.simpleName}>>>>>"

    override fun onReceive(context: Context, intent: Intent) {
        onReceiveInstall(context)
    }

    @CallSuper
    fun onReceiveInstall(context: Context) {
        val installIntent = Intent()
        installIntent.action = Intent.ACTION_VIEW
        installIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK        //在Boradcast中启动活动需要添加Intent.FLAG_ACTIVITY_NEW_TASK
        val file = File(_filePathWithName) //缓存路径
        if (UtilKFile.isFileExist(file)) {          //判断下载的apk是否存在  存在true  不存在false
            Log.d(TAG, "onReceiveInstall: isFileExist true")
        } else {
            Log.e(TAG, "onReceiveInstall: isFileExist false")
        }
        val type = "application/vnd.android.package-archive" //安装路径
        try {
            if (Build.VERSION.SDK_INT >= 24) {   //判断安卓系统是否大于7.0  大于7.0使用以下方法
                val uri: Uri = FileProvider.getUriForFile(context, "$_packageName.fileprovider", file) //转为URI格式  第二个参数为AndroidManifest.xml中定义的authorities的值
                installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                installIntent.setDataAndType(uri, type)
            } else {
                installIntent.setDataAndType(Uri.fromFile(file), type) //存储位置为Android/data/包名/file/Download文件夹
            }
            context.startActivity(installIntent) //启动生命周期
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}