package com.mozhimen.basicsmk.crashmk

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Looper
import android.os.Process
import android.util.Log
import android.widget.Toast
import com.mozhimen.basicsmk.utilmk.UtilMKGlobal
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import java.util.*
import kotlin.system.exitProcess

/**
 * @ClassName CrashManager
 * @Description
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/9 17:18
 * @Version 1.0
 */
@SuppressLint("StaticFieldLeak")
class CrashMK private constructor() : Thread.UncaughtExceptionHandler {

    //region # variate
    private val TAG = "CrashMK>>>>>"

    private lateinit var _context: Application
    private lateinit var _exceptionHandler: Thread.UncaughtExceptionHandler

    private var _clcCallback: CrashMKLifeCycleCallback? = null

    private val _crashInfos = HashMap<String, String>() //用来存储设备信息和异常信息

    private var _logFileName: String? = null

    private var _crashMKCallback: CrashMKCallback? = null

    companion object {
        @JvmStatic
        val instance: CrashMK by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            CrashMK()
        }
    }
    //endregion

    //初始化
    fun init(
        crashMKCallback: CrashMKCallback
    ) {
        _context = UtilMKGlobal.instance.getApp()!!
        _clcCallback = CrashMKLifeCycleCallback()
        _crashMKCallback = crashMKCallback
        _exceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        _context.registerActivityLifecycleCallbacks(_clcCallback)
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    fun getLog(): String? {
        return _logFileName
    }

    fun getTopActivity(): Class<*>? {
        val manager = _context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val className = manager.getRunningTasks(1)[0].topActivity!!.className
        Log.d(TAG, "getTopActivity: className $className")
        var cls: Class<*>? = null
        try {
            cls = Class.forName(className)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return cls
    }


    //region # private function
    //当UncaughtException发生时会转入该函数来处理
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        ex.printStackTrace()
        if (!handleException(ex)) {
            _exceptionHandler.uncaughtException(thread, ex)
        } else {
            try {
                Log.d(TAG, "uncaughtException: restart")
                val alarmManager = _context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(_context, getTopActivity())
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                val restartIntent = PendingIntent
                    .getActivity(_context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
                alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 500, restartIntent)
                _context.startActivity(intent)
            } catch (e: Exception) {
                Log.e(TAG, "uncaughtException: ${e.localizedMessage}")
            }

            // 退出程序
            _clcCallback?.removeAllActivities()
            Process.killProcess(Process.myPid())
            exitProcess(0)
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private fun handleException(ex: Throwable?): Boolean {
        if (ex == null) return false
        object : Thread() {
            override fun run() {
                Looper.prepare()
                Toast.makeText(_context, "程序出现异常,即将退出.", Toast.LENGTH_LONG).show()
                Looper.loop()
            }
        }.start()

        // 收集设备参数信息
        collectDeviceInfo(_context)

        // 获取到日志文件名
        _logFileName = saveCrashInfo2File(ex)

        // 上传或其他处理外放
        _crashMKCallback?.onGetMessage(_logFileName)

        return true
    }

    //收集设备参数信息
    private fun collectDeviceInfo(context: Context) {
        try {
            val packageManager = context.packageManager
            val packageInfo =
                packageManager.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
            packageInfo?.let {
                val versionName =
                    if (packageInfo.versionName == null) "null" else packageInfo.versionName
                val versionCode = packageInfo.versionCode.toString()
                _crashInfos["versionName"] = versionName
                _crashInfos["versionCode"] = versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "collectDeviceInfo: an error happened when collect package info")
        }

        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                _crashInfos[field.name] = field[null].toString()
                Log.d(TAG, "collectDeviceInfo: ${field.name} : ${field[null]})")
            } catch (e: Exception) {
                Log.e(TAG, "collectDeviceInfo: an error happened when collect crash info")
            }
        }
    }

    /**
     * 保存错误信息到文件中，需要有对SD的读写权限！
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private fun saveCrashInfo2File(ex: Throwable): String {
        val sb = StringBuilder()
        val writer: Writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append("<font color=\"#FF0000\">")
        sb.append(result)
        sb.append("</font>")
        return sb.toString()
    }
    //endregion
}