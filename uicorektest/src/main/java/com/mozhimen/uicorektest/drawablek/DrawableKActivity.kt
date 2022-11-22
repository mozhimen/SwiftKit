package com.mozhimen.uicorektest.drawablek

import android.view.View
import com.mozhimen.basick.elemk.activity.commons.BaseActivityVB
import com.mozhimen.basick.utilk.exts.start
import com.mozhimen.uicorektest.databinding.ActivityDrawablekBinding

class DrawableKActivity : BaseActivityVB<ActivityDrawablekBinding>() {

    fun goDrawableKArrow(view: View) {
        start<DrawableKArrowActivity>()
    }
}