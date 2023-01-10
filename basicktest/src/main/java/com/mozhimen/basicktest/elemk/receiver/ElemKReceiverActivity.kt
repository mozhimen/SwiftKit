package com.mozhimen.basicktest.elemk.receiver

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.elemk.receiver.helpers.LifecycleReceiverDelegate
import com.mozhimen.basicktest.databinding.ActivityElemkReceiverBinding


/**
 * @ClassName ElemKReceiverActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/10 14:42
 * @Version 1.0
 */
class ElemKReceiverActivity : BaseActivityVB<ActivityElemkReceiverBinding>() {
    private lateinit var _lifecycleReceiverDelegate: LifecycleReceiverDelegate<ElemKReceiverActivity>

    override fun initData(savedInstanceState: Bundle?) {
        Log.d(TAG, "initData: start")
        _lifecycleReceiverDelegate = LifecycleReceiverDelegate(
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