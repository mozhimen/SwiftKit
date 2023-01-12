package com.mozhimen.basick.utilk.bar

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.manifestk.annors.AManifestKRequire


/**
 * @ClassName UtilKDialog
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/11 12:31
 * @Version 1.0
 */
@AManifestKRequire(CPermission.SYSTEM_ALERT_WINDOW)
object UtilKDialog {
    private val TAG = "UtilKDialog>>>>>"

    /**
     * 是否有Overlay的权限
     * @param context Context
     * @return Boolean
     */
    @JvmStatic
    fun isOverlayPermissionEnable(context: Context): Boolean {
        return Build.VERSION.SDK_INT < CVersionCode.V_23_6_M || Settings.canDrawOverlays(context)
    }

    /**
     * 关闭Android9.0弹出框（Detected problems with API compatibility）
     */
    @RequiresApi(CVersionCode.V_28_9_P)
    @JvmStatic
    fun closeDialogAtAndroidP() {
        try {
            val clazz = Class.forName("android.content.pm.PackageParser\$Package")
            val declaredConstructor = clazz.getDeclaredConstructor(String::class.java)
            declaredConstructor.isAccessible = true

            val clazz1 = Class.forName("android.app.ActivityThread")
            val declaredMethod = clazz1.getDeclaredMethod("currentActivityThread")
            declaredMethod.isAccessible = true
            val activityThread = declaredMethod.invoke(null)
            val hiddenApiWarningShown = clazz1.getDeclaredField("mHiddenApiWarningShown")
            hiddenApiWarningShown.isAccessible = true
            hiddenApiWarningShown.setBoolean(activityThread, true)
        } catch (e: Exception) {
            Log.e(TAG, "closeAndroidPDialog Exception ${e.message}")
            e.printStackTrace()
        }
    }
}