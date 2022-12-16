package com.mozhimen.uicorektest

import com.mozhimen.basick.elemk.application.bases.BaseApplication
import com.mozhimen.componentk.adaptk.AdaptKMgr


/**
 * @ClassName UicoreKApplication
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/16 15:11
 * @Version 1.0
 */
class UicoreKApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()

        AdaptKMgr.instance.init()
    }
}