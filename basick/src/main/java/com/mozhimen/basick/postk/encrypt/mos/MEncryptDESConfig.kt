package com.mozhimen.basick.postk.encrypt.mos

import com.mozhimen.basick.elemk.cons.CAlgorithm
import com.mozhimen.basick.postk.encrypt.bases.BaseEncryptConfig
import java.nio.charset.Charset

/**
 * @ClassName MEncryptAESConfig
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/12 15:55
 * @Version 1.0
 */
class MEncryptDESConfig(
    secretKey: String= "saaierForTodoKey",//key
    charset: Charset= Charsets.UTF_8,  //字符编码
) : BaseEncryptConfig(
    secretKey,
    "",
    CAlgorithm.DES,
    32,
    "0",
    charset,
    ""
)