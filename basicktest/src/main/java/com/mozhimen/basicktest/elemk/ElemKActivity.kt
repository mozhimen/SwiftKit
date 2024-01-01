package com.mozhimen.basicktest.elemk

import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basicktest.databinding.ActivityElemkBinding
import com.mozhimen.basicktest.elemk.android.ElemKAndroidActivity
import com.mozhimen.basicktest.elemk.androidx.ElemKVBVMActivity
import com.mozhimen.basicktest.elemk.android.ElemKGestureActivity
import com.mozhimen.basicktest.elemk.android.ElemKGestureFlingActivity
import com.mozhimen.basicktest.elemk.android.ElemKReceiverActivity
import com.mozhimen.basicktest.elemk.android.ElemKServiceActivity
import com.mozhimen.basicktest.elemk.androidx.ElemKAndroidXActivity

/**
 * @ClassName ElemKActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/19 16:44
 * @Version 1.0
 */
class ElemKActivity : BaseActivityVB<ActivityElemkBinding>() {

    fun goElemKAndroid(view: View) {
        startContext<ElemKAndroidActivity>()
    }

    fun goElemKAndroidX(view: View) {
        startContext<ElemKAndroidXActivity>()
    }
}