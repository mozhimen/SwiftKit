package com.mozhimen.app

import com.mozhimen.app.componentk.guidek.fragments.HomeFragment
import com.mozhimen.basick.basek.BaseKApplication
import com.mozhimen.basick.extsk.toJson
import com.mozhimen.basick.logk.LogKMgr
import com.mozhimen.basick.logk.mos.LogKConfig
import com.mozhimen.basick.logk.printers.PrinterConsole
import com.mozhimen.basick.logk.printers.PrinterFile
import com.mozhimen.basick.stackk.StackKMgr
import com.mozhimen.componentk.guidek.GuideK
import com.mozhimen.componentk.guidek.mos.*
import com.mozhimen.uicorek.tabk.bottom.mos.TabKBottomMo

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

        //logk
        LogKMgr.init(_logkConfig, PrinterConsole(), PrinterFile.getInstance(retentionDay = 3))

        //stackk
        StackKMgr.instance.init()

        //guidek
        //GuideKMgr.instance.init(_guidekConfig)

    }

    private val _guidekConfig = GuideKPkgConfig(
        0,
        0,
        arrayListOf(
            GuideKPkgPage(
                GuideKPageInfo(
                    "com.mozhimen.app.componentk.guidek.fragment.HomeFragment",
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

    private val _logkConfig = object : LogKConfig() {
        override fun injectJsonParser(): IJsonParser {
            return object : IJsonParser {
                override fun toJson(src: Any): String {
                    return src.toJson()
                }
            }
        }

        override fun getGlobalTag(): String {
            return TAG
        }

        override fun enable(): Boolean {
            return true
        }

        override fun includeThread(): Boolean {
            return true
        }

        override fun stackTraceDepth(): Int {
            return 0
        }
    }

/*    private val _crashKCallback = object : ICrashKListener {
        override fun onGetMessage(msg: String?) {
            msg?.let {

            } ?: "Ops! A crash happened, but i didn't get it messages".e()
        }
    }*/
}