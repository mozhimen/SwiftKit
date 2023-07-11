package com.mozhimen.basick.stackk.process

import com.mozhimen.basick.stackk.commons.IStackK
import com.mozhimen.basick.stackk.process.helpers.StackKProcessDelegate

/**
 * @ClassName StackKProcess
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/11 14:32
 * @Version 1.0
 */
class StackKProcess private constructor() : IStackK by StackKProcessDelegate() {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    /////////////////////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = StackKProcess()
    }
}