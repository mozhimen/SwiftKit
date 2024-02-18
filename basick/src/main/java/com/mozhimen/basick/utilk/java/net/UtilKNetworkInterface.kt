package com.mozhimen.basick.utilk.java.net

import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.IUtilK
import java.net.Inet6Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.Enumeration

/**
 * @ClassName UtilKNetworkInterface
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/27 11:34
 * @Version 1.0
 */
object UtilKNetworkInterface : IUtilK {
    @JvmStatic
    fun getNetworkInterfaces(): Enumeration<NetworkInterface> =
        NetworkInterface.getNetworkInterfaces()

    //获取网路IP
    @JvmStatic
    fun getStrIP(): String? {
        try {
            val networkInterfaces: Enumeration<NetworkInterface> = getNetworkInterfaces()
            var inetAddress: InetAddress
            while (networkInterfaces.hasMoreElements()) {
                val inetAddresses: Enumeration<InetAddress> = (networkInterfaces.nextElement() as NetworkInterface).inetAddresses
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement() as InetAddress
                    if (inetAddress !is Inet6Address) {
                        if (inetAddress.hostAddress != "127.0.0.1") {
                            return inetAddress.hostAddress ?: continue
                        }
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
            "getStrIP SocketException ${e.message}".et(TAG)
        }
        return null
    }
}