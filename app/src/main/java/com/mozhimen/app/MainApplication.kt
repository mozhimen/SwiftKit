package com.mozhimen.app

import com.mozhimen.abilityk.hotfixk.HotFixMgr
import com.mozhimen.basick.basek.BaseKApplication

/**
 * @ClassName MainApplication
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/28 16:03
 * @Version 1.0
 */
class MainApplication : BaseKApplication() {
    override fun onCreate() {
        super.onCreate()

        //hotfixk
        HotFixMgr.instance.init()//注释此条查看热修复前的弹框提示
    }
}