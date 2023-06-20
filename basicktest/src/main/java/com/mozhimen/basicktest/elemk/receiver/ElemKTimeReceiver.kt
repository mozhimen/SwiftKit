package com.mozhimen.basicktest.elemk.receiver

import android.util.Log
import com.mozhimen.basick.elemk.cons.CDateFormat
import com.mozhimen.basick.elemk.receiver.bases.BaseTimeReceiver
import com.mozhimen.basick.elemk.receiver.commons.ITimeReceiverListener
import com.mozhimen.basick.utilk.os.UtilKDate


/**
 * @ClassName ElemKTimeReceiver
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/10 14:17
 * @Version 1.0
 */
class ElemKTimeReceiver : BaseTimeReceiver(
    object : ITimeReceiverListener {
        override fun onTimeTick() {
            Log.v("ElemKTimeReceiver>>>>>", "onTimeTick: long ${UtilKDate.getNowLong()} string ${UtilKDate.getNowStr(CDateFormat.HHmm)}")
        }
    }
)