package com.mozhimen.basick.manifestk.annors

/**
 * @ClassName PermissionAnnor
 * @Description TODO
 * @Author Kolin Zhao
 * @Version 1.0
 */
@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class AManifestKRequire(
    vararg val permission: String//需要持有的权限
)