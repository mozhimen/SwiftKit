package com.mozhimen.uicorektest.popwink

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import com.mozhimen.basick.animk.AnimK
import com.mozhimen.basick.animk.mos.TranslationConfig
import com.mozhimen.basick.basek.BaseKActivityVB
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
class PopwinKActivity : BaseKActivityVB<ActivityPopwinkBinding>() {
    override fun initData(savedInstanceState: Bundle?) {

    }

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
            return AnimK.asAnimation().asTranslation(TranslationConfig.FROM_TOP).show()
        }

        override fun onCreateDismissAnimation(): Animation {
            return AnimK.asAnimation().asTranslation(TranslationConfig.TO_TOP).dismiss()
        }
    }
}