package com.mozhimen.basicktest.sensek

import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.sensek.systembar.annors.ASenseKSystemBarProperty
import com.mozhimen.basick.sensek.systembar.cons.CProperty
import com.mozhimen.basick.sensek.systembar.initSenseKSystemBar
import com.mozhimen.basicktest.databinding.ActivitySensekSystembarBinding

//Normal
@ASenseKSystemBarProperty(CProperty.IMMERSED_HARD_STICKY)
class SystemBarActivity : BaseActivityVB<ActivitySensekSystembarBinding>() {
    override fun initFlag() {
        initSenseKSystemBar()
    }
}