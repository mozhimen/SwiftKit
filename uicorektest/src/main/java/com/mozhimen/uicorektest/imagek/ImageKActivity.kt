package com.mozhimen.uicorektest.imagek

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.elemk.activity.commons.BaseActivityVB
import com.mozhimen.basick.extsk.start
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
        start<ImageKBindingAdapterActivity>()
    }
}