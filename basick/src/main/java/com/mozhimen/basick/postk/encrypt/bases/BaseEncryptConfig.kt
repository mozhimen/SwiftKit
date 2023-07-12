package com.mozhimen.basick.postk.encrypt.bases

import java.nio.charset.Charset

/**
 * @ClassName MEncryptAESConfig
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/12 15:55
 * @Version 1.0
 */
open class BaseEncryptConfig(
    var secretKey: String,//key
    var ivString: String, //IV
    var keyAlgorithm: String,//加密算法
    var secureKeyLength: Int,  //AES 的 密钥长度，32 字节，范围：16 - 32 字节
    var defaultFill: String,    //秘钥长度不足 16 个字节时，默认填充位数
    var charset: Charset,  //字符编码
    var cipherAlgorithm: String    //加解密算法/工作模式/填充方式
)