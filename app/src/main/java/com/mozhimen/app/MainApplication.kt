package com.mozhimen.app

import android.util.Log
import com.google.gson.Gson
import com.mozhimen.basicsk.crashk.CrashK
import com.mozhimen.basicsk.crashk.CrashKCallback
import com.mozhimen.basicsk.logk.LogKManager
import com.mozhimen.basicsk.logk.helpers.ConsolePrinter
import com.mozhimen.basicsk.logk.mos.LogKConfig
import com.mozhimen.basicsk.stackk.StackK
import com.mozhimen.componentk.basek.BaseKApplication

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

        //stackk
        StackK.instance.init()

        //crashk
        CrashK.instance.init(_crashKCallback)

        //logk
        LogKManager.init(
            logkConfig,
            ConsolePrinter()/*, FilePrinter.getInstance(applicationContext.cacheDir.absolutePath, 0)*/
        )
    }

    private val logkConfig = object : LogKConfig() {
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

    private val _crashKCallback = object : CrashKCallback {
        override fun onGetMessage(msg: String?) {
            msg?.let {

            } ?: Log.e(TAG, "Ops! A crash happened, but i didn't get it messages")
        }
    }
}