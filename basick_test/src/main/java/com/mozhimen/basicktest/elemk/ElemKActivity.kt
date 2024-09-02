package com.mozhimen.basicktest.elemk

import android.view.View
import com.mozhimen.kotlin.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.kotlin.utilk.android.content.startContext
import com.mozhimen.basicktest.databinding.ActivityElemkBinding
import com.mozhimen.basicktest.elemk.android.ElemKAndroidActivity
import com.mozhimen.basicktest.elemk.androidx.ElemKAndroidXActivity

/**
 * @ClassName ElemKActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/19 16:44
 * @Version 1.0
 */
class ElemKActivity : BaseActivityVDB<ActivityElemkBinding>() {

    fun goElemKAndroid(view: View) {
        startContext<ElemKAndroidActivity>()
    }

    fun goElemKAndroidX(view: View) {
        startContext<ElemKAndroidXActivity>()
    }
}