package com.mozhimen.basick.manifestk.permission.scoped

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CApplication
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.scoped.cons.CManifestKPermissionScoped
import com.mozhimen.basick.manifestk.permission.scoped.helpers.IManifestKPermissionScopedListener
import com.mozhimen.basick.manifestk.permission.scoped.helpers.InvisibleProxyFragmentScoped
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName ManifestKPermissionScope
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/10 0:16
 * @Version 1.0
 */
@AManifestKRequire(
    CPermission.WRITE_EXTERNAL_STORAGE,
    CPermission.READ_EXTERNAL_STORAGE,
    CPermission.MANAGE_EXTERNAL_STORAGE,
    CApplication.REQUEST_LEGACY_EXTERNAL_STORAGE,
    CApplication.PRESERVE_LEGACY_EXTERNAL_STORAGE
)
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