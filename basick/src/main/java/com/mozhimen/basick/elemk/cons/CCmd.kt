package com.mozhimen.basick.elemk.cons

/**
 * @ClassName CCmd
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 18:40
 * @Version 1.0
 */
object CCmd {
    const val FILL_LIGHT_OPEN = "echo 255 > /sys/devices/platform/pwm-leds/leds/rgb:leds/brightness"
    const val FILL_LIGHT_CLOSE = "echo 0 > /sys/devices/platform/pwm-leds/leds/rgb:leds/brightness"
}