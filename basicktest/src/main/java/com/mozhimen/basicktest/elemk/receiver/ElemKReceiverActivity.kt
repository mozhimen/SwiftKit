package com.mozhimen.basicktest.elemk.receiver

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.android.content.bases.BaseBroadcastReceiverProxy
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiCall_RegisterDynamic
import com.mozhimen.basicktest.databinding.ActivityElemkReceiverBinding


/**
 * @ClassName ElemKReceiverActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/10 14:42
 * @Version 1.0
 */
class ElemKReceiverActivity : BaseActivityVB<ActivityElemkReceiverBinding>() {
    @OptIn(OptInApiInit_ByLazy::class, OptInApiCall_BindLifecycle::class, OptInApiCall_RegisterDynamic::class)
    private val _receiverProxy: BaseBroadcastReceiverProxy<ElemKReceiverActivity> by lazy {
        BaseBroadcastReceiverProxy(
            this,
            ElemKTimeReceiver(),
            CIntent.ACTION_TIME_TICK,
            CIntent.ACTION_TIMEZONE_CHANGED,
            CIntent.ACTION_TIME_CHANGED
        )
    }

    @OptIn(OptInApiInit_ByLazy::class, OptInApiCall_BindLifecycle::class)
    override fun initView(savedInstanceState: Bundle?) {
        Log.d(TAG, "initView: start")
        _receiverProxy.bindLifecycle(this)
    }
}