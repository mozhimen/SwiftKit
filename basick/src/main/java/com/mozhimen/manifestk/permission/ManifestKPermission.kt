package com.mozhimen.manifestk.permission

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import com.mozhimen.kotlin.elemk.androidx.fragment.InvisibleProxyFragment_ofAndroid
import com.mozhimen.kotlin.elemk.androidx.fragment.InvisibleProxyFragment_ofAndroidx
import com.mozhimen.kotlin.elemk.commons.IA_Listener
import com.mozhimen.kotlin.elemk.commons.I_Listener
import com.mozhimen.manifestk.permission.annors.APermissionCheck
import com.mozhimen.kotlin.utilk.bases.BaseUtilK
import com.mozhimen.kotlin.utilk.android.app.UtilKActivityStart
import com.mozhimen.kotlin.utilk.wrapper.UtilKPermission
import com.mozhimen.kotlin.utilk.android.app.getAnnotation
import com.mozhimen.kotlin.utilk.android.os.UtilKBuildVersion
import com.mozhimen.kotlin.utilk.android.util.w
import com.mozhimen.manifestk.permission.helpers.InvisiblePermissionProxyFragment_ofAndroid
import com.mozhimen.manifestk.permission.helpers.InvisiblePermissionProxyFragment_ofAndroidx

/**
 * @ClassName ManifestKPermission
 * @Description TO
 * @Author mozhimen
 * @Version 1.0
 */
object ManifestKPermission : BaseUtilK() {
    const val PERMISSION_REQUEST_CODE = 101

    @JvmStatic
    fun requestPermission(
        fragmentActivity: FragmentActivity,
        permission: String,
        onSuccess: I_Listener,
        onFail: I_Listener? = { UtilKActivityStart.startSettingApplicationDetailsSettings(fragmentActivity) }
    ) {
        requestPermission(fragmentActivity, permission, onResult = { if (it) onSuccess.invoke() else onFail?.invoke() })
    }

    @JvmStatic
    fun requestPermission(
        fragmentActivity: FragmentActivity,
        permission: String,
        onResult: IA_Listener<Boolean>? = null
    ) {
        requestPermissions(fragmentActivity, arrayOf(permission), onResult)
    }

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun requestPermissions(
        fragmentActivity: FragmentActivity,
        onSuccess: I_Listener,
        onFail: I_Listener? = { UtilKActivityStart.startSettingApplicationDetailsSettings(fragmentActivity) }
    ) {
        requestPermissions(fragmentActivity, onResult = { if (it) onSuccess.invoke() else onFail?.invoke() })
    }

    @JvmStatic
    @Throws(Exception::class)
    fun requestPermissions(
        fragmentActivity: FragmentActivity,
        onResult: IA_Listener<Boolean>/*(isGranted: Boolean) -> Unit*/? = null,
    ) {
        val permissionAnnor = fragmentActivity.getAnnotation(APermissionCheck::class.java)
        require(permissionAnnor != null) { "$TAG you may be forget add annor" }
        val permissions = mutableSetOf<String>()
        permissions.addAll(permissionAnnor.permissions)
        requestPermissions(fragmentActivity, permissions.toTypedArray(), onResult)
    }

    @JvmStatic
    fun requestPermissions(
        fragmentActivity: FragmentActivity,
        permissions: Array<String>,
        onResult: IA_Listener<Boolean>/*(isGranted: Boolean) -> Unit*/? = null
    ) {
        if (permissions.isNotEmpty()) {
            if (!UtilKPermission.isSelfGranted(permissions)) {
                requestPermissions_ofProxyFragment(fragmentActivity, permissions, onResult = { onResult?.invoke(it) })
            } else {
                onResult?.invoke(true)
            }
        } else {
            onResult?.invoke(true)
        }
    }

    @JvmStatic
    fun requestPermissions_ofProxyFragment(activity: Activity, permissions: Array<out String>, onResult: IA_Listener<Boolean>) {
        val noPermissions = mutableListOf<String>()
        for (permission in permissions) {
            if (!UtilKPermission.isSelfGranted(permission))
                noPermissions.add(permission)
        }
        if (noPermissions.isNotEmpty()) {
//            val fragmentManager = fragmentActivity.supportFragmentManager
//            val existedFragment = fragmentManager.findFragmentByTag(TAG)
//            val fragment = if (existedFragment != null) {
//                existedFragment as InvisiblePermissionProxyFragment
//            } else {
//                val invisiblePermissionProxyFragment = InvisiblePermissionProxyFragment()
//                fragmentManager.beginTransaction().add(invisiblePermissionProxyFragment, TAG).commitNow()
//                invisiblePermissionProxyFragment
//            }
//            fragment.request(noPermissions.toTypedArray(), onResult)
            if (activity is FragmentActivity) {
                InvisibleProxyFragment_ofAndroidx.startInvisibleProxyFragment<InvisiblePermissionProxyFragment_ofAndroidx>(activity.supportFragmentManager,
                    onAction = { it.request(permissions) },
                    onResult = null,
                    onPermissionResult = PERMISSION_REQUEST_CODE to { deniedList ->
                        if (deniedList != null) {
                            printDeniedPermissions(deniedList)
                            onResult.invoke(deniedList.isEmpty())
                        } else {
                            onResult.invoke(false)
                        }
                    }
                )
            } else if (UtilKBuildVersion.isAfterV_23_6_M()) {
                InvisibleProxyFragment_ofAndroid.startInvisibleProxyFragment<InvisiblePermissionProxyFragment_ofAndroid>(activity.fragmentManager,
                    onAction = { it.request(permissions) },
                    onResult = null,
                    onPermissionResult = PERMISSION_REQUEST_CODE to { deniedList->
                        if (deniedList != null) {
                            printDeniedPermissions(deniedList)
                            onResult.invoke(deniedList.isEmpty())
                        } else {
                            onResult.invoke(false)
                        }
                    }
                )
            }
        } else {
            onResult.invoke(true)
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private fun printDeniedPermissions(deniedList: List<String>) {
        "printDeniedPermissions $deniedList".w(TAG)
//        if (deniedList.isNotEmpty())
//            "请在设置中打开${deniedList.joinToString()}权限".showToastOnMain()
    }
}