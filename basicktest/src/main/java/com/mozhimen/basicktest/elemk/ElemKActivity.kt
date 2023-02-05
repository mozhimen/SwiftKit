package com.mozhimen.basicktest.elemk

import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.start
import com.mozhimen.basicktest.databinding.ActivityElemkBinding
import com.mozhimen.basicktest.elemk.activity.ElemKVBVMActivity
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

    fun goElemKActivityVBVM(view: View) {
        start<ElemKVBVMActivity>()
    }

    fun goElemKServiceActivity(view: View) {
        start<ElemKServiceActivity>()
    }

    fun goElemKReceiverActivity(view: View) {
        start<ElemKReceiverActivity>()
    }

    fun goElemKGestureActivity(view: View) {
        start<ElemKGestureActivity>()
    }
}