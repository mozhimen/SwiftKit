package com.mozhimen.basick.utilk.java.util

import java.util.concurrent.locks.ReentrantLock

/**
 * @ClassName UtilKReentrantLock
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/3/30 1:40
 * @Version 1.0
 */
fun ReentrantLock.safeUnlock(){
    UtilKReentrantLock.safeUnlock(this)
}

///////////////////////////////////////////////////////////////////////

object UtilKReentrantLock {
    @JvmStatic
    fun safeUnlock(reentrantLock: ReentrantLock) {
        if (reentrantLock.isHeldByCurrentThread()) {
            reentrantLock.unlock()
        }
    }
}