package com.mozhimen.uicorektest.layoutk.refresh

import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.start
import com.mozhimen.uicorektest.databinding.ActivityLayoutkRefreshBinding

/**
 * @ClassName LayoutKRefreshActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/7 1:36
 * @Version 1.0
 */
class LayoutKRefreshActivity : BaseActivityVB<ActivityLayoutkRefreshBinding>() {
    fun goLayoutKRefreshLottie(view: View) {
        start<LayoutKRefreshLottieActivity>()
    }

    fun goLayoutKRefreshText(view: View) {
        start<LayoutKRefreshTextActivity>()
    }
}