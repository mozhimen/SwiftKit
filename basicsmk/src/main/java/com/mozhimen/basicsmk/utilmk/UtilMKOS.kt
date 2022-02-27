package com.mozhimen.basicsmk.utilmk

import android.os.Build
import android.text.TextUtils
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * @ClassName OSUtil
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:12
 * @Version 1.0
 */
object UtilMKOS {
    private const val TAG = "UtilMKOS>>>>>"

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

    private var xName: String? = null
    private var xVersion: String? = null

    fun isEmui(): Boolean {
        return check(ROM_EMUI)
    }

    fun isMiui(): Boolean {
        return check(ROM_MIUI)
    }

    fun isVivo(): Boolean {
        return check(ROM_VIVO)
    }

    fun isOppo(): Boolean {
        return check(ROM_OPPO)
    }

    fun isFlyme(): Boolean {
        return check(ROM_FLYME)
    }

    fun is360(): Boolean {
        return check(ROM_QIKU) || check("360")
    }

    fun isSmartisan(): Boolean {
        return check(ROM_SMARTISAN)
    }

    private fun check(rom: String): Boolean {
        xName?.let { return xName == rom }

        if (!TextUtils.isEmpty(getProp(KEY_VERSION_MIUI).also { xVersion = it })) {
            xName = ROM_MIUI
        } else if (!TextUtils.isEmpty(getProp(KEY_VERSION_EMUI).also { xVersion = it })) {
            xName = ROM_EMUI
        } else if (!TextUtils.isEmpty(getProp(KEY_VERSION_OPPO).also { xVersion = it })) {
            xName = ROM_OPPO
        } else if (!TextUtils.isEmpty(getProp(KEY_VERSION_VIVO).also { xVersion = it })) {
            xName = ROM_VIVO
        } else if (!TextUtils.isEmpty(getProp(KEY_VERSION_SMARTISAN).also { xVersion = it })) {
            xName = ROM_SMARTISAN
        } else {
            xVersion = Build.DISPLAY
            if (xVersion?.toUpperCase()!!.contains(ROM_FLYME)) {
                xName = ROM_FLYME
            } else {
                xVersion = Build.UNKNOWN
                xName = Build.MANUFACTURER.toUpperCase()
            }
        }
        return xName == rom
    }

    private fun getProp(name: String): String? {
        val line: String
        var input: BufferedReader? = null
        try {
            val process = Runtime.getRuntime().exec("getprop $name")
            input = BufferedReader(InputStreamReader(process.inputStream), 1024)
            line = input.readLine()
            input.close()
        } catch (ex: IOException) {
            Log.e(TAG, "Unable to read prop $name", ex)
            return null
        } finally {
            input?.let {
                try {
                    input.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return line
    }
}