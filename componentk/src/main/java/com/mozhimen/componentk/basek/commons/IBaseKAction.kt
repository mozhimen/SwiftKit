package com.mozhimen.componentk.basek.commons

import android.os.Bundle

/**
 * @ClassName BaseKIAction
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/25 13:44
 * @Version 1.0
 */
interface IBaseKAction {
    fun initFlag()
    /**
     * vb.vm=vm
     */
    fun assignVM()

    fun initData(savedInstanceState: Bundle?)

    fun initView()
}