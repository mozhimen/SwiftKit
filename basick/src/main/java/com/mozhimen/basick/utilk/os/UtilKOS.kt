package com.mozhimen.basick.utilk.os

import android.annotation.SuppressLint
import android.os.Build
import android.text.TextUtils
import android.util.Log
import com.mozhimen.basick.utilk.log.et

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.reflect.Method
import java.util.*

/**
 * @ClassName OSUtil
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:12
 * @Version 1.0
 */
object UtilKOS {
    private const val TAG = "UtilKOS>>>>>"

    private const val ROM_EMUI = "EMUI"
    private const val ROM_MIUI = "MIUI"
    private const val ROM_FLYME = "FLYME"
    private const val ROM_OPPO = "OPPO"
    private const val ROM_SMARTISAN = "SMARTISAN"
    private const val ROM_VIVO = "VIVO"
    private const val ROM_QIKU = "QIKU"

    private const val KEY_VERSION_MIUI = "ro.miui.ui.version.name"
    private const val KEY_VERSION_EMUI = "ro.build.version.emui"
    private const val KEY_VERSION_OPPO = "ro.build.version.opporom"
    private const val KEY_VERSION_SMARTISAN = "ro.smartisan.version"
    private const val KEY_VERSION_VIVO = "ro.vivo.os.version"

    private var _name: String? = null
    private var _version: String? = null

    /**
     * 是否是Emui系统
     * @return Boolean
     */
    @JvmStatic
    fun isEmui(): Boolean {
        return check(ROM_EMUI)
    }

    /**
     * 是否是Miui系统
     * @return Boolean
     */
    @JvmStatic
    fun isMiui(): Boolean {
        return check(ROM_MIUI)
    }

    /**
     * Miui版本
     * @return Boolean
     */
    @JvmStatic
    @SuppressLint("PrivateApi")
    fun isMIUILarger6(): Boolean {
        return try {
            val clazz = Class.forName("android.os.SystemProperties")
            val method: Method = clazz.getMethod("get", String::class.java)
            var value = method.invoke(null, KEY_VERSION_MIUI) as String
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
     * 是否是Vivo系统
     * @return Boolean
     */
    @JvmStatic
    fun isVivo(): Boolean {
        return check(ROM_VIVO)
    }

    /**
     * 是否是Oppo系统
     * @return Boolean
     */
    @JvmStatic
    fun isOppo(): Boolean {
        return check(ROM_OPPO)
    }

    /**
     * colorOS是否大于3
     * @return Boolean
     */
    @JvmStatic
    @SuppressLint("PrivateApi")
    fun isColorOSLarger3(): Boolean {
        return try {
            val clazz = Class.forName("android.os.SystemProperties")
            val method = clazz.getMethod("get", String::class.java)
            var value = method.invoke(null, KEY_VERSION_OPPO) as String
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

    /**
     * 是否是Flyme系统
     * @return Boolean
     */
    @JvmStatic
    fun isFlyme(): Boolean {
        return check(ROM_FLYME)
    }

    /**
     * 是否是360系统
     * @return Boolean
     */
    @JvmStatic
    fun is360(): Boolean {
        return check(ROM_QIKU) || check("360")
    }

    /**
     * 是否是Smartisan系统
     * @return Boolean
     */
    @JvmStatic
    fun isSmartisan(): Boolean {
        return check(ROM_SMARTISAN)
    }

    private fun check(rom: String): Boolean {
        _name?.let { return _name == rom }

        if (!TextUtils.isEmpty(UtilKShell.getProp(KEY_VERSION_MIUI).also { _version = it })) {
            _name = ROM_MIUI
        } else if (!TextUtils.isEmpty(UtilKShell.getProp(KEY_VERSION_EMUI).also { _version = it })) {
            _name = ROM_EMUI
        } else if (!TextUtils.isEmpty(UtilKShell.getProp(KEY_VERSION_OPPO).also { _version = it })) {
            _name = ROM_OPPO
        } else if (!TextUtils.isEmpty(UtilKShell.getProp(KEY_VERSION_VIVO).also { _version = it })) {
            _name = ROM_VIVO
        } else if (!TextUtils.isEmpty(UtilKShell.getProp(KEY_VERSION_SMARTISAN).also { _version = it })) {
            _name = ROM_SMARTISAN
        } else {
            _version = Build.DISPLAY
            if (_version?.uppercase(Locale.getDefault())!!.contains(ROM_FLYME)) {
                _name = ROM_FLYME
            } else {
                _version = Build.UNKNOWN
                _name = Build.MANUFACTURER.uppercase(Locale.ROOT)
            }
        }
        return _name == rom
    }
}