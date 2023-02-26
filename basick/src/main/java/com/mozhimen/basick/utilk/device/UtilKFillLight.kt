package com.mozhimen.basick.utilk.device

import com.mozhimen.basick.utilk.java.io.UtilKCmd


/**
 * @ClassName UtilKLight
 * @Description 补光灯
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/24 15:18
 * @Version 1.0
 */
object UtilKFillLight {
    private const val CMD_FILL_LIGHT_OPEN = "echo 255 > /sys/devices/platform/pwm-leds/leds/rgb:leds/brightness"
    private const val CMD_FILL_LIGHT_CLOSE = "echo 0 > /sys/devices/platform/pwm-leds/leds/rgb:leds/brightness"

    /**
     * 开补光灯
     */
    @JvmStatic
    fun openFillLight() {
        UtilKCmd.executeShellCmd(CMD_FILL_LIGHT_OPEN)
    }

    /**
     * 关补光灯
     */
    @JvmStatic
    fun closeFillLight() {
        UtilKCmd.executeShellCmd(CMD_FILL_LIGHT_CLOSE)
    }
}