package com.mozhimen.basick.utilk.java.lang

import com.mozhimen.basick.utilk.bases.BaseUtilK
import java.io.IOException

/**
 * @ClassName UtilKShell
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/13 17:52
 * @Version 1.0
 */
object UtilKRuntime : BaseUtilK() {

    @JvmStatic
    fun get(): Runtime =
        Runtime.getRuntime()

    @JvmStatic
    @Throws(IOException::class)
    fun exec(cmdarray: Array<String>): Process =
        get().exec(cmdarray)

    @JvmStatic
    @Throws(IOException::class)
    fun exec(command: String): Process =
        get().exec(command)

}