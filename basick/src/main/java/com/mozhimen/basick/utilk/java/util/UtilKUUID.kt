package com.mozhimen.basick.utilk.java.util

import com.mozhimen.basick.elemk.android.util.cons.CBase64
import com.mozhimen.basick.utilk.kotlin.bytes2bytesBase64
import com.mozhimen.basick.utilk.kotlin.bytes2str
import com.mozhimen.basick.utilk.kotlin.strHex2bytes
import java.util.UUID

/**
 * @ClassName UtilKStrUuid
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 15:51
 * @Version 1.0
 */
fun String.compressStrUUID(): String =
    UtilKUUID.compressStrUUID(this)

object UtilKUUID {
    @JvmStatic
    fun getStrUUID(): String =
        UUID.randomUUID().toString()

    /**
     * 生成随机字符串
     */
    @JvmStatic
    fun getFormatStrUUID(): String =
        getStrUUID().replace("-", "")

    //////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun compressStrUUID(strUUID: String): String =
        strUUID.replace("-", "").strHex2bytes().bytes2bytesBase64(CBase64.URL_SAFE or CBase64.NO_PADDING or CBase64.NO_WRAP).bytes2str()
}