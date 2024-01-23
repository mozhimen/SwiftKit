package com.mozhimen.basick.elemk.commons

import com.mozhimen.basick.elemk.android.net.cons.ENetType


/**
 * @ClassName IConnectionListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/13 15:02
 * @Version 1.0
 */
interface IConnectionListener {
    fun onDisconnect()
    fun onConnect(type: ENetType)
}