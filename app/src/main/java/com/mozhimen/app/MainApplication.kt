package com.mozhimen.app

import android.util.Log
import com.mozhimen.app.componentk.guidek.fragment.HomeFragment
import com.mozhimen.basicsk.basek.BaseKApplication
import com.mozhimen.basicsk.crashk.commons.ICrashKListener
import com.mozhimen.basicsk.extsk.e
import com.mozhimen.basicsk.extsk.toJson
import com.mozhimen.basicsk.logk.LogKMgr
import com.mozhimen.basicsk.logk.mos.LogKConfig
import com.mozhimen.basicsk.logk.printers.PrinterConsole
import com.mozhimen.basicsk.logk.printers.PrinterFile
import com.mozhimen.basicsk.stackk.StackKMgr
import com.mozhimen.componentk.guidek.GuideK
import com.mozhimen.componentk.guidek.GuideKMgr
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
        LogKMgr.init(_logkConfig, PrinterConsole(), PrinterFile.getInstance())

        //stackk
        StackKMgr.instance.init()

        //guidek
        GuideKMgr.instance.init(_config)
    }

    private val _config = GuideKPkgConfig(
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

    private val _crashKCallback = object : ICrashKListener {
        override fun onGetMessage(msg: String?) {
            msg?.let {

            } ?: "Ops! A crash happened, but i didn't get it messages".e()
        }
    }
}