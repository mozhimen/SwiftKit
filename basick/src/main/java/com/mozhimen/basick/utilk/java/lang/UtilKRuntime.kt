package com.mozhimen.basick.utilk.java.lang

import android.text.TextUtils
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.elemk.cons.CPath
import com.mozhimen.basick.elemk.kotlin.text.cons.CCharsets
import com.mozhimen.basick.elemk.mos.MResultISS
import com.mozhimen.basick.utilk.android.util.d
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.UtilKInputStreamReader
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream

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