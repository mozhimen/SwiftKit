package com.mozhimen.basicktest.elemk

import android.view.View
import com.mozhimen.basick.elemk.activity.commons.BaseActivityVB
import com.mozhimen.basick.utilk.exts.start
import com.mozhimen.basicktest.databinding.ActivityElemkBinding
import com.mozhimen.basicktest.elemk.activity.ElemKDemoVBVMActivity
import com.mozhimen.basicktest.elemk.service.ElemKDemoServiceActivity

/**
 * @ClassName ElemKActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/19 16:44
 * @Version 1.0
 */
class ElemKActivity : BaseActivityVB<ActivityElemkBinding>() {

    fun goDemoActivityVBVM(view: View) {
        start<ElemKDemoVBVMActivity>()
    }

    fun goDemoServiceActivity(view: View) {
        start<ElemKDemoServiceActivity>()
    }
}