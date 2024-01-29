package com.mozhimen.basick.utilk.android.hardware

import android.content.Context
import android.hardware.usb.UsbDevice

/**
 * @ClassName UtilKUsbDevice
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/30 0:15
 * @Version 1.0
 */
object UtilKUsbDevice {
    @JvmStatic
    fun get(context: Context): Collection<UsbDevice> =
        UtilKUsbManager.getDeviceList(context).values


}