package com.mozhimen.uicorektest.drawablek

import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.uicorektest.databinding.ActivityDrawablekBinding

class DrawableKActivity : BaseActivityVB<ActivityDrawablekBinding>() {

    fun goDrawableKArrow(view: View) {
        startContext<DrawableKArrowActivity>()
    }
}