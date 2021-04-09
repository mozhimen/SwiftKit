package com.swiftmk.library.util.permission

import androidx.fragment.app.FragmentActivity

object PermissionApplier {
    private const val tag = "InvisibleFragment"

    /**
     * 用法示例:
     * PermissionApplier.request(this,Manifest.permission.CALL_PHONE,
     * ...){ allGranted,deniedList ->
     *     if(allGranted){ ... }
     *     else { ... }
     * }
     */
    fun request(
        activity: FragmentActivity,
        vararg permissions: String,
        callback: PermissionCallback
    ) {
        val fragmentManager=activity.supportFragmentManager
        val existedFragment=fragmentManager.findFragmentByTag(tag)
        val fragment=if(existedFragment!=null){
            existedFragment as InvisibleFragment
        }else{
            val invisibleFragment=InvisibleFragment()
            fragmentManager.beginTransaction().add(invisibleFragment, tag).commitNow()
            invisibleFragment
        }
        fragment.requestNow(callback,*permissions)
    }
}