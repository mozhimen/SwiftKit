package com.mozhimen.abilityk.hotfixk

import android.os.Environment
import android.util.Log
import com.mozhimen.basick.cachek.CacheKSP
import com.mozhimen.basick.logk.LogK
import com.mozhimen.basick.utilk.UtilKFile
import com.mozhimen.basick.utilk.UtilKGlobal
import java.io.File
import java.lang.reflect.InvocationTargetException

/**
 * @ClassName HotFixMgr
 * @Description android:requestLegacyExternalStorage="true" application 设置
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/7/20 17:42
 * @Version 1.0
 */
class HotFixMgr {

    companion object {
        private const val TAG = "HotFixKMgr>>>>>"
        const val HOTFIXK_SP_NAME = "hotfixk_sp_name"
        const val SP_DEX_VERSION = "dex_version"

        @JvmStatic
        val instance = HotFixKMgrHolder.holder
    }

    private object HotFixKMgrHolder {
        val holder = HotFixMgr()
    }

    private val _context = UtilKGlobal.instance.getApp()!!

    /**
     * 需要在application中设置: android:requestLegacyExternalStorage="true"
     * @param dexVersion Long
     * @param dexPath File
     * @param dexName String
     */
    fun startFix(dexVersion: Long, dexPath: File = Environment.getExternalStorageDirectory(), dexName: String = "patch.dex") {
        val oldDexVersion = CacheKSP.instance.with(HOTFIXK_SP_NAME).getLong(SP_DEX_VERSION)
        if (dexVersion <= oldDexVersion) {
            Log.e(TAG, "startFix: fix exit because version is old")
            return
        }
        val sourceFile = File(dexPath, dexName)
        val destFileFile = File(_context.applicationInfo.dataDir, dexName)
        UtilKFile.copyFile(sourceFile, destFileFile)

        //startFix
        try {
            HotFixK.fix(_context, destFileFile.absolutePath)
        } catch (e: IllegalAccessException) {
            LogK.et(TAG, "startFix: IllegalAccessException ${e.message}")
            e.printStackTrace()
            return
        } catch (e: NoSuchFieldException) {
            LogK.et(TAG, "startFix: NoSuchFieldException ${e.message}")
            e.printStackTrace()
            return
        } catch (e: InvocationTargetException) {
            LogK.et(TAG, "startFix: InvocationTargetException ${e.message}")
            e.printStackTrace()
            return
        } catch (e: NoSuchMethodException) {
            LogK.et(TAG, "startFix: NoSuchMethodException ${e.message}")
            e.printStackTrace()
            return
        } catch (e: Throwable) {
            LogK.et(TAG, "startFix: Throwable ${e.message}")
            e.printStackTrace()
            return
        }

        CacheKSP.instance.with(HOTFIXK_SP_NAME).putLong(SP_DEX_VERSION, dexVersion)
        LogK.dt(TAG, "startFix: dex fix finished, current dex version is $dexVersion")
    }
}