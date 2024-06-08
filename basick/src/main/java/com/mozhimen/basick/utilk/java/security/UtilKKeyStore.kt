package com.mozhimen.basick.utilk.java.security

import java.security.KeyStore
import javax.crypto.SecretKey

/**
 * @ClassName UtilKKeyStore
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/6/7
 * @Version 1.0
 */
object UtilKKeyStore {
    @JvmStatic
    fun get(type: String): KeyStore =
        KeyStore.getInstance(type)

    @JvmStatic
    fun getKey(keyName: String, type: String): SecretKey? {
        try {
            val keyStore = get(type)
            keyStore.load(null)
            keyStore.getKey(keyName, null)?.let {
                return it as SecretKey
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}