package com.mozhimen.basick.elemk.android.content.bases

import android.content.Context
import android.content.Intent
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.lintk.optin.OptInApiCall_RegisterDynamic
import com.mozhimen.basick.utilk.bases.IUtilK

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
private lateinit var _receiverProxy: ReceiverProxy<ElemKReceiverActivity>

override fun initData(savedInstanceState: Bundle?) {
Log.d(TAG, "initData: start")
_receiverProxy = ReceiverProxy(
this,
arrayOf(
CIntent.ACTION_TIME_TICK,
CIntent.ACTION_TIMEZONE_CHANGED,
CIntent.ACTION_TIME_CHANGED
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
interface ITimeReceiverListener : IUtilK {
    fun onTimeZoneChanged() {}
    fun onTimeTick() {}
    fun onTimeChanged() {}
}

@OptInApiCall_RegisterDynamic
open class BaseTimeBroadcastReceiver(private val _listener: ITimeReceiverListener) : BaseBroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            CIntent.ACTION_TIMEZONE_CHANGED -> _listener.onTimeZoneChanged()
            CIntent.ACTION_TIME_TICK -> _listener.onTimeTick()
            CIntent.ACTION_TIME_CHANGED -> _listener.onTimeChanged()
        }
    }
}