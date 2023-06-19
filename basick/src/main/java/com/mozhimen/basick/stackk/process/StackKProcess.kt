package com.mozhimen.basick.stackk.process

import android.app.Activity
import com.mozhimen.basick.stackk.commons.IStackK
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.stackk.process.helpers.StackKProcessProxy
import java.lang.ref.WeakReference

/**
 * @ClassName StackKProcess
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/11 14:32
 * @Version 1.0
 */
class StackKProcess private constructor() : IStackK by StackKProcessProxy() {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    /////////////////////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = StackKProcess()
    }
}