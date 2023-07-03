package com.mozhimen.basicktest.sensek

import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.sensek.systembar.annors.ASenseKSystemBarProperty
import com.mozhimen.basick.sensek.systembar.annors.ASenseKSystemBarPropertyAnd
import com.mozhimen.basick.sensek.systembar.annors.ASenseKSystemBarPropertyOr
import com.mozhimen.basick.sensek.systembar.cons.CProperty
import com.mozhimen.basick.sensek.systembar.cons.CPropertyAnd
import com.mozhimen.basick.sensek.systembar.cons.CPropertyOr
import com.mozhimen.basick.sensek.systembar.initSenseKSystemBar
import com.mozhimen.basicktest.databinding.ActivitySensekSystembarBinding

//简单用法, 直接使用预制的属性
//@ASenseKSystemBarProperty(CProperty.IMMERSED_HARD_STICKY)
//@ASenseKSystemBarProperty(CProperty.IMMERSED_HARD_STICKY)
//高级用法自己组合
@ASenseKSystemBarProperty(CProperty.NORMAL)
@ASenseKSystemBarPropertyOr(CPropertyOr.IMMERSED_OPEN, CPropertyOr.HIDE_STATUS_BAR, CPropertyOr.HIDE_NAVIGATION_BAR)
class SystemBarActivity : BaseActivityVB<ActivitySensekSystembarBinding>() {
    override fun initFlag() {
        initSenseKSystemBar()
    }
}