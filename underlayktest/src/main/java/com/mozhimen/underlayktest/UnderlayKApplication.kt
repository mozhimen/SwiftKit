package com.mozhimen.underlayktest

import com.mozhimen.basick.elemk.android.app.bases.BaseApplication
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.lintk.optin.OptInApiMultiDex_InApplication
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.squareup.moshi.t2json
import com.mozhimen.underlayk.crashk.CrashKMgr
import com.mozhimen.underlayk.crashk.commons.ICrashKListener
import com.mozhimen.underlayk.logk.LogKMgr
import com.mozhimen.underlayk.logk.bases.BaseLogKConfig
import com.mozhimen.underlayk.logk.commons.ILogKJsonParser
import com.mozhimen.underlayk.logk.temps.printer.LogKPrinterConsole
import com.mozhimen.underlayk.logk.temps.printer.LogKPrinterFile
import com.mozhimen.underlayk.logk.temps.printer.LogKPrinterMonitor

/**
 * @ClassName UnderlayKApplication
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/24 17:41
 * @Version 1.0
 */
@OptIn(OptInApiMultiDex_InApplication::class)
@OptInApiInit_InApplication
@AManifestKRequire(CPermission.SYSTEM_ALERT_WINDOW)
class UnderlayKApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()

        //logk
        LogKMgr.instance.init(_logkConfig, LogKPrinterConsole(), LogKPrinterFile(retentionDay = 3), LogKPrinterMonitor())

        //crashk
        CrashKMgr.instance.init(_crashKCallback)

    }

    private val _logkConfig = object : BaseLogKConfig() {
        override fun injectJsonParser(): ILogKJsonParser =
            object : ILogKJsonParser {
                override fun toJson(src: Any): String =
                    src.t2json()
            }

        override fun getGlobalTag(): String =
            TAG

        override fun getStackTraceDepth(): Int =
            0

        override fun isEnable(): Boolean =
            true

        override fun isIncludeThread(): Boolean =
            false
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