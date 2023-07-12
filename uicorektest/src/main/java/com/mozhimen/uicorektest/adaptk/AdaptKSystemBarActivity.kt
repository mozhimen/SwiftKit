package com.mozhimen.uicorektest.adaptk

import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.uicorek.adaptk.systembar.annors.AAdaptKSystemBarProperty
import com.mozhimen.uicorek.adaptk.systembar.annors.AAdaptKSystemBarPropertyOr
import com.mozhimen.uicorek.adaptk.systembar.cons.CProperty
import com.mozhimen.uicorek.adaptk.systembar.cons.CPropertyOr
import com.mozhimen.uicorek.adaptk.systembar.initSenseKSystemBar
import com.mozhimen.uicorektest.databinding.ActivityAdaptkSystembarBinding

//简单用法, 直接使用预制的属性
//@ASenseKSystemBarProperty(CProperty.IMMERSED_HARD_STICKY)
//@ASenseKSystemBarProperty(CProperty.IMMERSED_HARD_STICKY)
//高级用法自己组合
@AAdaptKSystemBarProperty(CProperty.NORMAL)
@AAdaptKSystemBarPropertyOr(CPropertyOr.IMMERSED_OPEN, CPropertyOr.HIDE_STATUS_BAR, CPropertyOr.HIDE_NAVIGATION_BAR)
class AdaptKSystemBarActivity : BaseActivityVB<ActivityAdaptkSystembarBinding>() {
    override fun initFlag() {
        initSenseKSystemBar()
    }
}