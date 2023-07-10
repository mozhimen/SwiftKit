package com.mozhimen.basicktest.elemk.receiver

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.lintk.optin.annors.AOptInInitByLazy
import com.mozhimen.basick.elemk.android.content.bases.BaseReceiverProxy
import com.mozhimen.basick.lintk.optin.annors.AOptInNeedCallBindLifecycle
import com.mozhimen.basicktest.databinding.ActivityElemkReceiverBinding


/**
 * @ClassName ElemKReceiverActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/10 14:42
 * @Version 1.0
 */
class ElemKReceiverActivity : BaseActivityVB<ActivityElemkReceiverBinding>() {
    @OptIn(AOptInInitByLazy::class, AOptInNeedCallBindLifecycle::class)
    private val _receiverProxy: BaseReceiverProxy<ElemKReceiverActivity> by lazy { BaseReceiverProxy(
        this,
        ElemKTimeReceiver(),
        Intent.ACTION_TIME_TICK,
        Intent.ACTION_TIMEZONE_CHANGED,
        Intent.ACTION_TIME_CHANGED
    ) }

    @OptIn(AOptInInitByLazy::class, AOptInNeedCallBindLifecycle::class)
    override fun initView(savedInstanceState: Bundle?) {
        Log.d(TAG, "initView: start")
        _receiverProxy.bindLifecycle(this)
    }
}