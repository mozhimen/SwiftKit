package com.mozhimen.basick.manifestk.permission

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.permission.helpers.IManifestKPermissionListener
import com.mozhimen.basick.manifestk.permission.helpers.InvisibleFragment
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.app.UtilKLaunchActivity
import com.mozhimen.basick.utilk.android.content.UtilKContextCompat
import com.mozhimen.basick.utilk.android.util.wt
import com.mozhimen.basick.utilk.android.widget.showToastOnMain

/**
 * @ClassName ManifestKPermission
 * @Description TO
 * @Author mozhimen
 * @Date 2021/4/14 17:08
 * @Version 1.0
 */
object ManifestKPermission : BaseUtilK() {

    @JvmStatic
    fun requestPermissions(
        activity: AppCompatActivity,
        onSuccess: I_Listener,
        onFail: I_Listener? = { UtilKLaunchActivity.startSettingAppDetails(activity) }
    ) {
        requestPermissions(activity, onResult = { if (it) onSuccess.invoke() else onFail?.invoke() })
    }

    @JvmStatic
    @Throws(Exception::class)
    fun requestPermissions(
        activity: AppCompatActivity,
        onResult: (IA_Listener<Boolean>/*(isGranted: Boolean) -> Unit*/)? = null,
    ) {
        val permissionAnnor = activity.javaClass.getAnnotation(APermissionCheck::class.java)
        require(permissionAnnor != null) { "$TAG you may be forget add annor" }
        val permissions = mutableSetOf<String>()
        permissions.addAll(permissionAnnor.permission)
        requestPermissions(activity, permissions.toTypedArray(), onResult)
    }

    @JvmStatic
    fun requestPermissions(
        activity: AppCompatActivity,
        permissions: Array<out String>,
        onResult: (IA_Listener<Boolean>/*(isGranted: Boolean) -> Unit*/)? = null
    ) {
        if (permissions.isNotEmpty()) {
            if (!checkPermissions(permissions)) {
                requestPermissionsByFragment(activity, *permissions) { allGranted, deniedList ->
                    printDeniedPermissions(deniedList)
                    onResult?.invoke(allGranted)
                }
            } else onResult?.invoke(true)
        } else onResult?.invoke(true)
    }

    @JvmStatic
    fun requestPermissionsByFragment(
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

    @JvmStatic
    fun checkPermission(permission: String): Boolean =
        checkPermissions(arrayOf(permission))

    @JvmStatic
    fun checkPermissions(permissions: Array<out String>): Boolean =
        checkPermissions(permissions.toList())

    @JvmStatic
    fun checkPermissions(permissions: List<String>): Boolean {
        var allGranted = true
        return if (permissions.isEmpty()) true
        else {
            for (permission in permissions) allGranted = allGranted and (UtilKContextCompat.checkSelfPermission(_context, permission) == PackageManager.PERMISSION_GRANTED)
            allGranted
        }
    }

    private fun printDeniedPermissions(deniedList: List<String>) {
        "printDeniedList $deniedList".wt(TAG)
        if (deniedList.isNotEmpty()) "请在设置中打开${deniedList.joinToString()}权限".showToastOnMain()
    }
}