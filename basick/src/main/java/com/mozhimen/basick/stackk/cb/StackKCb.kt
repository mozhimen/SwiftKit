package com.mozhimen.basick.stackk.cb


import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiInit_InApplication
import com.mozhimen.basick.stackk.cb.helpers.StackKCbDelegate
import com.mozhimen.basick.stackk.commons.IStackK

/**
 * @ClassName StackKCb
 * @Description 提供前后台状态监听 以及栈顶activity的服务
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/20 21:58
 * @Version 1.0
 */
@ALintKOptIn_ApiInit_InApplication
class StackKCb private constructor() : IStackK by StackKCbDelegate() {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    /////////////////////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = StackKCb()
    }
}