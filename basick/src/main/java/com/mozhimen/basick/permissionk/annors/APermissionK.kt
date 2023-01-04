package com.mozhimen.basick.permissionk.annors

/**
 * @ClassName PermissionAnnor
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/5/25 11:22
 * @Version 1.0
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class APermissionK(
    vararg val permission: String//需要持有的权限
)