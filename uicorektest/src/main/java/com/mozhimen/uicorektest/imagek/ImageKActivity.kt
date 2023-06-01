package com.mozhimen.uicorektest.imagek

import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.content.startContext
import com.mozhimen.uicorektest.databinding.ActivityImagekBinding


/**
 * @ClassName ImageKActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/11 14:11
 * @Version 1.0
 */
class ImageKActivity : BaseActivityVB<ActivityImagekBinding>() {

    fun goImageKBindingAdapter(view: View) {
        startContext<ImageKBindingAdapterActivity>()
    }
}