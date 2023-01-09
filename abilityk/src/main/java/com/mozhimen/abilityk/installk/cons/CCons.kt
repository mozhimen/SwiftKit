package com.mozhimen.abilityk.installk.cons

import android.Manifest


/**
 * @ClassName CCons
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/9 14:45
 * @Version 1.0
 */
object CCons {
    const val MSG_INSTALL_START = 0
    const val MSG_INSTALL_FINISH = 1
    const val MSG_INSTALL_ERROR = 2
    const val MSG_NEED_OPEN_ACCESSIBILITY_SETTING = 2
    const val MSG_NEED_PERMISSIONS = 3

    val PERMISSIONS = arrayOf(Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
}