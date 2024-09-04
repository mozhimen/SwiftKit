package com.mozhimen.basicktest.elemk.android

import android.os.Bundle
import com.mozhimen.basick.bases.BaseBroadcastReceiverProxy
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.kotlin.elemk.android.content.cons.CIntent
import com.mozhimen.mvvmk.bases.activity.databinding.BaseActivityVDB
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiCall_RegisterDynamic
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
        UtilKLogWrapper.d(TAG, "initView: start")
        _receiverProxy.bindLifecycle(this)
    }
}