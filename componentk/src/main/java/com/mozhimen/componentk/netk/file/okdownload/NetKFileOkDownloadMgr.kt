package com.mozhimen.componentk.netk.file.okdownload

import android.content.Context
import com.liulishuo.okdownload.OkDownloadExt
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication

/**
 * @ClassName NetKFileOkDownloadMgr
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/11/24 21:25
 * @Version 1.0
 */
@OptInApiInit_InApplication
object NetKFileOkDownloadMgr {
    /**
     * fix bug of #415
     */
    @JvmStatic
    fun init(context: Context) {
        OkDownloadExt.fix415Bug(context)
    }
}