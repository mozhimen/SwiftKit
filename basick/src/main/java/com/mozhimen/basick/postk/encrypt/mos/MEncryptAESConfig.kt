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
class MEncryptAESConfig(
    secretKey: String,//key
    ivString: String, //IV
    secureKeyLength: Int,  //AES 的 密钥长度，32 字节，范围：16 - 32 字节
    defaultFill: String,    //秘钥长度不足 16 个字节时，默认填充位数
    charset: Charset,  //字符编码
    cipherAlgorithm: String    //加解密算法/工作模式/填充方式
) : BaseEncryptConfig(
    secretKey,
    ivString,
    CAlgorithm.AES,
    secureKeyLength,
    defaultFill,
    charset,
    cipherAlgorithm
)