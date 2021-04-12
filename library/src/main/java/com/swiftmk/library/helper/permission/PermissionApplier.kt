package com.swiftmk.library.helper.permission

import androidx.fragment.app.FragmentActivity

/**
 * @ClassName StatusBarIniter1
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/9 17:15
 * @Version 1.0
 */

object PermissionApplier {
    private const val tag = "InvisibleFragment"

    /**
     * 作用: 批量申请动态权限
     * 用法:
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