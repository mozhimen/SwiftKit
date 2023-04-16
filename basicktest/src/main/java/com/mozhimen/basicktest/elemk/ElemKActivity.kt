package com.mozhimen.basicktest.elemk

import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.start
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
        start<ElemKVBVMActivity>()
    }

    fun goElemKService(view: View) {
        start<ElemKServiceActivity>()
    }

    fun goElemKReceiver(view: View) {
        start<ElemKReceiverActivity>()
    }

    fun goElemKGesture(view: View) {
        start<ElemKGestureActivity>()
    }

    fun goElemKGestureFling(view: View) {
        start<ElemKGestureFlingActivity>()
    }
}