package com.mozhimen.uicorektest.drawablek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.start
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityDrawablekBinding

class DrawableKActivity : BaseKActivityVB<ActivityDrawablekBinding>() {
    override fun initData(savedInstanceState: Bundle?) {

    }

    fun goDrawableKArrow(view: View) {
        start<DrawableKArrowActivity>()
    }
}