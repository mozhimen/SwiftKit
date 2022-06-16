package com.mozhimen.abilityk.camera2k.commons

/**
 * @ClassName ICamera2KFrameListener
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/16 12:16
 * @Version 1.0
 */
interface ICamera2KFrameListener {
    fun onFrame(bytes: ByteArray, time: Long)
}