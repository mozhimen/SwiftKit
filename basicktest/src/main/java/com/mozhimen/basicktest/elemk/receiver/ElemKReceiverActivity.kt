package com.mozhimen.basicktest.elemk.receiver

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.elemk.receiver.BaseReceiverDelegate
import com.mozhimen.basicktest.databinding.ActivityElemkReceiverBinding


/**
 * @ClassName ElemKReceiverActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/10 14:42
 * @Version 1.0
 */
class ElemKReceiverActivity : BaseActivityVB<ActivityElemkReceiverBinding>() {
    private val _receiverDelegate: BaseReceiverDelegate<ElemKReceiverActivity> by lazy { BaseReceiverDelegate(
        this,
        ElemKTimeReceiver(),
        Intent.ACTION_TIME_TICK,
        Intent.ACTION_TIMEZONE_CHANGED,
        Intent.ACTION_TIME_CHANGED
    ) }

    override fun initView(savedInstanceState: Bundle?) {
        Log.d(TAG, "initView: start")
        _receiverDelegate.bindLifecycle(this)
    }
}