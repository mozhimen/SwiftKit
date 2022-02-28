package com.mozhimen.app

import android.util.Log
import com.google.gson.Gson
import com.mozhimen.basicsmk.crashmk.CrashMK
import com.mozhimen.basicsmk.crashmk.CrashMKCallback
import com.mozhimen.basicsmk.logmk.LogMKManager
import com.mozhimen.basicsmk.logmk.helpers.ConsolePrinter
import com.mozhimen.basicsmk.logmk.mos.LogMKConfig
import com.mozhimen.basicsmk.stackmk.StackMK
import com.mozhimen.componentmk.basemk.BaseMKApplication

/**
 * @ClassName MainApplication
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/28 16:03
 * @Version 1.0
 */
class MainApplication : BaseMKApplication() {
    override fun onCreate() {
        super.onCreate()

        //stackmk
        StackMK.instance.init()

        //crashmk
        CrashMK.instance.init(_crashMKCallback)

        //logmk
        LogMKManager.init(
            logmkConfig,
            ConsolePrinter()/*, FilePrinter.getInstance(applicationContext.cacheDir.absolutePath, 0)*/
        )
    }

    private val logmkConfig = object : LogMKConfig() {
        override fun injectJsonParser(): JsonParser {
            return object : JsonParser {
                override fun toJson(src: Any): String {
                    return Gson().toJson(src)
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
            return 5
        }
    }

    private val _crashMKCallback = object : CrashMKCallback {
        override fun onGetMessage(msg: String?) {
            msg?.let {

            } ?: Log.e(TAG, "Ops! A crash happened, but i didn't get it messages")
        }
    }
}