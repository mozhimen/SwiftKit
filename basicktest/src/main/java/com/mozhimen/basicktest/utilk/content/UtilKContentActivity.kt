package com.mozhimen.basicktest.utilk.content

import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basicktest.databinding.ActivityUtilkContentBinding

/**
 * @ClassName UtilKContentActivity
 * @Description TODO
 * @Author Mozhimen & Kolin
 * @Date 2023/4/17 17:24
 * @Version 1.0
 */
class UtilKContentActivity : BaseActivityVB<ActivityUtilkContentBinding>() {
    fun goUtilKContextDir(view: View) {
        startContext<UtilKContextDirActivity>()
    }

    fun goUtilKIntent(view: View) {
        startContext<UtilKIntentActivity>()
    }
}