package com.mozhimen.basick.manifestk.permission

import android.content.pm.PackageManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.permission.annors.APermissionsCheck
import com.mozhimen.basick.manifestk.permission.helpers.IManifestKPermissionListener
import com.mozhimen.basick.manifestk.permission.helpers.InvisibleFragment
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.app.UtilKLaunchActivity
import com.mozhimen.basick.utilk.android.content.UtilKContextCompat
import com.mozhimen.basick.utilk.android.widget.showToastOnMain

/**
 * @ClassName ManifestKPermission
 * @Description TO
 * @Author mozhimen
 * @Date 2021/4/14 17:08
 * @Version 1.0
 */
object ManifestKPermission : BaseUtilK() {

    /**
     * 作用: 权限申请
     * @param activity AppCompatActivity
     * @param onSuccess Function0<Unit>
     * @param onFail Function0<Unit>
     */
    @JvmStatic
    fun initPermissions(
        activity: AppCompatActivity,
        onSuccess: I_Listener,
        onFail: I_Listener? = { UtilKLaunchActivity.startSettingAppDetails(activity) }
    ) {
        initPermissions(activity, onResult = { if (it) onSuccess.invoke() else onFail?.invoke() })
    }

    /**
     * 作用: 权限申请
     * @param activity AppCompatActivity
     * @param onResult Function1<Boolean, Unit>
     */
    @JvmStatic
    @Throws(Exception::class)
    fun initPermissions(
        activity: AppCompatActivity,
        onResult: (IA_Listener<Boolean>/*(isGranted: Boolean) -> Unit*/)? = null,
    ) {
        val permissionAnnor = activity.javaClass.getAnnotation(APermissionCheck::class.java)
        val permissionsAnnor = activity.javaClass.getAnnotation(APermissionsCheck::class.java)
        require(permissionAnnor != null || permissionsAnnor != null) { "$TAG you may be forget add annor" }
        if (permissionAnnor != null) {
            initPermissions(activity, permissionAnnor.permission, onResult)
        } else if (permissionsAnnor != null) {
            initPermissions(activity, permissionsAnnor.permissions, onResult)
        }
    }

    /**
     * 作用: 权限申请
     * @param activity AppCompatActivity
     * @param permissions Array<String>
     * @param onResult Function1<Boolean, Unit>
     */
    @JvmStatic
    fun initPermissions(
        activity: AppCompatActivity,
        permissions: Array<out String>,
        onResult: (IA_Listener<Boolean>/*(isGranted: Boolean) -> Unit*/)? = null
    ) {
        if (permissions.isNotEmpty()) {
            if (!checkPermissions(permissions)) {
                requestPermissions(activity, *permissions) { allGranted, deniedList ->
                    printDeniedList(deniedList)
                    onResult?.invoke(allGranted)
                }
            } else {
                onResult?.invoke(true)
            }
        } else {
            onResult?.invoke(true)
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
                allGranted = allGranted and (UtilKContextCompat.checkSelfPermission(_context, it) == PackageManager.PERMISSION_GRANTED)
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