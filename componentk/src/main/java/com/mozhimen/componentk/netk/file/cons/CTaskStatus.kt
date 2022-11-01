package com.mozhimen.componentk.netk.file.cons


/**
 * @ClassName ETaskStatus
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/1 17:44
 * @Version 1.0
 */
object CTaskStatus {
    const val IDLE = 0x0000
    const val STARTED = 0x0001
    const val PROGRESSING = 0x0002
    const val PAUSED = 0x0003
    const val CANCELED = 0x0004
    const val WAITED = 0x0005
    const val COMPLETE = 0x0006
    const val FAILED = 0x0007
}