package com.mozhimen.basick.utilk.device

import android.content.Context
import android.hardware.usb.UsbManager
import com.mozhimen.basick.utilk.content.UtilKContext


/**
 * @ClassName UtilKUsb
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/20 16:17
 * @Version 1.0
 */
object UtilKUsbManager {

    @JvmStatic
    fun get(context: Context): UsbManager =
        UtilKContext.getUsbManager(context)
}