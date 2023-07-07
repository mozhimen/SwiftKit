package com.mozhimen.basick.elemk.receiver.bases

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.mozhimen.basick.lintk.optin.annors.ADescription
import com.mozhimen.basick.elemk.receiver.commons.ITimeReceiverListener


/**
 * @ClassName BaseTimeReceiver
 * @Description

 * 继承
class ElemKTimeReceiver : BaseTimeReceiver(
object : ITimeReceiverListener {
override fun onTimeTick() {
Log.v("ElemKTimeReceiver>>>>>", "onTimeTick: long ${UtilKDate.getNowLong()} string ${UtilKDate.getNowString()}")
}
}
)

 * 动态注册
class ElemKReceiverActivity : BaseActivityVB<ActivityElemkReceiverBinding>() {
private lateinit var _receiverDelegate: ReceiverDelegate<ElemKReceiverActivity>

override fun initData(savedInstanceState: Bundle?) {
Log.d(TAG, "initData: start")
_receiverDelegate = ReceiverDelegate(
this,
arrayOf(
Intent.ACTION_TIME_TICK,
Intent.ACTION_TIMEZONE_CHANGED,
Intent.ACTION_TIME_CHANGED
),
ElemKTimeReceiver()
)
super.initData(savedInstanceState)
}
}

 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/10 14:01
 * @Version 1.0
 */
@ADescription("need register dynamic 需要动态注册")
open class BaseTimeReceiver(private val _listener: ITimeReceiverListener) : BaseBroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_TIMEZONE_CHANGED -> {
                _listener.onTimeZoneChanged()
            }
            Intent.ACTION_TIME_TICK -> {
                _listener.onTimeTick()
            }
            Intent.ACTION_TIME_CHANGED -> {
                _listener.onTimeChanged()
            }
        }
    }
}