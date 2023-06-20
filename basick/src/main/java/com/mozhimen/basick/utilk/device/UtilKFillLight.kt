package com.mozhimen.basick.utilk.device

import com.mozhimen.basick.elemk.cons.CCmd
import com.mozhimen.basick.utilk.os.UtilKShell


/**
 * @ClassName UtilKLight
 * @Description 补光灯
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/24 15:18
 * @Version 1.0
 */
object UtilKFillLight {

    /**
     * 开补光灯
     */
    @JvmStatic
    fun openFillLight() {
        UtilKShell.execCmd(CCmd.FILL_LIGHT_OPEN)
    }

    /**
     * 关补光灯
     */
    @JvmStatic
    fun closeFillLight() {
        UtilKShell.execCmd(CCmd.FILL_LIGHT_CLOSE)
    }
}