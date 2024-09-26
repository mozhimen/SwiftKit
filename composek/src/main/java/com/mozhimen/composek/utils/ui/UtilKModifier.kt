package com.mozhimen.composek.utils.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.mozhimen.kotlin.elemk.commons.I_Listener
import kotlinx.coroutines.delay

/**
 * @ClassName UtilKModifier
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/26
 * @Version 1.0
 */
fun Modifier.debouncedClickable(delay: Long = 500, onClick: I_Listener): Modifier =
    UtilKModifier.debouncedClickable(this, delay, onClick)

object UtilKModifier {
    @JvmStatic
    @SuppressLint("ModifierFactoryUnreferencedReceiver", "ModifierFactoryExtensionFunction")
    fun debouncedClickable(modifier: Modifier, delay: Long = 500, onClick: I_Listener): Modifier =
        modifier.composed {
            //按钮是否可点击
            var canClick by remember {
                mutableStateOf(true)
            }
            LaunchedEffect(key1 = canClick, block = {
                if (!canClick) {
                    delay(delay)
                    canClick = true
                }
            })
            Modifier.clickable(canClick) {
                canClick = false
                onClick()
            }
        }
}