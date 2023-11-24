package com.mozhimen.underlayk.crashk

import com.mozhimen.basick.BuildConfig
import com.mozhimen.basick.elemk.cons.CMsg
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.stackk.cb.StackKCb
import com.mozhimen.basick.utilk.android.app.UtilKActivityManager
import com.mozhimen.basick.utilk.android.content.UtilKApp
import com.mozhimen.basick.utilk.android.content.UtilKPackage
import com.mozhimen.basick.utilk.android.hardware.UtilKDevice
import com.mozhimen.basick.utilk.android.os.UtilKBuild
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFile
import com.mozhimen.basick.utilk.java.lang.UtilKCurrentThread
import com.mozhimen.basick.utilk.java.util.UtilKDate
import com.mozhimen.basick.utilk.kotlin.UtilKStrPath
import com.mozhimen.basick.utilk.kotlin.UtilKStringFormat
import com.mozhimen.basick.utilk.kotlin.createFolder
import com.mozhimen.basick.utilk.kotlin.getFolderFiles
import com.mozhimen.basick.utilk.kotlin.throwable2printWriter
import com.mozhimen.underlayk.crashk.commons.ICrashK
import com.mozhimen.underlayk.crashk.commons.ICrashKListener
import com.mozhimen.underlayk.logk.etk
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter

/**
 * @ClassName CrashKJava
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/27 16:27
 * @Version 1.0
 */
@OptInApiInit_InApplication
@AManifestKRequire(CPermission.READ_PHONE_STATE, CPermission.READ_PRIVILEGED_PHONE_STATE)
class CrashKJava : BaseUtilK(), ICrashK {

    private var _crashKListener: ICrashKListener? = null
    private var _isRestart = true

    var crashPathJava: String? = null
        get() {
            if (field != null) return field
            val crashFullPath = UtilKStrPath.Absolute.Internal.getCache() + "/crashk_java"
            crashFullPath.createFolder()
            return crashFullPath.also { field = it }
        }

    fun setEnableRestart(isRestart: Boolean): CrashKJava {
        _isRestart = isRestart
        return this
    }

    override fun init(listener: ICrashKListener?) {
        listener?.let { this._crashKListener = it }
        Thread.setDefaultUncaughtExceptionHandler(CrashKUncaughtExceptionHandler(_isRestart))
    }

    override fun getCrashFiles(): Array<File> =
        crashPathJava!!.getFolderFiles()

    ////////////////////////////////////////////////////////////////////////////////////////

    @OptInApiInit_InApplication
    private inner class CrashKUncaughtExceptionHandler(private val _isRestart: Boolean = true) : Thread.UncaughtExceptionHandler {
        private val _defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        private val _launchTime = UtilKDate.getNowStr()

        override fun uncaughtException(t: Thread, e: Throwable) {
            if (!handleException(e) && _defaultExceptionHandler != null) {
                _defaultExceptionHandler.uncaughtException(t, e)
            }
            if (_isRestart) {
                Thread.sleep(2000)
                UtilKApp.restartApp(isKillProcess = true, isValid = false)
            }
        }

        /**
         * 设备类型、0S本版、线程名、前后台、使用时长、App版本、升级渠道
         * CPU架构、内存信息、存储信息、permission权限
         * @param e Throwable
         * @return Boolean
         */
        private fun handleException(e: Throwable): Boolean {
            val crashLog = collectDeviceInfoAndCrash(e)
            if (BuildConfig.DEBUG)
                e.printStackTrace()
            "UncaughtExceptionHandler handleException log $crashLog".etk(TAG)

            ///////////////////////////////////////////////////////////////////////////////

            _crashKListener?.onGetCrashLog(crashLog)

            ///////////////////////////////////////////////////////////////////////////////

            saveCrashLog2File(crashLog)
            return true
        }

        private fun saveCrashLog2File(log: String) {
            val savePath = crashPathJava + "/${UtilKFile.getStrFileNameForStrNowDate()}.txt"
            UtilKStringFormat.str2file(log, savePath)
        }

        private fun collectDeviceInfoAndCrash(e: Throwable): String {
            val stringBuilder = StringBuilder()

            //device info
            stringBuilder.append(CMsg.LINE_BREAK).append(CMsg.PART_LINE_BIAS).append(CMsg.LINE_BREAK)
            stringBuilder.append("brand= ${UtilKBuild.getBrand()}").append(CMsg.LINE_BREAK)//手机品牌
            stringBuilder.append("cpu_arch= ${UtilKBuild.getSupportABIs()}").append(CMsg.LINE_BREAK)//CPU架构
            stringBuilder.append("model= ${UtilKBuild.getModel()}").append(CMsg.LINE_BREAK)//手机系列
            stringBuilder.append("rom= ${UtilKDevice.getRomVersion()}").append(CMsg.LINE_BREAK)//rom
            stringBuilder.append("os= ${UtilKBuild.getVersionRelease()}").append(CMsg.LINE_BREAK)//API版本:9.0
            stringBuilder.append("sdk= ${UtilKBuild.getVersionSDKStr()}").append(CMsg.LINE_BREAK)//SDK版本:31
            stringBuilder.append("launch_time= $_launchTime").append(CMsg.LINE_BREAK)//启动APP的时间
            stringBuilder.append("crash_time= ${UtilKDate.getNowStr()}").append(CMsg.LINE_BREAK)//crash发生的时间
            stringBuilder.append("foreground= ${StackKCb.instance.isFront()}").append(CMsg.LINE_BREAK)//应用处于前台
            stringBuilder.append("thread= ${UtilKCurrentThread.getName()}").append(CMsg.LINE_BREAK)//异常线程名

            //app info
            stringBuilder.append("version_code= ${UtilKPackage.getVersionCode()}").append(CMsg.LINE_BREAK)
            stringBuilder.append("version_name= ${UtilKPackage.getVersionName()}").append(CMsg.LINE_BREAK)
            stringBuilder.append("package_code= ${UtilKPackage.getPackageName()}").append(CMsg.LINE_BREAK)
            stringBuilder.append("requested_permissions= ${UtilKPackage.getRequestedPermissionsStr()}").append(CMsg.LINE_BREAK)

            //storage info
            val memoryInfo = UtilKActivityManager.getMemoryInfo(_context)
            stringBuilder.append("availableMemory= ${UtilKActivityManager.getAvailMemSizeStr(memoryInfo)}").append(CMsg.LINE_BREAK)//可用内存
            stringBuilder.append("totalMemory= ${UtilKActivityManager.getTotalMenSizeStr(memoryInfo)}").append(CMsg.LINE_BREAK)//设备总内存

            //sd storage size
            try {
                stringBuilder.append("availableStorage= ${UtilKDevice.getFreeExternalMemorySize()}").append(CMsg.LINE_BREAK)//存储空间
            } catch (_: Exception) {
            }
            stringBuilder.append(CMsg.PART_LINE_BIAS).append(CMsg.LINE_BREAK)

            //stack info
            val stringWriter = StringWriter()
            val printWriter = PrintWriter(stringWriter)
            e.throwable2printWriter(printWriter)
//            e.printStackTrace(printWriter)
//            var cause = e.cause
//            while (cause != null) {
//                cause.printStackTrace(printWriter)
//                cause = cause.cause
//            }
//            printWriter.close()
            stringBuilder.append(stringWriter.toString())
            return stringBuilder.toString()
        }
    }
}