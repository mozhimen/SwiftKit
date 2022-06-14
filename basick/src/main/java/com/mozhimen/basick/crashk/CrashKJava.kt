package com.mozhimen.basick.crashk

import android.app.ActivityManager
import android.content.Context
import android.text.format.Formatter
import com.mozhimen.basick.BuildConfig
import com.mozhimen.basick.crashk.commons.ICrashKListener
import com.mozhimen.basick.logk.LogK
import com.mozhimen.basick.stackk.StackK
import com.mozhimen.basick.utilk.*
import java.io.*
import java.util.*

/**
 * @ClassName CrashKJava
 * @Description <uses-permission
android:name="android.permission.WRITE_EXTERNAL_STORAGE"
tools:ignore="ScopedStorage" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/27 16:27
 * @Version 1.0
 */
object CrashKJava {
    private const val CRASHK_JAVA_DIR = "crashk_java"

    private const val TAG = "CrashKJava>>>>>"
    private var _crashFullPath: String = getJavaCrashDir().absolutePath
    private var _crashListener: ICrashKListener? = null

    fun init(crashKListener: ICrashKListener) {
        this._crashListener = crashKListener
        Thread.setDefaultUncaughtExceptionHandler(CrashKUncaughtExceptionHandler())
    }

    fun getJavaCrashFiles(): Array<File> {
        return File(_crashFullPath).listFiles() ?: emptyArray()
    }

    private fun getJavaCrashDir(): File {
        val javaCrashFile = File(UtilKGlobal.instance.getApp()!!.cacheDir, CRASHK_JAVA_DIR)
        if (!javaCrashFile.exists()) {
            javaCrashFile.mkdirs()
        }
        return javaCrashFile
    }

    private class CrashKUncaughtExceptionHandler : Thread.UncaughtExceptionHandler {
        private val _context = UtilKGlobal.instance.getApp()!!
        private val _defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        private val _launchTime = UtilKDate.date2String(Date(), UtilKDate.FORMAT_yyyyMMddHHmmss)

        override fun uncaughtException(t: Thread, e: Throwable) {
            if (!handleException(e) && _defaultExceptionHandler != null) {
                _defaultExceptionHandler.uncaughtException(t, e)
            }
            UtilKApp.restartApp(false)
        }

        /**
         * 设备类型、0S本版、线程名、前后台、使用时长、App版本、升级渠道
         * CPU架构、内存信息、存储信息、permission权限
         * @param e Throwable
         * @return Boolean
         */
        private fun handleException(e: Throwable): Boolean {
            val log = collectDeviceInfo(e)
            if (BuildConfig.DEBUG) {
                LogK.et(TAG, "UncaughtExceptionHandler handleException log $log")
            }

            _crashListener?.onGetCrashJava(log)

            saveCrashInfo2File(log)
            return true
        }

        private fun saveCrashInfo2File(log: String) {
            val crashDir = File(_crashFullPath)
            if (!crashDir.exists()) {
                crashDir.mkdirs()
            }
            val crashkFile = File(crashDir, "crashk_" + UtilKDate.date2String(Date(), UtilKDate.FORMAT_yyyyMMddHHmmss + ".txt"))
            crashkFile.createNewFile()
            val fos = FileOutputStream(crashkFile)

            try {
                fos.write(log.toByteArray())
                fos.flush()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                fos.close()
            }
        }

        private fun collectDeviceInfo(e: Throwable): String {
            val stringBuilder = StringBuilder()
            //device info
            stringBuilder.append("brand= ${UtilKBuild.getBrand()}\n")//手机品牌
            stringBuilder.append("cpu_arch= ${UtilKBuild.getSupportABIs()}")//CPU架构
            stringBuilder.append("model= ${UtilKBuild.getModel()}\n")//手机系列
            stringBuilder.append("rom= ${UtilKDevice.getRomVersion()}\n")//rom
            stringBuilder.append("os= ${UtilKBuild.getVersionRelease()}\n")//API版本:9.0
            stringBuilder.append("sdk= ${UtilKBuild.getVersionSDKCode()}\n")//SDK版本:31
            stringBuilder.append("launch_time= $_launchTime\n")//启动APP的时间
            stringBuilder.append("crash_time= ${UtilKDate.date2String(Date(), UtilKDate.FORMAT_yyyyMMddHHmmss)}")//crash发生的时间
            stringBuilder.append("foreground= ${StackK.isFront()}")//应用处于前台
            stringBuilder.append("thread= ${Thread.currentThread().name}\n")//异常线程名

            //app info
            val packageInfo = _context.packageManager.getPackageInfo(_context.packageName, 0)
            stringBuilder.append("version_code= ${packageInfo.versionCode}\n")
            stringBuilder.append("version_name= ${packageInfo.versionName}\n")
            stringBuilder.append("package_code= ${packageInfo.packageName}\n")
            stringBuilder.append("requested_permissions= ${Arrays.toString(packageInfo.requestedPermissions)}\n")

            //storage info
            val memoryInfo = ActivityManager.MemoryInfo()
            val activityManager = _context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityManager.getMemoryInfo(memoryInfo)

            stringBuilder.append("availableMemory= ${Formatter.formatFileSize(_context, memoryInfo.availMem)}\n")//可用内存
            stringBuilder.append("totalMemory= ${Formatter.formatFileSize(_context, memoryInfo.totalMem)}\n")//设备总内存

            //sd storage size
            stringBuilder.append("availableStorage= ${UtilKDevice.getFreeExternalMemorySize()}\n")//存储空间

            //stack info
            val write: Writer = StringWriter()
            val printWriter = PrintWriter(write)
            e.printStackTrace(printWriter)
            var cause = e.cause
            while (cause != null) {
                cause.printStackTrace(printWriter)
                cause = cause.cause
            }
            printWriter.close()
            stringBuilder.append(write.toString())
            return stringBuilder.toString()
        }
    }
}