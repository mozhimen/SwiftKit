package com.mozhimen.underlayktest

import com.mozhimen.basick.basek.BaseKApplication
import com.mozhimen.basick.extsk.toJson
import com.mozhimen.underlayk.crashk.commons.ICrashKListener
import com.mozhimen.underlayk.logk.LogKMgr
import com.mozhimen.basick.extsk.e
import com.mozhimen.underlayk.logk.mos.LogKConfig
import com.mozhimen.underlayk.logk.temps.PrinterConsole
import com.mozhimen.underlayk.logk.temps.PrinterFile

/**
 * @ClassName UnderlayKApplication
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/24 17:41
 * @Version 1.0
 */
class UnderlayKApplication : BaseKApplication() {
    override fun onCreate() {
        super.onCreate()

        //logk
        LogKMgr.instance.init(_logkConfig, PrinterConsole(), PrinterFile.getInstance(retentionDay = 3))

        //crashk
        //CrashKMgr.instance.init(_crashKCallback)
    }

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

        override fun onGetCrashJava(msg: String?) {
            msg?.let {

            } ?: "Ops! A crash happened, but i didn't get it messages".e()
        }

        override fun onGetCrashNative(msg: String?) {
            msg?.let {

            } ?: "Ops! A crash happened, but i didn't get it messages".e()
        }
    }
}