package com.mozhimen.basicktest.elemk

import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basicktest.databinding.ActivityElemkBinding
import com.mozhimen.basicktest.elemk.activity.ElemKVBVMActivity
import com.mozhimen.basicktest.elemk.gesture.ElemKGestureActivity
import com.mozhimen.basicktest.elemk.gesture.ElemKGestureFlingActivity
import com.mozhimen.basicktest.elemk.receiver.ElemKReceiverActivity
import com.mozhimen.basicktest.elemk.service.ElemKServiceActivity

/**
 * @ClassName ElemKActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/19 16:44
 * @Version 1.0
 */
class ElemKActivity : BaseActivityVB<ActivityElemkBinding>() {

    fun goElemKVBVM(view: View) {
        startContext<ElemKVBVMActivity>()
    }

    fun goElemKService(view: View) {
        startContext<ElemKServiceActivity>()
    }

    fun goElemKReceiver(view: View) {
        startContext<ElemKReceiverActivity>()
    }

    fun goElemKGesture(view: View) {
        startContext<ElemKGestureActivity>()
    }

    fun goElemKGestureFling(view: View) {
        startContext<ElemKGestureFlingActivity>()
    }
}