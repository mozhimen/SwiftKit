package com.mozhimen.swiftmk.helper.os

import android.util.Log
import java.lang.Exception

/**
 * @ClassName CompatDetector
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/20 17:36
 * @Version 1.0
 */
object CompatDetector {
    private const val tag = "CompatDetector"

    /**
     * 关闭Android9.0弹出框（Detected problems with API compatibility）
     */
    fun closeAndroidPDialog() {
        try {
            val cls = Class.forName("android.content.pm.PackageParser\$Package")
            val declaredConstructor = cls.getDeclaredConstructor(String.javaClass)
            declaredConstructor.isAccessible = true
        } catch (e: Exception) {
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
            Log.e(tag, e.message.toString())
        }
    }
}