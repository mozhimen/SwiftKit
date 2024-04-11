package com.mozhimen.basick.postk.crypto.mos

import com.mozhimen.basick.postk.crypto.cons.CAlgorithm
import com.mozhimen.basick.postk.crypto.bases.BaseCryptoConfig
import java.nio.charset.Charset

/**
 * @ClassName MEncryptAESConfig
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
class MCryptoAESConfig(
    secretKey: String = "saaierForTodoKey",//key
    ivString: String = "ihaierForTodo_Iv", //IV
    secureKeyLength: Int = 16,  //AES 的 密钥长度，32 字节，范围：16 - 32 字节
    defaultFill: String = "0",    //秘钥长度不足 16 个字节时，默认填充位数
    charset: Charset = Charsets.UTF_8,  //字符编码
    cipherAlgorithm: String = "AES/CBC/PKCS5Padding"  //加解密算法/工作模式/填充方式
) : BaseCryptoConfig(
    secretKey,
    ivString,
    CAlgorithm.AES,
    secureKeyLength,
    defaultFill,
    charset,
    cipherAlgorithm
)