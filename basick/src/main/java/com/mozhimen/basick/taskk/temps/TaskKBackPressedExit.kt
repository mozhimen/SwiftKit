package com.mozhimen.basick.taskk.temps

import android.widget.Toast
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.taskk.bases.BaseWakeBefDestroyTaskK
import com.mozhimen.basick.utilk.android.content.UtilKApp
import com.mozhimen.basick.utilk.android.widget.showToast

/**
 * @ClassName TaskKExitApp
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/26 16:57
 * @Version 1.0
 */
@OptInApiCall_BindLifecycle
@OptInApiInit_ByLazy
class TaskKBackPressedExit : BaseWakeBefDestroyTaskK() {
    private var _exitWaitTime = 2000L//退出App判断时间
    private var _firstClickTime = 0L//用来记录第一次点击的时间

    override fun isActive(): Boolean = _firstClickTime != 0L

    fun setExitWaitTime(time: Long): TaskKBackPressedExit {
        _exitWaitTime = time
        return this
    }

    fun onBackPressed(onExit: I_Listener? = null): Boolean {
        val secondClickTime = System.currentTimeMillis()
        if (secondClickTime - _firstClickTime > _exitWaitTime) {
            _firstClickTime = secondClickTime
            "再按一次退出纽扣助手".showToast()
            return false
        }
        onExit?.invoke()
        UtilKApp.exitApp()
        return true
    }

    override fun cancel() {
        _firstClickTime = 0L
    }
}