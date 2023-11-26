package com.mozhimen.componentk.netk.app.cons

import com.mozhimen.componentk.netk.app.download.mos.AppDownloadException

/**
 * @ClassName ENetKAppFinish
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/10 17:41
 * @Version 1.0
 */
sealed class ENetKAppFinishType {
    object SUCCESS : ENetKAppFinishType()
    object CANCEL : ENetKAppFinishType()
    data class FAIL(val exception: AppDownloadException) : ENetKAppFinishType()
}