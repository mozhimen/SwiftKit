package com.mozhimen.basick.packk.netk.conn.commons

import com.mozhimen.basick.packk.netk.conn.cons.ENetKType


/**
 * @ClassName INetKConnListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/13 15:02
 * @Version 1.0
 */
interface INetKConnListener {
    fun onDisconnected()
    fun onConnected(type: ENetKType)
}