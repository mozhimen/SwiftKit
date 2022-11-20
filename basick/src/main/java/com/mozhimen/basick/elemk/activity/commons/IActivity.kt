package com.mozhimen.basick.elemk.activity.commons

import android.os.Bundle

/**
 * @ClassName BaseKIAction
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/25 13:44
 * @Version 1.0
 */
interface IActivity {
    fun initFlag()

    fun initLayout()

    fun initData(savedInstanceState: Bundle?)

    fun initView(savedInstanceState: Bundle?)
}