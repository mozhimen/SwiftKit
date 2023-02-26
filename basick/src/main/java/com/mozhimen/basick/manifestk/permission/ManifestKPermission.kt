package com.mozhimen.basick.manifestk.permission

import android.content.pm.PackageManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.permission.helpers.IManifestKPermissionListener
import com.mozhimen.basick.manifestk.permission.helpers.InvisibleFragment
import com.mozhimen.basick.utilk.content.UtilKIntentSkip
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.exts.showToastOnMain

/**
 * @ClassName ManifestKPermission
 * @Description TO
 * @Author mozhimen
 * @Date 2021/4/14 17:08
 * @Version 1.0
 */
object ManifestKPermission {
    private const val TAG = "ManifestKPermission>>>>>"
    private val _context = UtilKApplication.instance.get()

    /**
     * 作用: 权限申请
     * @param activity AppCompatActivity
     * @param onSuccess Function0<Unit>
     * @param onFail Function0<Unit>
     */
    @JvmStatic
    fun initPermissions(
        activity: AppCompatActivity,
        onSuccess: () -> Unit,
        onFail: (() -> Unit)? = { UtilKIntentSkip.startSettingSelf(activity) }
    ) {
        initPermissions(activity, isGranted = { if (it) onSuccess.invoke() else onFail?.invoke() })
    }

    /**
     * 作用: 权限申请
     * @param activity AppCompatActivity
     * @param isGranted Function1<Boolean, Unit>
     */
    @JvmStatic
    @Throws(Exception::class)
    fun initPermissions(
        activity: AppCompatActivity,
        isGranted: ((Boolean) -> Unit)? = null,
    ) {
        val permissionAnnor = activity.javaClass.getAnnotation(APermissionCheck::class.java)
        requireNotNull(permissionAnnor) { "$TAG you may be forget add annor" }
        initPermissions(activity, permissionAnnor.permission, isGranted)
    }

    /**
     * 作用: 权限申请
     * @param activity AppCompatActivity
     * @param permissions Array<String>
     * @param isGranted Function1<Boolean, Unit>
     */
    @JvmStatic
    fun initPermissions(
        activity: AppCompatActivity,
        permissions: Array<out String>,
        isGranted: ((Boolean) -> Unit)? = null
    ) {
        if (permissions.isNotEmpty()) {
            if (!checkPermissions(permissions)) {
                requestPermissions(activity, *permissions) { allGranted, deniedList ->
                    printDeniedList(deniedList)
                    isGranted?.invoke(allGranted)
                }
            } else {
                isGranted?.invoke(true)
            }
        } else {
            isGranted?.invoke(true)
        }
    }

    /**
     * 批量申请动态权限
     * @param activity FragmentActivity
     * @param permissions Array<out String>
     * @param callback Function2<Boolean, List<String>, Unit>
     */
    @JvmStatic
    fun requestPermissions(
        activity: FragmentActivity,
        vararg permissions: String,
        callback: IManifestKPermissionListener
    ) {
        val fragmentManager = activity.supportFragmentManager
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        val fragment = if (existedFragment != null) {
            existedFragment as InvisibleFragment
        } else {
            val invisibleFragment = InvisibleFragment()
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }
        fragment.requestNow(callback, *permissions)
    }

    /**
     * 权限检查
     * @param permission String
     * @return Boolean
     */
    fun checkPermission(permission: String): Boolean {
        return checkPermissions(arrayOf(permission))
    }

    /**
     * 权限检查
     * @param permissions Array<out String>
     * @return Boolean
     */
    @JvmStatic
    fun checkPermissions(permissions: Array<out String>): Boolean {
        return checkPermissions(permissions.toList())
    }

    /**
     * 权限检查
     * @param permissions Array<out String>
     * @return Boolean
     */
    @JvmStatic
    fun checkPermissions(permissions: List<String>): Boolean {
        var allGranted = true
        return if (permissions.isEmpty()) true
        else {
            permissions.forEach {
                allGranted = allGranted and (ContextCompat.checkSelfPermission(_context, it) == PackageManager.PERMISSION_GRANTED)
            }
            allGranted
        }
    }

    /**
     * 打印被拒绝的权限
     * @param deniedList List<String>
     */
    private fun printDeniedList(deniedList: List<String>) {
        Log.w(TAG, "printDeniedList $deniedList")
        if (deniedList.isNotEmpty()) {
            "请在设置中打开${deniedList.joinToString()}权限".showToastOnMain()
        }
    }
}