package com.mozhimen.uicorektest.layoutk.refresh

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.start
import com.mozhimen.uicorektest.databinding.ActivityLayoutkRefreshBinding

/**
 * @ClassName LayoutKRefreshActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/7 1:36
 * @Version 1.0
 */
class LayoutKRefreshActivity : BaseKActivityVB<ActivityLayoutkRefreshBinding>() {
    override fun initData(savedInstanceState: Bundle?) {

    }

    fun goLayoutKRefreshLottie(view: View) {
        start<LayoutKRefreshLottieActivity>()
    }

    fun goLayoutKRefreshText(view: View) {
        start<LayoutKRefreshTextActivity>()
    }
}