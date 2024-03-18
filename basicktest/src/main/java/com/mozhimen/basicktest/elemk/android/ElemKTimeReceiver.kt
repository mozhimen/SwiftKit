package com.mozhimen.basicktest.elemk.android

import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.elemk.android.content.bases.BaseTimeBroadcastReceiver
import com.mozhimen.basick.elemk.android.content.bases.ITimeReceiverListener
import com.mozhimen.basick.elemk.java.util.cons.CDateFormat
import com.mozhimen.basick.lintk.optins.OApiCall_RegisterDynamic
import com.mozhimen.basick.utilk.java.util.UtilKDate


/**
 * @ClassName ElemKTimeReceiver
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/10 14:17
 * @Version 1.0
 */
@OApiCall_RegisterDynamic
class ElemKTimeReceiver : BaseTimeBroadcastReceiver(
    object : ITimeReceiverListener {
        override fun onTimeTick() {
            UtilKLogWrapper.v("ElemKTimeReceiver>>>>>", "onTimeTick: long ${UtilKDate.getNowLong()} string ${UtilKDate.getNowStr(CDateFormat.HH_mm)}")
        }
    }
)