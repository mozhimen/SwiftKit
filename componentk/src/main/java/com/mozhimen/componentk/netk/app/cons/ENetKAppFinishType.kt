package com.mozhimen.componentk.netk.app.cons

import java.lang.Exception

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
    data class FAIL(val exception: Exception) : ENetKAppFinishType()
}