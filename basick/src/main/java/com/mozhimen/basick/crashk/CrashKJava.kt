package com.mozhimen.basick.crashk

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.os.Process
import android.os.StatFs
import android.text.format.Formatter
import android.util.Log
import com.mozhimen.basick.BuildConfig
import com.mozhimen.basick.crashk.commons.ICrashKListener
import com.mozhimen.basick.stackk.StackK
import com.mozhimen.basick.utilk.UtilKDate
import com.mozhimen.basick.utilk.UtilKGlobal
import java.io.*
import java.util.*
import kotlin.system.exitProcess

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
    const val CRASHK_JAVA_DIR = "crashk_java"

    private val TAG = "CrashKJava>>>>>"
    private var _crashFullPath: String = getJavaCrashDir().absolutePath
    private var _crashListener: ICrashKListener? = null

    fun init(crashKListener: ICrashKListener) {
        this._crashListener = crashKListener
        Thread.setDefaultUncaughtExceptionHandler(CrashKUncaughtExceptionHandler())
    }

    fun getJavaCrashFiles(): Array<File> {
        return File(_crashFullPath).listFiles() ?: emptyArray()
    }

    fun getJavaCrashDir(): File {
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
            restartApp()
        }

        private fun restartApp() {
            val intent = _context.packageManager.getLaunchIntentForPackage(_context.packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            _context.startActivity(intent)

            Process.killProcess(Process.myPid())
            exitProcess(10)
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
                Log.e(TAG, log)
            }

            _crashListener?.onGetCrashJava(log)

            saveCrashInfo2File(log)
            return true
        }

        private fun saveCrashInfo2File(log: String) {
            val crashDir = File(_crashFullPath!!)
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
            val sb = StringBuilder()
            //device info
            sb.append("brand= ${Build.BRAND}\n")//手机品牌
            sb.append("rom= ${Build.MODEL}\n")//手机系列
            sb.append("os= ${Build.VERSION.RELEASE}\n")//API版本:9.0
            sb.append("sdk= ${Build.VERSION.SDK_INT}\n")//SDK版本:31
            sb.append("launch_time= $_launchTime\n")//启动APP的时间
            sb.append("crash_time= ${UtilKDate.date2String(Date(), UtilKDate.FORMAT_yyyyMMddHHmmss)}")//crash发生的时间
            sb.append("foreground= ${StackK.isFront()}")//应用处于前台
            sb.append("thread= ${Thread.currentThread().name}\n")//异常线程名
            sb.append("cpu_arch= ${Build.CPU_ABI}")//CPU架构

            //app info
            val packageInfo = _context.packageManager.getPackageInfo(_context.packageName, 0)
            sb.append("version_code= ${packageInfo.versionCode}\n")
            sb.append("version_name= ${packageInfo.versionName}\n")
            sb.append("package_code= ${packageInfo.packageName}\n")
            sb.append("requested_permissions= ${Arrays.toString(packageInfo.requestedPermissions)}\n")

            //storage info
            val memoryInfo = ActivityManager.MemoryInfo()
            val activityManager = _context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityManager.getMemoryInfo(memoryInfo)

            sb.append("availableMemory= ${Formatter.formatFileSize(_context, memoryInfo.availMem)}\n")//可用内存
            sb.append("totalMemory= ${Formatter.formatFileSize(_context, memoryInfo.totalMem)}\n")//设备总内存

            //sd storage size
            val file = Environment.getExternalStorageDirectory()
            val statFs = StatFs(file.path)
            val availableSize = statFs.availableBlocks * statFs.blockSize
            sb.append("availableStorage= ${Formatter.formatFileSize(_context, availableSize.toLong())}\n")//存储空间

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
            sb.append(write.toString())
            return sb.toString()
        }
    }
}