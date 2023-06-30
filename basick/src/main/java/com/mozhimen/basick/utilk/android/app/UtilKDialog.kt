package com.mozhimen.basick.utilk.android.app

import android.annotation.SuppressLint
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.cons.CVersCode
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.java.lang.UtilKReflect
import com.mozhimen.basick.utilk.kotlin.packageStr2Clazz


/**
 * @ClassName UtilKDialog
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/11 12:31
 * @Version 1.0
 */
@AManifestKRequire(CPermission.SYSTEM_ALERT_WINDOW)
object UtilKDialog : BaseUtilK() {

    /**
     * 关闭Android9.0弹出框（Detected problems with API compatibility）
     */
    @SuppressLint("PrivateApi", "DiscouragedPrivateApi", "SoonBlockedPrivateApi")
    @RequiresApi(CVersCode.V_28_9_P)
    @JvmStatic
    fun closeDialogAtP() {
        try {
            val declaredConstructor = "android.content.pm.PackageParser\$Package".packageStr2Clazz().getDeclaredConstructor(String::class.java)
            declaredConstructor.isAccessible = true

            val activityThreadClazz = "android.app.ActivityThread".packageStr2Clazz()
            val declaredMethod = activityThreadClazz.getDeclaredMethod("currentActivityThread")
            declaredMethod.isAccessible = true
            val activityThread = declaredMethod.invoke(null)
            val hiddenApiWarningShown = activityThreadClazz.getDeclaredField("mHiddenApiWarningShown")
            hiddenApiWarningShown.isAccessible = true
            hiddenApiWarningShown.setBoolean(activityThread, true)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
    }
}