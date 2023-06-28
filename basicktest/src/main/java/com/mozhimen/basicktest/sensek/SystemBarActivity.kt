package com.mozhimen.basicktest.sensek

import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.sensek.systembar.SenseKSystemBar
import com.mozhimen.basick.sensek.systembar.annors.ASenseKSystemBar
import com.mozhimen.basick.sensek.systembar.cons.CSystemBarType
import com.mozhimen.basicktest.databinding.ActivitySensekSystembarBinding

@ASenseKSystemBar(systemBarType = CSystemBarType.IMMERSED_LIGHT)
//@ASenseKSystemBar(systemBarType = ASenseKSystemBarType.CUSTOM, isFontIconDark = false, bgColorLight = android.R.color.black)
class SystemBarActivity : BaseActivityVB<ActivitySensekSystembarBinding>() {
    override fun initFlag() {
        SenseKSystemBar.init(this)
    }
}