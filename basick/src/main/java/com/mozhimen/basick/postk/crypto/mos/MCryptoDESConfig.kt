package com.mozhimen.basick.postk.crypto.mos

import com.mozhimen.basick.postk.crypto.cons.CAlgorithm
import com.mozhimen.basick.postk.crypto.bases.BaseCryptoConfig
import java.nio.charset.Charset

/**
 * @ClassName MEncryptAESConfig
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/12 15:55
 * @Version 1.0
 */
class MCryptoDESConfig(
    secretKey: String= "saaierForTodoKey",//key
    charset: Charset= Charsets.UTF_8,  //字符编码
) : BaseCryptoConfig(
    secretKey,
    "",
    CAlgorithm.DES,
    32,
    "0",
    charset,
    ""
)