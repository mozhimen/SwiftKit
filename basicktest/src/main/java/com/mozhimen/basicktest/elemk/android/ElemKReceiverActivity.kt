package com.mozhimen.basicktest.elemk.android

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.android.content.bases.BaseBroadcastReceiverProxy
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.lintk.optins.OApiInit_ByLazy
import com.mozhimen.basick.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optins.OApiCall_RegisterDynamic
import com.mozhimen.basicktest.databinding.ActivityElemkReceiverBinding


/**
 * @ClassName ElemKReceiverActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/10 14:42
 * @Version 1.0
 */
class ElemKReceiverActivity : BaseActivityVDB<ActivityElemkReceiverBinding>() {
    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiCall_RegisterDynamic::class)
    private val _receiverProxy: BaseBroadcastReceiverProxy<ElemKReceiverActivity> by lazy {
        BaseBroadcastReceiverProxy(
            this,
            ElemKTimeReceiver(),
            arrayOf(
                CIntent.ACTION_TIME_TICK,
                CIntent.ACTION_TIMEZONE_CHANGED,
                CIntent.ACTION_TIME_CHANGED
            )
        )
    }

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class)
    override fun initView(savedInstanceState: Bundle?) {
        Log.d(TAG, "initView: start")
        _receiverProxy.bindLifecycle(this)
    }
}