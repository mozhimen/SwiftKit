package com.mozhimen.basick.utilk.android.hardware

import android.content.Context
import android.hardware.input.InputManager
import com.mozhimen.basick.utilk.android.content.UtilKContext

/**
 * @ClassName UtilKInputManager
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/28
 * @Version 1.0
 */
object UtilKInputManager {
    @JvmStatic
    fun get(context: Context): InputManager =
        UtilKContext.getInputManager(context)
}