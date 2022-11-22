package com.mozhimen.uicorektest.recyclerk

import android.view.View
import com.mozhimen.basick.elemk.activity.commons.BaseActivityVB
import com.mozhimen.basick.utilk.exts.start
import com.mozhimen.uicorektest.databinding.ActivityRecyclerkBinding

/**
 * @ClassName RecyclerKActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/7 1:17
 * @Version 1.0
 */
class RecyclerKActivity : BaseActivityVB<ActivityRecyclerkBinding>() {

    fun goRecyclerKAdapter(view: View) {
        start<RecyclerKAdapterActivity>()
    }

    fun goRecyclerKLoad(view: View) {
        start<RecyclerKLoadActivity>()
    }
}