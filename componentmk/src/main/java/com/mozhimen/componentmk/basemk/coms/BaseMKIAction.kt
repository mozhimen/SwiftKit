package com.mozhimen.componentmk.basemk.coms

import android.os.Bundle

/**
 * @ClassName BaseMKIAction
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/25 13:44
 * @Version 1.0
 */
interface BaseMKIAction {
    fun initFlag()
    /**
     * vb.vm=vm
     */
    fun assignVM()

    fun initData(savedInstanceState: Bundle?)

    fun initView()
}