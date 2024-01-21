package com.mozhimen.basicktest.utilk.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.utilk.android.content.UtilKAppInstall
import com.mozhimen.basick.utilk.kotlin.UtilKStrAsset
import com.mozhimen.basick.utilk.kotlin.UtilKStrPath
import com.mozhimen.basick.utilk.kotlin.isFileExist

/**
 * @ClassName UtilKAppInstallActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/17 19:46
 * @Version 1.0
 */
class UtilKAppInstallActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val path = "${UtilKStrPath.Absolute.Internal.getCache()}/lelejoy.apk"
        UtilKStrAsset.strAssetName2file("lelejoy.apk", path)
        if (path.isFileExist()) {
            UtilKAppInstall.installSilence(path, InstallKReceiver::class.java)
        }
    }
}