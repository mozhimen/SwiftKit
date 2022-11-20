package com.mozhimen.uicorektest.popwink

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.animation.Animation
import com.mozhimen.basick.animk.builder.AnimKBuilder
import com.mozhimen.basick.animk.builder.cons.EDirection
import com.mozhimen.basick.animk.builder.temps.*
import com.mozhimen.basick.elemk.activity.commons.BaseActivityVB
import com.mozhimen.uicorek.popwink.PopwinKLifecycle
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityPopwinkBinding


/**
 * @ClassName PopwinKActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/17 16:45
 * @Version 1.0
 */
class PopwinKActivity : BaseActivityVB<ActivityPopwinkBinding>() {

    private val _popwin by lazy { Popwin(this) }
    fun onPopwinShow(view: View) {
        _popwin.showPopupWindow()
    }

    class Popwin(context: Context) : PopwinKLifecycle(context) {
        init {
            setContentView(R.layout.layout_popwin)
            setBackgroundColor(Color.TRANSPARENT)
        }

        override fun onCreateShowAnimation(): Animation {
            return AnimKBuilder.asAnimation().asTranslation(TranslationType.FROM_TOP_SHOW).build()
        }

        override fun onCreateDismissAnimation(): Animation {
            return AnimKBuilder.asAnimation().asTranslation(TranslationType.TO_TOP_HIDE).build()
        }
    }
}