package com.mozhimen.basick.elemk.android.app.bases

import com.mozhimen.basick.elemk.android.app.commons.IBaseServiceResListener

/**
 * @ClassName BaseServiceResCallback
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/28 0:58
 * @Version 1.0
 */
open class BaseServiceResCallback : IBaseServiceResListener.Stub() {
    override fun onResInt(resInt: Int) {}
    override fun onResLong(resLong: Long) {}
    override fun onResBoolean(resBoolean: Boolean) {}
    override fun onResFloat(resFloat: Float) {}
    override fun onResDouble(resDouble: Double) {}
    override fun onResString(resString: String?) {}
}