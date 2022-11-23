package com.mozhimen.basick.elemk.network.commons


/**
 * @ClassName INetworkListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/23 14:12
 * @Version 1.0
 */
interface INetworkListener {
    fun onConnect()
    fun onLost()
}