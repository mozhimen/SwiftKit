package com.mozhimen.basick.elemk.receiver.commons

/**
 * @ClassName IReceiverInstall
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/19 15:32
 * @Version 1.0
 */
interface IReceiverInstallListener {
    fun onAppUpdate()
    fun onAppInstall()
    fun onAppUnInstall()
}