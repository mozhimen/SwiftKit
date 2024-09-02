package com.mozhimen.stackk.annors

import androidx.annotation.IntDef

/**
 * @ClassName ALifecycleOpportunity
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/7/23
 * @Version 1.0
 */
@IntDef(ALifecycleOpportunity.PRE, ALifecycleOpportunity.AT, ALifecycleOpportunity.POST)
annotation class ALifecycleOpportunity {
    companion object {
        const val PRE = 0
        const val AT = 1
        const val POST = 2
    }
}
