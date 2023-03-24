package com.mozhimen.basick.utilk.view.bar

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.exts.et


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
     * 关闭Android9.0弹出框（Detected problems with API compatibility）
     */
    @SuppressLint("PrivateApi")
    @RequiresApi(CVersionCode.V_28_9_P)
    @JvmStatic
    fun closeDialogAtP() {
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
            Log.e(TAG, "closeDialogAtP Exception ${e.message}")
            e.printStackTrace()
            e.message?.et(TAG)
        }
    }
}