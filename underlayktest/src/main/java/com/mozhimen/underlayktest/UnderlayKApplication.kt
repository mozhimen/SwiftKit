package com.mozhimen.underlayktest

import com.mozhimen.basick.elemk.android.app.bases.BaseApplication
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiInit_InApplication
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.squareup.moshi.moshiT2Json
import com.mozhimen.underlayk.crashk.CrashKMgr
import com.mozhimen.underlayk.crashk.commons.ICrashKListener
import com.mozhimen.underlayk.logk.LogKMgr
import com.mozhimen.underlayk.logk.bases.BaseLogKConfig
import com.mozhimen.underlayk.logk.temps.printer.LogKPrinterConsole
import com.mozhimen.underlayk.logk.temps.printer.LogKPrinterMonitor

/**
 * @ClassName UnderlayKApplication
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/24 17:41
 * @Version 1.0
 */
@ALintKOptIn_ApiInit_InApplication
@AManifestKRequire(CPermission.SYSTEM_ALERT_WINDOW)
class UnderlayKApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()

        //logk
        LogKMgr.instance.init(_logkConfig, LogKPrinterConsole()/*, LogKPrinterFile.getInstance(retentionDay = 3)*/, LogKPrinterMonitor())

        //crashk
        CrashKMgr.instance.init(_crashKCallback)
    }

    private val _logkConfig = object : BaseLogKConfig() {
        override fun injectJsonParser(): IJsonParser {
            return object : IJsonParser {
                override fun toJson(src: Any): String {
                    return src.moshiT2Json()
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
            //msg?.et(TAG) ?: "Ops! A crash happened, but i didn't get it messages".e()
        }

        override fun onGetCrashNative(msg: String?) {
            //msg?.et(TAG) ?: "Ops! A crash happened, but i didn't get it messages".e()
        }
    }
}