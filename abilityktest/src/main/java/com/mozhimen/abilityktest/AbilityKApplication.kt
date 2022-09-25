package com.mozhimen.abilityktest

import com.mozhimen.abilityk.hotfixk.HotFixMgr
import com.mozhimen.basick.basek.BaseKApplication

/**
 * @ClassName AbilityKApplication
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/25 22:26
 * @Version 1.0
 */
class AbilityKApplication : BaseKApplication() {

    override fun onCreate() {
        super.onCreate()

        //hotfixk
        HotFixMgr.instance.init()//注释此条查看热修复前的弹框提示
    }
}