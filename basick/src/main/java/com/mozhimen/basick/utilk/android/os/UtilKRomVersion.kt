package com.mozhimen.basick.utilk.android.os

import android.annotation.SuppressLint
import com.mozhimen.basick.elemk.cons.CPackage
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.kotlin.strPackage2clazz
import java.lang.reflect.Method

/**
 * @ClassName UtilKRomVersion
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 16:13
 * @Version 1.0
 */
object UtilKRomVersion : BaseUtilK() {

    @JvmStatic
    @SuppressLint("PrivateApi")
    fun isMIUIAfter6(): Boolean {
        return try {
            val clazz = "android.os.SystemProperties".strPackage2clazz()
            val method: Method = clazz.getMethod("get", String::class.java)
            var value = method.invoke(null, CPackage.RO_MIUI_UI_VERSION_NAME) as String
            value = value.replace("[vV]".toRegex(), "")
            val version = value.toInt()
            version >= 6
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
            false
        }
    }

    /**
     * colorOS是否大于3
     * @return Boolean
     */
    @JvmStatic
    @SuppressLint("PrivateApi")
    fun isColorOSAfter3(): Boolean {
        return try {
            val clazz = "android.os.SystemProperties".strPackage2clazz()
            val method = clazz.getMethod("get", String::class.java)
            var value = method.invoke(null, CPackage.RO_BUILD_VERSION_OPPOROM) as String
            value = value.replace("[vV]".toRegex(), "")
            value = value.substring(0, 1)
            val version = value.toInt()
            version >= 3
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
            false
        }
    }
}