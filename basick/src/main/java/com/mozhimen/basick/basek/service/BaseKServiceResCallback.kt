package com.mozhimen.basick.basek.service

import com.mozhimen.basick.IBaseKServiceResListener

/**
 * @ClassName BaseKServiceResCallback
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/28 0:58
 * @Version 1.0
 */
open class BaseKServiceResCallback : IBaseKServiceResListener.Stub() {
    override fun onResInt(resInt: Int) {}
    override fun onResLong(resLong: Long) {}
    override fun onResBoolean(resBoolean: Boolean) {}
    override fun onResFloat(resFloat: Float) {}
    override fun onResDouble(resDouble: Double) {}
    override fun onResString(resString: String?) {}
}