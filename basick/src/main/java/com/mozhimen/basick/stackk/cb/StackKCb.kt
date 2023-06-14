package com.mozhimen.basick.stackk.cb


import android.app.Activity
import com.mozhimen.basick.stackk.cb.helpers.StackKCbProxy
import com.mozhimen.basick.stackk.commons.IStackK
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.utilk.bases.BaseUtilK
import java.lang.ref.WeakReference

/**
 * @ClassName StackKMgr
 * @Description 提供前后台状态监听 以及栈顶activity的服务
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/20 21:58
 * @Version 1.0
 */
class StackKCb private constructor() : IStackK by StackKCbProxy() {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    /////////////////////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = StackKCb()
    }
}