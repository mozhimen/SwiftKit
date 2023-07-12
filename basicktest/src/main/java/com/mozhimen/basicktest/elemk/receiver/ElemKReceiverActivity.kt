package com.mozhimen.basicktest.elemk.receiver

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.android.content.bases.BaseBroadcastReceiverProxy
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiInit_ByLazy
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiCall_RegisterDynamic
import com.mozhimen.basicktest.databinding.ActivityElemkReceiverBinding


/**
 * @ClassName ElemKReceiverActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/10 14:42
 * @Version 1.0
 */
class ElemKReceiverActivity : BaseActivityVB<ActivityElemkReceiverBinding>() {
    @OptIn(ALintKOptIn_ApiInit_ByLazy::class, ALintKOptIn_ApiCall_BindLifecycle::class, ALintKOptIn_ApiCall_RegisterDynamic::class)
    private val _receiverProxy: BaseBroadcastReceiverProxy<ElemKReceiverActivity> by lazy {
        BaseBroadcastReceiverProxy(
            this,
            ElemKTimeReceiver(),
            Intent.ACTION_TIME_TICK,
            Intent.ACTION_TIMEZONE_CHANGED,
            Intent.ACTION_TIME_CHANGED
        )
    }

    @OptIn(ALintKOptIn_ApiInit_ByLazy::class, ALintKOptIn_ApiCall_BindLifecycle::class)
    override fun initView(savedInstanceState: Bundle?) {
        Log.d(TAG, "initView: start")
        _receiverProxy.bindLifecycle(this)
    }
}