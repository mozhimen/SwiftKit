package com.mozhimen.swiftmk.helper.permission

/**
 * @ClassName PermissionAnnor
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/5/25 11:22
 * @Version 1.0
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class PermissionAnnor(
    val isNeededPermissions: Boolean = false,//是否检查权限
)