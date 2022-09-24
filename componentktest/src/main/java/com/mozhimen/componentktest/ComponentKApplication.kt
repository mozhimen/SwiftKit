package com.mozhimen.componentktest

import com.mozhimen.basick.basek.BaseKApplication
import com.mozhimen.componentk.guidek.GuideK
import com.mozhimen.componentk.guidek.mos.GuideKPageInfo
import com.mozhimen.componentk.guidek.mos.GuideKPkgConfig
import com.mozhimen.componentk.guidek.mos.GuideKPkgPage
import com.mozhimen.componentktest.guidek.fragments.HomeFragment
import com.mozhimen.uicorek.tabk.bottom.mos.TabKBottomMo

/**
 * @ClassName ComponentKApplication
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/25 1:13
 * @Version 1.0
 */
class ComponentKApplication: BaseKApplication() {

    override fun onCreate() {
        super.onCreate()

        //guidek
        //GuideKMgr.instance.init(_guidekConfig)
    }

    private val _guidekConfig = GuideKPkgConfig(
        0,
        0,
        arrayListOf(
            GuideKPkgPage(
                GuideKPageInfo(
                    "com.mozhimen.componentktest.guidek.fragment.HomeFragment",
                    "fragment",
                    0,
                    GuideK.getHashCode(HomeFragment::class.java),
                    "main/guidek/home"
                ),
                TabKBottomMo(
                    "首页",
                    "fonts/iconfont.ttf",
                    "&#xe98d;",
                    "&#xe98d;",
                    "#ff000000",
                    "#ff287FF1"
                )
            )
        )
    )
}