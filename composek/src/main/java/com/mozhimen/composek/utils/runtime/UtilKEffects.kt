package com.mozhimen.composek.utils.runtime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import com.mozhimen.kotlin.elemk.commons.IA_Listener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @ClassName UtilKEffect
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/25
 * @Version 1.0
 */
@Composable
fun <T> T.debounceChange(
    delayMillis: Long = 1000L,
    // 1. couroutine scope
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onChange: IA_Listener<T>,
): T =
    UtilKEffects.debounceChange(this, delayMillis, coroutineScope, onChange)

////////////////////////////////////////////////////////////////

object UtilKEffects {

    /**
     *     var text by remember { mutableStateOf("") }
     *     //搜索
     *     text.debounceChange(delayMillis = 2000) {
     *         onTextChange?.invoke(it)
     *     }
     */
    @JvmStatic
    @Composable
    fun <T> debounceChange(
        data: T,
        delayMillis: Long = 1000L,
        // 1. couroutine scope
        coroutineScope: CoroutineScope = rememberCoroutineScope(),
        onChange: IA_Listener<T>,
    ): T {
        // 2. updating state
        val state: T by rememberUpdatedState(data)

        // 3. launching the side-effect handler
        DisposableEffect(state) {
            val job = coroutineScope.launch {
                delay(delayMillis)
                onChange(state)
            }
            onDispose {
                job.cancel()
            }
        }
        return state
    }
}