package com.mozhimen.basick.postk.encrypt

import com.mozhimen.basick.postk.encrypt.helpers.EncryptDESProvider
import com.mozhimen.basick.postk.encrypt.mos.MEncryptDESConfig
import java.nio.charset.Charset

/**
 * @ClassName UtilKDES
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/16 20:21
 * @Version 1.0
 */
object PostKEncryptDES {

    @JvmStatic
    fun with(
        secretKey: String = "saaierForTodoKey",
        charset: Charset = Charsets.UTF_8,
    ): EncryptDESProvider =
        EncryptDESProvider(MEncryptDESConfig(secretKey, charset))
}