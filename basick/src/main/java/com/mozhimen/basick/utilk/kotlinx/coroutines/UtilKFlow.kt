package com.mozhimen.basick.utilk.kotlinx.coroutines

import android.view.View
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

/**
 * @ClassName UtilKFlow
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/4 17:29
 * @Version 1.0
 */
fun <T> Flow<T>.throttleFirst(thresholdMillis: Long): Flow<T> =
    UtilKFlow.throttleFirst(this, thresholdMillis)

fun View.asViewClickFlow() =
    UtilKFlow.asViewClickFlow(this)

object UtilKFlow {
    @JvmStatic
    fun asViewClickFlow(view: View): Flow<Unit> = callbackFlow {
        view.setOnClickListener { this.trySend(Unit).isSuccess }
        awaitClose { view.setOnClickListener(null) }
    }

    @JvmStatic
    fun <T> throttleFirst(flow: Flow<T>, thresholdMillis: Long): Flow<T> = flow {
        var lastTime = 0L // 上次发射数据的时间
        flow.collect { upstream ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastTime > thresholdMillis) {// 时间差超过阈值则发送数据并记录时间
                lastTime = currentTime
                emit(upstream)
            }
        }
    }
}