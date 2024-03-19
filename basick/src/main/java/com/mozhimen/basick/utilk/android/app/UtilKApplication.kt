package com.mozhimen.basick.utilk.android.app

import android.app.Application
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion

/**
 * @ClassName UtilKApplication
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/28 23:24
 * @Version 1.0
 */
object UtilKApplication {
    /**
     * 通过Application新的API获取进程名，无需反射，无需IPC，效率最高。
     */
    @JvmStatic
    fun getProcessName(): String? {
        if (UtilKBuildVersion.isAfterV_28_9_P())
            return Application.getProcessName()
        return null
    }
}