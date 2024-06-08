package com.mozhimen.basick.utilk.javax.crypto

import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.android.security.UtilKKeyGenParameterSpec
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

/**
 * @ClassName UtilKKeyGenerator
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/6/7
 * @Version 1.0
 */
object UtilKKeyGenerator {
    @JvmStatic
    fun get(algorithm: String): KeyGenerator =
        KeyGenerator.getInstance(algorithm)

    @JvmStatic
    fun get(algorithm: String, provider: String): KeyGenerator =
        KeyGenerator.getInstance(algorithm, provider)

    @RequiresApi(CVersCode.V_23_6_M)
    @JvmStatic
    fun getKey(
        algorithm: String,
        provider: String,
        keyName: String,
        purposes: Int,
        blockModes: String?,
        encryptionPaddings: Array<String>?,
        userAuthenticationRequired: Boolean?,
        invalidatedByBiometricEnrollment: Boolean?
    ): SecretKey {
        val keyGenerator = get(algorithm, provider)
        keyGenerator.init(UtilKKeyGenParameterSpec.get(keyName, purposes, blockModes, encryptionPaddings, userAuthenticationRequired, invalidatedByBiometricEnrollment))
        return keyGenerator.generateKey()
    }
}