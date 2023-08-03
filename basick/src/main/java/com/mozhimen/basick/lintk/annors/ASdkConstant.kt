package com.mozhimen.basick.lintk.annors

/**
 * @ClassName ASdkConstant
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/3 11:24
 * @Version 1.0
 */

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class SdkConstant(val value: SdkConstantType) {
    enum class SdkConstantType {
        ACTIVITY_INTENT_ACTION,
        BROADCAST_INTENT_ACTION,
        SERVICE_ACTION,
        INTENT_CATEGORY,
        FEATURE
    }
}


