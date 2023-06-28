package com.mozhimen.basick.sensek.net_conn.commons

import com.mozhimen.basick.sensek.net_conn.cons.ENetType


/**
 * @ClassName INetKConnListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/13 15:02
 * @Version 1.0
 */
interface INetConnListener {
    fun onDisconnected()
    fun onConnected(type: ENetType)
}