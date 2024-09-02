package com.mozhimen.basicktest.elemk.android

import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.kotlin.elemk.android.content.bases.BaseTimeBroadcastReceiver
import com.mozhimen.kotlin.elemk.android.content.bases.ITimeReceiverListener
import com.mozhimen.kotlin.elemk.java.util.cons.CDateFormat
import com.mozhimen.kotlin.lintk.optins.OApiCall_RegisterDynamic
import com.mozhimen.kotlin.utilk.java.util.UtilKDate
import com.mozhimen.kotlin.utilk.java.util.UtilKDateWrapper


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
            UtilKLogWrapper.v("ElemKTimeReceiver>>>>>", "onTimeTick: long ${UtilKDateWrapper.getNowLong()} string ${UtilKDateWrapper.getNowStr(CDateFormat.Format.HH_mm)}")
        }
    }
)