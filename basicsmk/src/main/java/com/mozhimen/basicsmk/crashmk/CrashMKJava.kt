package com.mozhimen.basicsmk.crashmk

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.os.Process
import android.os.StatFs
import android.text.format.Formatter
import com.mozhimen.basicsmk.BuildConfig
import com.mozhimen.basicsmk.logmk.LogMK
import com.mozhimen.basicsmk.stackmk.StackMK
import com.mozhimen.basicsmk.utilmk.UtilMKDate
import com.mozhimen.basicsmk.utilmk.UtilMKGlobal
import java.io.*
import java.util.*

/**
 * @ClassName CrashHandler
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/27 16:27
 * @Version 1.0
 */
object CrashMKJava {
    const val CRASHMK_JAVA_DIR = "crashmk_java_dir"
    private var _crashFullPath: String? = null

    fun init(crashDir: String) {
        Thread.setDefaultUncaughtExceptionHandler(CaughtExceptionHandler())
        this._crashFullPath = crashDir
    }

    fun getJavaCrashFiles(): Array<File> {
        return File(_crashFullPath).listFiles()
    }

    fun getJavaCrashDir(): File {
        val javaCrashFile = File(UtilMKGlobal.instance.getApp()!!.cacheDir, CRASHMK_JAVA_DIR)
        if (!javaCrashFile.exists()) {
            javaCrashFile.mkdirs()
        }
        return javaCrashFile
    }

    private class CaughtExceptionHandler : Thread.UncaughtExceptionHandler {
        private val context = UtilMKGlobal.instance.getApp()!!
        private val defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        private val launchTime = UtilMKDate.date2String(Date(), UtilMKDate.FORMAT_yyyyMMddHHmmss)

        override fun uncaughtException(t: Thread, e: Throwable) {
            if (!handleException(e) && defaultExceptionHandler != null) {
                defaultExceptionHandler.uncaughtException(t, e)
            }
            restartApp()
        }

        private fun restartApp() {
            val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(intent)

            Process.killProcess(Process.myPid())
            System.exit(10)
        }

        /**
         *  设备类型、0S本版、线程名、前后台、使用时长、App版本、升级渠道
         *  CPU架构、内存信息、存储信息、permission权限
         */
        private fun handleException(e: Throwable): Boolean {
            if (e == null) return false
            val log = collectDeviceInfo(e)
            if (BuildConfig.DEBUG) {
                LogMK.e(log)
            }

            saveCrashInfo2File(log)
            return true
        }

        private fun saveCrashInfo2File(log: String) {
            val crashDir = File(_crashFullPath)
            if (!crashDir.exists()) {
                crashDir.mkdirs()
            }
            val crashmkFile = File(crashDir, UtilMKDate.date2String(Date(), UtilMKDate.FORMAT_yyyyMMddHHmmss + "_crashmk.txt"))
            crashmkFile.createNewFile()
            val fos = FileOutputStream(crashmkFile)

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
            sb.append("brand=${Build.BRAND}\n")//手机品牌
            sb.append("rom=${Build.MODEL}\n")//手机系列
            sb.append("os=${Build.VERSION.RELEASE}\n")//API版本:9.0
            sb.append("sdk=${Build.VERSION.SDK_INT}\n")//SDK版本:31
            sb.append("launch_time=$launchTime\n")//启动APP的时间
            sb.append("crash_time=${UtilMKDate.date2String(Date(), UtilMKDate.FORMAT_yyyyMMddHHmmss)}")//crash发生的时间
            sb.append("foreground=${StackMK.instance.isFront()}")//应用处于前台
            sb.append("thread=${Thread.currentThread().name}\n")//异常线程名
            sb.append("cpu_arch=${Build.CPU_ABI}")//CPU架构

            //app info
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            sb.append("version_code=${packageInfo.versionCode}\n")
            sb.append("version_name=${packageInfo.versionName}\n")
            sb.append("package_code=${packageInfo.packageName}\n")
            sb.append("requested_permissions=${Arrays.toString(packageInfo.requestedPermissions)}\n")

            //storage info
            val memoryInfo = ActivityManager.MemoryInfo()
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityManager.getMemoryInfo(memoryInfo)

            sb.append("availableMemory=${Formatter.formatFileSize(context, memoryInfo.availMem)}\n")//可用内存
            sb.append("totalMemory=${Formatter.formatFileSize(context, memoryInfo.totalMem)}\n")//设备总内存

            //sd storage size
            val file = Environment.getExternalStorageDirectory()
            val statFs = StatFs(file.path)
            val availableSize = statFs.availableBlocks * statFs.blockSize
            sb.append("availableStorage=${Formatter.formatFileSize(context, availableSize.toLong())}\n")//存储空间

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