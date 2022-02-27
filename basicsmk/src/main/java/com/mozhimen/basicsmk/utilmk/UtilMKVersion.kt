package com.mozhimen.basicsmk.utilmk

import android.annotation.SuppressLint
import android.util.Log
import java.lang.Exception

/**
 * @ClassName UtilMKVersion
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 18:16
 * @Version 1.0
 */
object UtilMKVersion {
    private const val TAG = "UtilMKVersion>>>>>"

    /**
     * 关闭Android9.0弹出框（Detected problems with API compatibility）
     */
    @SuppressLint("PrivateApi", "SoonBlockedPrivateApi", "DiscouragedPrivateApi")
    fun closeAndroidPDialog() {
        try {
            val cls = Class.forName("android.content.pm.PackageParser\$Package")
            val declaredConstructor = cls.getDeclaredConstructor(String.javaClass)
            declaredConstructor.isAccessible = true
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("CompatDetector", e.message.toString())
        }

        try {
            val cls = Class.forName("android.app.ActivityThread")
            val declaredMethod = cls.getDeclaredMethod("currentActivityThread")
            declaredMethod.isAccessible = true
            val activityThread = declaredMethod.invoke(null)
            val mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown")
            mHiddenApiWarningShown.isAccessible = true
            mHiddenApiWarningShown.setBoolean(activityThread, true)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, e.message.toString())
        }
    }
}