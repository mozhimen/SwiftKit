package com.mozhimen.swiftmk.helper.permission

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import android.util.Log.INFO
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.mozhimen.swiftmk.helper.toast.showToast

/**
 * @ClassName PermissionApplier
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:08
 * @Version 1.0
 */
object PermissionApplier {
    private val tag = this.javaClass.canonicalName.toString()

    /**
     * 作用: 权限申请
     */
    fun initPermissions(
        activity: AppCompatActivity,
        permissions: Array<String>,
        callback: PermissionCallback
    ) {
        val permissionAnnor = activity.javaClass.getAnnotation(PermissionAnnor::class.java)
        permissionAnnor?.let {
            if (!it.isNeededPermissions) {//是否开启权限申请
                callback(true, emptyList())
                return
            }

            if (!checkPermissions(activity, *permissions)) {
                PermissionApplier.requestPermissions(
                    activity,
                    *permissions
                ) { allGranted, deniedList ->
                    if (allGranted) {
                        callback(allGranted, deniedList)
                    } else {
                        "权限被拒绝".showToast(activity)
                    }
                }
            }
        } ?: run {
            if (permissions.isEmpty() && permissions != null) {
                callback(true, emptyList())
            } else {
                Log.w(tag, "你可能忘记加注解")
            }
        }
    }

    /**
     * 作用: 批量申请动态权限
     * 用法: PermissionApplier.requestPermissions(this,Manifest.permission.CALL_PHONE,
     * ...){ allGranted,deniedList ->
     *     if(allGranted){ ... }
     *     else { ... }
     * }
     */
    fun requestPermissions(
        activity: FragmentActivity,
        vararg permissions: String,
        callback: PermissionCallback
    ) {
        val fragmentManager = activity.supportFragmentManager
        val existedFragment = fragmentManager.findFragmentByTag(tag)
        val fragment = if (existedFragment != null) {
            existedFragment as InvisibleFragment
        } else {
            val invisibleFragment = InvisibleFragment()
            fragmentManager.beginTransaction().add(invisibleFragment, tag).commitNow()
            invisibleFragment
        }
        fragment.requestNow(callback, *permissions)
    }

    /**
     * 作用: 权限检查
     */
    fun checkPermissions(activity: Activity, vararg permissions: String): Boolean {
        var allGranted = true
        return if (permissions == null || permissions.isEmpty()) {
            true
        } else {
            permissions.forEach {
                allGranted = allGranted and (ContextCompat.checkSelfPermission(
                    activity,
                    it
                ) == PackageManager.PERMISSION_GRANTED)
            }
            allGranted
        }
    }
}