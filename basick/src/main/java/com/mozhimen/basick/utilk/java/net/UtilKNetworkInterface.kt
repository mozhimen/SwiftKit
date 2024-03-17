package com.mozhimen.basick.utilk.java.net

import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.kotlin.intIp2strIp
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

    //获取网路IP(移动网络)
    @JvmStatic
    fun getStrIp(): String? {
        try {
            val networkInterfaces: Enumeration<NetworkInterface> = getNetworkInterfaces()
            var inetAddress: InetAddress
            while (networkInterfaces.hasMoreElements()) {
                val networkInterface = (networkInterfaces.nextElement() as NetworkInterface)
                val inetAddresses: Enumeration<InetAddress> = networkInterface.inetAddresses
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement() as InetAddress
                    if (inetAddress !is Inet6Address && !inetAddress.isLoopbackAddress && inetAddress.hostAddress != "127.0.0.1") {
                        return inetAddress.hostAddress ?: continue
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
            "getStrIp SocketException ${e.message}".et(TAG)
        }
        return null
    }

    @JvmStatic
    fun printStrIp() {
        try {
            val networkInterfaces: Enumeration<NetworkInterface> = getNetworkInterfaces()
            var inetAddress: InetAddress
            while (networkInterfaces.hasMoreElements()) {
                val networkInterface = (networkInterfaces.nextElement() as NetworkInterface)
                val inetAddresses: Enumeration<InetAddress> = networkInterface.inetAddresses
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement() as InetAddress
                    if (inetAddress !is Inet6Address && !inetAddress.isLoopbackAddress && inetAddress.hostAddress != "127.0.0.1") {
                        val ip: String = inetAddress.hashCode().intIp2strIp()
                        UtilKLogWrapper.it(TAG, "Found new IP: " + ip + " at " + networkInterface.name)
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
            "printStrIp SocketException ${e.message}".et(TAG)
        }
    }
}