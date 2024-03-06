package com.mozhimen.basick.manifestk.permission.scoped

import androidx.fragment.app.FragmentActivity
import com.mozhimen.basick.lintk.optins.OApiDeprecated_Official_AfterV_30_11_R
import com.mozhimen.basick.lintk.optins.application.OApplication_PRESERVE_LEGACY_EXTERNAL_STORAGE
import com.mozhimen.basick.lintk.optins.application.OApplication_REQUEST_LEGACY_EXTERNAL_STORAGE
import com.mozhimen.basick.lintk.optins.permission.OPermission_MANAGE_EXTERNAL_STORAGE
import com.mozhimen.basick.lintk.optins.permission.OPermission_READ_EXTERNAL_STORAGE
import com.mozhimen.basick.lintk.optins.permission.OPermission_WRITE_EXTERNAL_STORAGE
import com.mozhimen.basick.manifestk.permission.scoped.cons.CManifestKPermissionScoped
import com.mozhimen.basick.manifestk.permission.scoped.helpers.IManifestKPermissionScopedListener
import com.mozhimen.basick.manifestk.permission.scoped.helpers.InvisibleProxyFragmentScoped
import com.mozhimen.basick.utilk.commons.IUtilK

/**
 * @ClassName ManifestKPermissionScope
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/10 0:16
 * @Version 1.0
 */
@Deprecated("OApiDeprecated_Official_AfterV_30_11_R")
@OApiDeprecated_Official_AfterV_30_11_R
@OPermission_WRITE_EXTERNAL_STORAGE
@OPermission_READ_EXTERNAL_STORAGE
@OPermission_MANAGE_EXTERNAL_STORAGE
@OApplication_REQUEST_LEGACY_EXTERNAL_STORAGE
@OApplication_PRESERVE_LEGACY_EXTERNAL_STORAGE
object ManifestKPermissionScoped : IUtilK {

    @JvmStatic
    fun requestPermissionScopedStorageAndroidObb(
        activity: FragmentActivity,
        onResult: IManifestKPermissionScopedListener
    ) {
        val fragmentManager = activity.supportFragmentManager
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        val fragment: InvisibleProxyFragmentScoped = if (existedFragment != null) {
            existedFragment as InvisibleProxyFragmentScoped
        } else {
            val invisibleProxyFragmentScoped = InvisibleProxyFragmentScoped.newInstance(CManifestKPermissionScoped.STR_ANDROID_OBB)
            fragmentManager.beginTransaction().add(invisibleProxyFragmentScoped, TAG).commitNow()
            invisibleProxyFragmentScoped
        }
        fragment.request(onResult)
    }

    @JvmStatic
    fun requestPermissionScopedStorageAndroidData(
        activity: FragmentActivity,
        onResult: IManifestKPermissionScopedListener
    ) {
        val fragmentManager = activity.supportFragmentManager
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        val fragment: InvisibleProxyFragmentScoped = if (existedFragment != null) {
            existedFragment as InvisibleProxyFragmentScoped
        } else {
            val invisibleProxyFragmentScoped = InvisibleProxyFragmentScoped.newInstance(CManifestKPermissionScoped.STR_ANDROID_DATA)
            fragmentManager.beginTransaction().add(invisibleProxyFragmentScoped, TAG).commitNow()
            invisibleProxyFragmentScoped
        }
        fragment.request(onResult)
    }
}