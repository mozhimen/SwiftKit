package com.mozhimen.basick.elemk.android.provider.cons

import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName CSettings
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/3 10:48
 * @Version 1.0
 */
object CSettings {
    @RequiresApi(CVersCode.V_23_6_M)
    const val ACTION_MANAGE_OVERLAY_PERMISSION = Settings.ACTION_MANAGE_OVERLAY_PERMISSION

    @RequiresApi(CVersCode.V_30_11_R)
    const val ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION

    @RequiresApi(CVersCode.V_26_8_O)
    const val ACTION_MANAGE_UNKNOWN_APP_SOURCES = Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES

    @RequiresApi(CVersCode.V_26_8_O)
    const val EXTRA_APP_PACKAGE = Settings.EXTRA_APP_PACKAGE

    @RequiresApi(CVersCode.V_26_8_O)
    const val ACTION_APP_NOTIFICATION_SETTINGS = Settings.ACTION_APP_NOTIFICATION_SETTINGS

    const val ACTION_ACCESSIBILITY_SETTINGS = Settings.ACTION_ACCESSIBILITY_SETTINGS
    const val ACTION_APPLICATION_DETAILS_SETTINGS = Settings.ACTION_APPLICATION_DETAILS_SETTINGS



    object Secure {
        const val ACCESSIBILITY_ENABLED = Settings.Secure.ACCESSIBILITY_ENABLED
        const val ENABLED_ACCESSIBILITY_SERVICES = Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
    }
}