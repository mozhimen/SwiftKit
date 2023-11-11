package com.mozhimen.componentk.netk.app.install

import com.mozhimen.basick.utilk.android.content.UtilKAppInstall
import java.io.File

/**
 * @ClassName AppInstallManager
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/8 17:21
 * @Version 1.0
 */
object NetKAppInstallManager {
    @JvmStatic
    fun installApk(fileApk: File) {
        UtilKAppInstall.installHand(fileApk)
    }
}