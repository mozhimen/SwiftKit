package com.mozhimen.basick.basek.commons

import android.os.Bundle

/**
 * @ClassName BaseKIAction
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/25 13:44
 * @Version 1.0
 */
interface IBaseKActivity {
    fun initFlag()

    fun initLayout()

    fun initData(savedInstanceState: Bundle?)

    fun initView(savedInstanceState: Bundle?)

    fun initObserver()
}