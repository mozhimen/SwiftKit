package com.mozhimen.basick.utilk.android.os

import android.content.Context
import android.os.VibratorManager
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.android.content.UtilKContext

/**
 * @ClassName UtilKVibratorManager
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 15:17
 * @Version 1.0
 */
object UtilKVibratorManager {
    @RequiresApi(CVersCode.V_31_11_S)
    fun get(context: Context): VibratorManager =
        UtilKContext.getVibratorManager(context)
}