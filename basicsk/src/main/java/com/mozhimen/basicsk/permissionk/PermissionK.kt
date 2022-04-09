package com.mozhimen.basicsk.permissionk

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

/**
 * @ClassName PermissionApplier
 * @Description TO
 * @Author mozhimen
 * @Date 2021/4/14 17:08
 * @Version 1.0
 */
object PermissionK {
    private val TAG = "PermissionK>>>>>"

    /**
     * 作用: 权限申请
     */
    fun initPermissions(
        activity: AppCompatActivity,
        permissionKCallback: PermissionKCallback
    ) {
        val permissionAnnor = activity.javaClass.getAnnotation(PermissionKAnnor::class.java)
            ?: return
        val permissions = permissionAnnor.permissions
        if (permissions.isNotEmpty()) {
            if (!checkPermissions(activity, *permissions)) {
                requestPermissions(activity, *permissions) { allGranted, deniedList ->
                    permissionKCallback(allGranted, deniedList)
                }
            } else {
                permissionKCallback(true, emptyList())
            }
        } else {
            Log.e(TAG, "initPermissions: you may be forget add annor")
            permissionKCallback(true, emptyList())
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
        callback: PermissionKCallback
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
     * 作用: 权限检查
     */
    fun checkPermissions(activity: Activity, vararg permissions: String): Boolean {
        var allGranted = true
        return if (permissions.isEmpty()) {
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

    /**
     * 设置申请权限
     */
    fun applySetts(activity: Activity) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = Uri.fromParts("package", activity.packageName, null)
        activity.startActivity(intent)
    }
}