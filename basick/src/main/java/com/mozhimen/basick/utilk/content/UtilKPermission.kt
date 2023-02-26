package com.mozhimen.basick.utilk.content

import android.content.Context
import android.provider.Settings
import android.text.TextUtils
import android.util.Log

/**
 * @ClassName UtilKPermission
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/26 23:06
 * @Version 1.0
 */
object UtilKPermission {
    private const val TAG = "UtilKPermission>>>>>"

    /**
     * 是否有无障碍权限
     * @param context Context
     * @return Boolean
     */
    @JvmStatic
    fun isAccessibilityPermissionEnable(context: Context, serviceClazz: Class<*>): Boolean {
        var permissionEnable = 0
        val service = "${context.packageName}/${serviceClazz.canonicalName}"
        try {
            permissionEnable = Settings.Secure.getInt(context.applicationContext.contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED)
            Log.d(TAG, "isSettingAccessibilityPermissionEnable permissionEnable $permissionEnable")
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
            Log.e(TAG, "isSettingAccessibilityPermissionEnable error finding setting, default accessibility to not found ${e.message}")
        }
        val stringColonSplitter = TextUtils.SimpleStringSplitter(':')
        if (permissionEnable == 1) {
            Log.d(TAG, "isSettingAccessibilityPermissionEnable accessibility is enabled")
            val settingValue = Settings.Secure.getString(context.applicationContext.contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
            if (settingValue != null) {
                stringColonSplitter.setString(settingValue)
                while (stringColonSplitter.hasNext()) {
                    val accessibilityService = stringColonSplitter.next()
                    Log.v(TAG, "isSettingAccessibilityPermissionEnable accessibilityService $accessibilityService - service $service")
                    if (accessibilityService.equals(service, ignoreCase = true)) {
                        Log.i(TAG, "isSettingAccessibilityPermissionEnable we've found the correct setting - accessibility is switched on!")
                        return true
                    }
                }
            } else {

            }
        } else {
            Log.e(TAG, "isSettingAccessibilityPermissionEnable accessibility is disabled")
        }
        return false
    }
}