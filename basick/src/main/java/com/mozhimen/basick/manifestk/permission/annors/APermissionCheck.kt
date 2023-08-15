package com.mozhimen.basick.manifestk.permission.annors

/**
 * @ClassName APermissionKCheck
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/11 14:49
 * @Version 1.0
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class APermissionCheck(
    vararg val permissions: String//需要持有的权限
)
