package com.mozhimen.taskk.temps

import com.mozhimen.kotlin.elemk.commons.I_Listener
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.taskk.bases.BaseWakeBefDestroyTaskK
import com.mozhimen.kotlin.utilk.wrapper.UtilKApp
import com.mozhimen.kotlin.utilk.android.widget.showToast

/**
 * @ClassName TaskKExitApp
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/26 16:57
 * @Version 1.0
 */
@OApiCall_BindLifecycle
@OApiInit_ByLazy
class TaskKBackPressedExit : BaseWakeBefDestroyTaskK() {
    private var _exitWaitTime = 2000L//退出App判断时间
    private var _firstClickTime = 0L//用来记录第一次点击的时间
    private var _strTip = ""
    private var _onExit: I_Listener? = null

    override fun isActive(): Boolean = _firstClickTime != 0L

    override fun cancel() {
        _firstClickTime = 0L
    }

    /////////////////////////////////////////////////////////

    fun setStrTip(strTip: String): TaskKBackPressedExit {
        _strTip = strTip
        return this
    }

    fun setExitWaitTime(time: Long): TaskKBackPressedExit {
        _exitWaitTime = time
        return this
    }

    fun setOnExitListener(onExit: I_Listener) {
        _onExit = onExit
    }

    fun onBackPressed(): Boolean {
        val secondClickTime = System.currentTimeMillis()
        if (secondClickTime - _firstClickTime > _exitWaitTime) {
            _firstClickTime = secondClickTime
            if (_strTip.isNotEmpty())
                _strTip.showToast()
            return false
        }
        _onExit?.invoke()
//        UtilKApp.exitApp()
        return true
    }
}