package com.mozhimen.basick.utilk.android.os

import android.os.Looper
import com.mozhimen.basick.elemk.commons.I_Listener

/**
 * @ClassName UtilKLooper
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/28 23:13
 * @Version 1.0
 */
object UtilKLooper {
    @JvmStatic
    fun get_ofMain(): Looper =
        getMainLooper()

    @JvmStatic
    fun get_ofMy(): Looper? =
        getMyLooper()

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getMainLooper(): Looper =
        Looper.getMainLooper()

    @JvmStatic
    fun getMyLooper(): Looper? =
        Looper.myLooper()

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getThread_ofMain(): Thread =
        get_ofMain().thread

    ////////////////////////////////////////////////////////////////////////////

    //是否是MainLooper
    @JvmStatic
    fun isMainLooper(): Boolean =
        get_ofMy() == get_ofMain()

    ////////////////////////////////////////////////////////////////////////////

    /**
     * 循环
     */
    @JvmStatic
    fun prepareAndLoop(block: I_Listener) {
        var myLooper = get_ofMy()
        if (myLooper == null) {
            Looper.prepare()
            myLooper = get_ofMy()
        }
        block.invoke()
        if (myLooper != null) {
            Looper.loop()
            myLooper.quit()
        }
    }
}