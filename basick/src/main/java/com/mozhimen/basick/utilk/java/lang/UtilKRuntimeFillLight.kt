package com.mozhimen.basick.utilk.java.lang

import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.cons.CCmd
import com.mozhimen.basick.lintk.optins.permission.OPermission_FLASHLIGHT
import com.mozhimen.basick.manifestk.cons.CPermission


/**
 * @ClassName UtilKLight
 * @Description 补光灯
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/24 15:18
 * @Version 1.0
 */
object UtilKRuntimeFillLight {

    /**
     * 开补光灯
     */
    @JvmStatic
    @OPermission_FLASHLIGHT
    @RequiresPermission(CPermission.FLASHLIGHT)
    fun open() {
        UtilKRuntime.execShC(CCmd.FILL_LIGHT_OPEN)
    }

    /**
     * 关补光灯
     */
    @JvmStatic
    @OPermission_FLASHLIGHT
    @RequiresPermission(CPermission.FLASHLIGHT)
    fun close() {
        UtilKRuntime.execShC(CCmd.FILL_LIGHT_CLOSE)
    }
}