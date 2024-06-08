package com.mozhimen.basick.elemk.android.provider.cons

import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName CSettings
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
object CSettings {
    @RequiresApi(CVersCode.V_23_6_M)
    const val ACTION_MANAGE_OVERLAY_PERMISSION = Settings.ACTION_MANAGE_OVERLAY_PERMISSION

    @RequiresApi(CVersCode.V_30_11_R)
    const val ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION =
        Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION

    @RequiresApi(CVersCode.V_26_8_O)
    const val ACTION_MANAGE_UNKNOWN_APP_SOURCES = Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES

    @RequiresApi(CVersCode.V_26_8_O)
    const val ACTION_APP_NOTIFICATION_SETTINGS = Settings.ACTION_APP_NOTIFICATION_SETTINGS

    const val ACTION_ACCESSIBILITY_SETTINGS = Settings.ACTION_ACCESSIBILITY_SETTINGS
    const val ACTION_APPLICATION_DETAILS_SETTINGS = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    const val ACTION_LOCATION_SOURCE_SETTINGS = Settings.ACTION_LOCATION_SOURCE_SETTINGS

    @RequiresApi(CVersCode.V_30_11_R)
    const val ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION =
        Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION

    @RequiresApi(CVersCode.V_30_11_R)
    const val ACTION_BIOMETRIC_ENROLL =
        Settings.ACTION_BIOMETRIC_ENROLL

    ///////////////////////////////////////////////////////////////////////////////////////

    @RequiresApi(CVersCode.V_30_11_R)
    const val EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED = Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED

    @RequiresApi(CVersCode.V_26_8_O)
    const val EXTRA_APP_PACKAGE = Settings.EXTRA_APP_PACKAGE

    ///////////////////////////////////////////////////////////////////////////////////////

    object Secure {
        const val ACCESSIBILITY_ENABLED = Settings.Secure.ACCESSIBILITY_ENABLED
        const val ENABLED_ACCESSIBILITY_SERVICES = Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        const val LOCATION_MODE_OFF = Settings.Secure.LOCATION_MODE_OFF
        const val LOCATION_PROVIDERS_ALLOWED = Settings.Secure.LOCATION_PROVIDERS_ALLOWED
        const val LOCATION_MODE = Settings.Secure.LOCATION_MODE
        const val ANDROID_ID = Settings.Secure.ANDROID_ID
    }
}