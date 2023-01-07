package com.mozhimen.abilityk.installk

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.text.TextUtils.SimpleStringSplitter
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.mozhimen.abilityk.installk.commons.IInstallStateChangedListener
import com.mozhimen.abilityk.installk.cons.EInstallMode
import com.mozhimen.basick.permissionk.PermissionK
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.basick.utilk.UtilKPermission
import com.mozhimen.basick.utilk.app.UtilKAppInstall
import com.mozhimen.basick.utilk.app.UtilKAppRoot
import com.mozhimen.basick.utilk.context.UtilKActivitySkip
import com.mozhimen.basick.utilk.context.UtilKApplication
import com.mozhimen.basick.utilk.file.UtilKFileNet
import java.io.*

/**
 * @ClassName InstallK
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/7 0:04
 * @Version 1.0
 */
@APermissionK(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET)
class InstallKBuilder : Handler(Looper.getMainLooper()) {

    companion object {
        private const val TAG = "InstallKBuilder>>>>>"
        private val PERMISSIONS = arrayOf(Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        @JvmStatic
        val instance = InstallKBuilderProvider.holder

        class Builder(private val context: Context) {
            private var _installMode: EInstallMode = EInstallMode.BOTH
            private var _installStateChangedListener: IInstallStateChangedListener? = null
            private var _directory = Environment.getExternalStorageDirectory().absolutePath

            fun setMode(mode: EInstallMode): Builder {
                _installMode = mode
                return this
            }

            fun setOnStateChangedListener(listener: IInstallStateChangedListener?): Builder {
                _installStateChangedListener = listener
                return this
            }

            fun setCacheDirectory(directory: String): Builder {
                _directory = directory
                return this
            }

            fun build(): InstallKBuilder {
                val installKBuilder = InstallKBuilder()
                installKBuilder._installMode = _installMode
                installKBuilder._installStateChangeListener = _installStateChangedListener
                installKBuilder._tempApkPathWithName = _directory
                return installKBuilder
            }
        }
    }

    private object InstallKBuilderProvider {
        val holder = InstallKBuilder()
    }

    private val _context = UtilKApplication.instance.get()
    private var _tempApkPathWithName = "${_context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)}/installk/update.apk"
    private var _installMode = EInstallMode.BOTH
    private var _installStateChangeListener: IInstallStateChangedListener? = null

    private fun installUseAS(apkPathWithName: String) {
        if (PermissionK.checkPermissions(PERMISSIONS)) {// 存储空间
            sendEmptyMessage(4)
            return
        }

        // 允许安装应用
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val b: Boolean = _context.packageManager.canRequestPackageInstalls()
            if (!b) {
                sendEmptyMessage(4)
                return
            }
        }
        val file = File(apkPathWithName)
        if (!file.exists()) {
            Log.e(TAG, "apk file not exists, path: $apkPathWithName")
            return
        }
        val uri = Uri.fromFile(file)
        val intent = Intent(Intent.ACTION_VIEW)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            val contentUri: Uri = FileProvider.getUriForFile(_context, BuildConfig.APPLICATION_ID + ".fileProvider", file)
            _context.grantUriPermission(_context.packageName, contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive")
        } else {
            intent.setDataAndType(uri, "application/vnd.android.package-archive")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        UtilKActivitySkip.start(_context, intent)
        if (!isAccessibilitySettingsOn(_context)) {
            toAccessibilityService()
            sendEmptyMessage(3)
        }
    }

    private fun toAccessibilityService() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        _context.startActivity(intent)
    }


    private fun isAccessibilitySettingsOn(_context: Context): Boolean {
        var accessibilityEnabled = 0
        val service = _context.packageName + "/" + InstallAccessibilityService::class.java.getCanonicalName()
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                _context.applicationContext.contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED
            )
            Log.v(TAG, "accessibilityEnabled = $accessibilityEnabled")
        } catch (e: SettingNotFoundException) {
            Log.e(
                TAG, "Error finding setting, default accessibility to not found: " + e.message
            )
        }
        val mStringColonSplitter = SimpleStringSplitter(':')
        if (accessibilityEnabled == 1) {
            Log.v(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------")
            val settingValue = Settings.Secure.getString(
                _context.applicationContext.contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            )
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue)
                while (mStringColonSplitter.hasNext()) {
                    val accessibilityService = mStringColonSplitter.next()
                    Log.v(TAG, "-------------- > accessibilityService :: $accessibilityService $service")
                    if (accessibilityService.equals(service, ignoreCase = true)) {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!")
                        return true
                    }
                }
            }
        } else {
            Log.v(TAG, "***ACCESSIBILITY IS DISABLED***")
        }
        return false
    }

    @Throws(Exception::class)
    fun install(apkPathWithName: String) {
        require(apkPathWithName.isNotEmpty() && apkPathWithName.endsWith(".apk")) { "$TAG not a correct apk file path" }
        Thread {
            sendEmptyMessage(1)
            when (_installMode) {
                EInstallMode.BOTH -> if (!UtilKAppRoot.isRoot() || !UtilKAppInstall.installRoot(apkPathWithName)) {
                    installUseAS(apkPathWithName)
                }
                EInstallMode.ROOT_ONLY -> UtilKAppInstall.installRoot(apkPathWithName)
                EInstallMode.AUTO_ONLY -> installUseAS(apkPathWithName)
            }
            sendEmptyMessage(0)
        }.start()
    }

    fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        when (msg.what) {
            0 -> if (mOnStateChangedListener != null) mOnStateChangedListener.onComplete()
            1 -> if (mOnStateChangedListener != null) mOnStateChangedListener.onStart()
            3 -> if (mOnStateChangedListener != null) mOnStateChangedListener.onNeed2OpenService()
            4 -> if (mOnStateChangedListener != null) mOnStateChangedListener.needPermission()
        }
    }

    fun install(file: File) {
        install(file.absolutePath)
    }

    fun installFromUrl(httpUrl: String) {
        Thread {
            sendEmptyMessage(1)
            val file = UtilKFileNet.downLoadFile(httpUrl, _tempApkPathWithName)
            install(file)
        }.start()
    }
}