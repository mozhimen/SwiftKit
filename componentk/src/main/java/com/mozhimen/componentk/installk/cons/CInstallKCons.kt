package com.mozhimen.componentk.installk.cons

import com.mozhimen.basick.manifestk.cons.CPermission

/**
 * @ClassName CCons
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/9 14:45
 * @Version 1.0
 */
object CInstallKCons {
    val PERMISSIONS = arrayOf(
        CPermission.INTERNET,
        CPermission.READ_EXTERNAL_STORAGE,
        CPermission.WRITE_EXTERNAL_STORAGE
    )

    const val MSG_DOWNLOAD_START = -1
    const val MSG_INSTALL_START = 0
    const val MSG_INSTALL_FINISH = 1
    const val MSG_INSTALL_FAIL = 2
    const val MSG_NEED_PERMISSION = 3
}